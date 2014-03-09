package de.htwg.seapal.aview.gui.activity;


import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.inject.Inject;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.adapter.SideDrawerListAdapter;
import de.htwg.seapal.aview.gui.fragment.MapDialogFragment;
import de.htwg.seapal.aview.gui.fragment.PictureDialogFragment;
import de.htwg.seapal.aview.gui.plugins.IMapPlugin;
import de.htwg.seapal.aview.gui.plugins.IMapPluginable;
import de.htwg.seapal.aview.gui.plugins.impl.CalcDistanceMapPlugin;
import de.htwg.seapal.aview.gui.plugins.impl.RouteDrawingMapPlugin;
import de.htwg.seapal.aview.gui.plugins.impl.WaypointDrawingMapPlugin;
import de.htwg.seapal.controller.IMainController;
import de.htwg.seapal.manager.SessionManager;
import de.htwg.seapal.model.IModel;
import de.htwg.seapal.model.IWaypoint;
import de.htwg.seapal.model.impl.Trip;
import de.htwg.seapal.services.TrackingService;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

import static java.lang.Math.asin;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;


@Deprecated
public class MapActivityTemp extends BaseDrawerActivity
        implements OnMapLongClickListener, OnMapClickListener, OnMarkerClickListener,
        MapDialogFragment.MapDialogListener, IMapPluginable, PictureDialogFragment.PictureDialogFragmentListener {

    private static final String DISTANCE_POLYLINE = "map_distance_polyline";
    private static final String WAYPOINT_POLYLINE = "map_waypoint_polyline";
    private static final String TRACKING_SERVICE = "map_tracking_service";


    private static final String MAP_SELECTED_OPTION_STATE = "map_selected_option_state";
    private static final String REGISTERED_PLUGINS_LIST = "map_registered_plugins";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    public static Marker crosshairMarker;
    String mCurrentPhotoPath;
    // Tracking Service
    private Intent trackingService;
    private TrackingServiceWaypointBroadcastReceiver waypointBroadcastReceiver;
    /**
     * used to register plugins for the map.
     */
    private HashMap<String, IMapPlugin> mapPluginHashMap;
    private MarkerOptions crosshairMarkerOptions;
    @Inject
    private IMainController mainController;
    @Inject
    private SessionManager sessionManager;
    // Drawer fields
    @InjectView(R.id.drawer_menu_drawer_list_right)
    private ListView drawerListViewRight;
    @InjectResource(R.array.drawer_list_array_right)
    private String[] drawerActivityListRight;
    private DrawerLayout drawer;
    private GoogleMap map;
    /**
     * State of the Activity
     */
    private SelectedOption option = SelectedOption.NONE;
    private Marker movingDirectionMarker;
    private Marker aimDirectionArrow;
    private Location oldLocation;
    private Uri fileUri;
    private Marker pictureMarker;
    private Marker aimDirectionTarget;

    private static boolean isIntentAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public static void zoomToWaypointRoute(GoogleMap map, List<LatLng> waypointList) {
        if (waypointList != null && !waypointList.isEmpty()) {
            LatLngBounds.Builder boundsBuilder = LatLngBounds.builder();
            for (LatLng latLng : waypointList) {
                boundsBuilder.include(latLng);
            }
            LatLngBounds latLngBounds = boundsBuilder.build();
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngBounds(latLngBounds, 50);
            map.animateCamera(yourLocation);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);


        FragmentManager myFragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) myFragmentManager.findFragmentById(R.id.map);
        map = mapFragment.getMap();


        if (map != null) {


            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(false);
            map.getUiSettings().setZoomControlsEnabled(false);

            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);

            map.setOnMapClickListener(this);
            map.setOnMapLongClickListener(this);
            map.setOnMarkerClickListener(this);

            goToLastKnownLocation(13);
        }

        setupAimDirectionArrows();

        drawer = (DrawerLayout) findViewById(R.id.drawer_menu_drawer_layout);

        setupDrawerForMapView();

        int icons[] = {
                R.drawable.search_icon,
                R.drawable.marker_icon,
                R.drawable.arrows_h_icon,
                R.drawable.arrows_h_icon,
                R.drawable.arrows_h_icon,
                R.drawable.camera_icon,
                R.drawable.male_icon,
                R.drawable.trash_icon
        };

        drawerListViewRight.setAdapter(new SideDrawerListAdapter(this,
                drawerActivityListRight, icons, getResources(), SideDrawerListAdapter.DrawerSide.RIGHT));
        drawerListViewRight.setOnItemClickListener(new DrawerItemClickListener());

        mapPluginHashMap = new LinkedHashMap<String, IMapPlugin>();

//        registerMapPlugin("route_drawing_map_plugin",new RouteDrawingMapPlugin(map));
//        registerMapPlugin("waypoint_drawing_map_plugin",new WaypointDrawingMapPlugin(map));


        // Marker configuration
        crosshairMarkerOptions = new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.haircross))
                .anchor(0.5f, 0.5f);

        waypointBroadcastReceiver = new TrackingServiceWaypointBroadcastReceiver();

    }

    @Override
    public void registerMapPlugin(String name, IMapPlugin plugin) {
        mapPluginHashMap.put(name, plugin);
    }

    @Override
    public IMapPlugin getMapPlugin(String name) {
        return mapPluginHashMap.get(name);
    }

    @Override
    public void unregisterMapPlugin(String name) {
        if (mapPluginHashMap.containsKey(name)) {
            mapPluginHashMap.remove(name);
        }

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                drawWaypoints();
            }
        }.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle pauseState = getIntent().getBundleExtra("pause_state");
        if (pauseState != null)
            onRestoreInstanceState(pauseState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        onSaveInstanceState(null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Bundle pauseState = new Bundle();
        getIntent().putExtra("pause_state", pauseState);

        // Saving Plugins
        pauseState.putSerializable(REGISTERED_PLUGINS_LIST, mapPluginHashMap);

        if (waypointBroadcastReceiver.isInitialStickyBroadcast()) {
            unregisterReceiver(waypointBroadcastReceiver);
        }

        pauseState.putParcelable(TRACKING_SERVICE, trackingService);
        pauseState.putSerializable(MAP_SELECTED_OPTION_STATE, option);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Bundle pauseState = getIntent().getBundleExtra("pause_state");
        if (pauseState != null) {
            // Restoring registered plugins.
            mapPluginHashMap = (HashMap<String, IMapPlugin>) pauseState.get(REGISTERED_PLUGINS_LIST);
            // Restoring Map State
            option = (SelectedOption) pauseState.getSerializable(MAP_SELECTED_OPTION_STATE);

            // Redrawing Plugins
            for (Map.Entry<String, IMapPlugin> m : mapPluginHashMap.entrySet()) {
                m.getValue().redraw(map);
            }

            trackingService = pauseState.getParcelable(TRACKING_SERVICE);
            if (trackingService != null) {
                registerReceiver(waypointBroadcastReceiver, new IntentFilter(TrackingService.WAYPOINT_BROADCAST_RECEIVER));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Location loc = map.getMyLocation();
            LatLng coor = new LatLng(loc.getLatitude(), loc.getLongitude());

            Bitmap.Config conf = Bitmap.Config.ARGB_8888;
            Bitmap bmp = Bitmap.createBitmap(imageBitmap.getWidth() + 10, imageBitmap.getHeight() + 10, conf);
            Canvas canvas1 = new Canvas(bmp);

            Paint color = new Paint();
            color.setColor(Color.BLUE);

            //modify canvas
            canvas1.drawColor(color.getColor());
            canvas1.drawBitmap(imageBitmap, 5, 5, color);

            //add marker to Map
            if (pictureMarker != null)
                pictureMarker.remove();
            pictureMarker = map.addMarker(new MarkerOptions().position(coor).icon(BitmapDescriptorFactory.fromBitmap(bmp)));

            showPictureDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSearchRequested() {
        Toast.makeText(this, "Coooool", Toast.LENGTH_SHORT).show();
        return super.onSearchRequested();
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_goTo:
                goToLastKnownLocation(0);
                break;
            case R.id.start_tracking:
                startTracking();
                break;
            case R.id.stop_tracking:
                stopTracking();
                break;
            case R.id.action_show_right_drawer:
                toggleRightDrawer();
                break;
            case android.R.id.home:
                closeRightDreawer();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onMapClick(LatLng latlng) {
        switch (option) {
            case MARK:
                break;
            case ROUTE:
                RouteDrawingMapPlugin routeDrawingMapPlugin = (RouteDrawingMapPlugin) getMapPlugin("route_drawing_map_plugin");
                routeDrawingMapPlugin.doAction(map, latlng);
                break;
            case DISTANCE:
                CalcDistanceMapPlugin c = (CalcDistanceMapPlugin) getMapPlugin("calc_distance_map_plugin");
                double calcDistance = c.doAction(map, latlng);
                Toast.makeText(getApplicationContext(),
                        String.format("%.2f", calcDistance) + "KM",
                        Toast.LENGTH_SHORT).show();
                break;
            case MENU_ROUTE:
                setCrosshairMarker(latlng);
                onDialogSetRouteClick(new DialogFragment(), crosshairMarker);
                option = SelectedOption.ROUTE;
                onMapClick(latlng);
                break;
            case MENU_MARK:
                setCrosshairMarker(latlng);
                onDialogSetMarkClick(new DialogFragment(), crosshairMarker);
                option = SelectedOption.NONE;
                break;
            case MENU_DISTANCE:
                setCrosshairMarker(latlng);
                onDialogcalcDistanceClick(new DialogFragment(), crosshairMarker);
                option = SelectedOption.DISTANCE;
                break;
            default:
                break;
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        option = SelectedOption.NONE;
        setCrosshairMarker(latLng);
        crosshairMarker.showInfoWindow();
    }

    @Override
    public boolean onMarkerClick(Marker arg0) {
        MapDialogFragment f = new MapDialogFragment(arg0);
        f.show(getFragmentManager(), "dialog");
        return false;
    }

    @Override
    public void onDialogSetMarkClick(DialogFragment dialog, Marker marker) {
        option = SelectedOption.MARK;
        map.addMarker(new MarkerOptions().position(crosshairMarker.getPosition()));
        crosshairMarker.remove();
    }

    @Override
    public void onDialogSetRouteClick(DialogFragment dialog, Marker marker) {
        option = SelectedOption.ROUTE;
        registerMapPlugin("route_drawing_map_plugin", new RouteDrawingMapPlugin(map, crosshairMarker.getPosition()));
        crosshairMarker.remove();
    }

    @Override
    public void onDialogcalcDistanceClick(DialogFragment dialog, Marker marker) {
        option = SelectedOption.DISTANCE;
        registerMapPlugin("calc_distance_map_plugin", new CalcDistanceMapPlugin(map, crosshairMarker.getPosition()));
        crosshairMarker.remove();
    }

    @Override
    public void onDialogSetTargetClick(DialogFragment dialog, Marker marker) {
        drawAimArrow();
        option = SelectedOption.GOAL;
    }

    @Override
    public void onDialogDeleteClick(DialogFragment dialog, Marker marker) {
        crosshairMarker.remove();
        option = SelectedOption.NONE;
    }

    private void drawWaypoints() {
        Intent i = getIntent();
        List<IWaypoint> waypoints = (List<IWaypoint>) i.getSerializableExtra("waypoints");
        if (waypoints != null) {
            registerMapPlugin("map_way_point_trough_trip_plugin", new WaypointDrawingMapPlugin(map, "#333333"));
            WaypointDrawingMapPlugin waypointDrawingMapPlugin = (WaypointDrawingMapPlugin) getMapPlugin("map_way_point_trough_trip_plugin");
            for (IWaypoint w : waypoints) {
                waypointDrawingMapPlugin.doAction(map, new LatLng(w.getLatitude(), w.getLongitude()));
            }
            waypointDrawingMapPlugin.zoomTo(map);
        }
    }

    private void setupDrawerForMapView() {

        int actionBarHeight = getActionBarHeight();
        ListView listLeft = (ListView) findViewById(R.id.drawer_menu_drawer_list_left);
        ListView listRight = (ListView) findViewById(R.id.drawer_menu_drawer_list_right);

        listLeft.setBackgroundDrawable(getResources().getDrawable(R.drawable.tranparent_drawer_under_actionbar));
        listRight.setBackgroundDrawable(getResources().getDrawable(R.drawable.tranparent_drawer_under_actionbar));

        listLeft.setPadding(0, actionBarHeight, 0, 0);
        listRight.setPadding(0, actionBarHeight, 0, 0);
    }

    private int getActionBarHeight() {
        int actionBarHeight = 0;
        // Calculate ActionBar height
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(
                    tv.data, getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    private void stopTracking() {
        if (trackingService != null) {
            UUID tripUuid = UUID.fromString(trackingService.getStringExtra(TrackingService.TRIP_UUID));
            Collection<Trip> trips = (Collection<Trip>) mainController.getSingleDocument("trip", sessionManager.getSession(), tripUuid);
            if (trips.iterator().hasNext()) {
                Trip t = trips.iterator().next();
                t.setEndDate(System.currentTimeMillis());
                mainController.creatDocument("trip", t, sessionManager.getSession());

                SharedPreferences s = getSharedPreferences(TripActivity.TRIP_PREFS, 0);
                SharedPreferences.Editor editor = s.edit();
                editor.commit();

                unregisterReceiver(waypointBroadcastReceiver);
                WaypointDrawingMapPlugin w = (WaypointDrawingMapPlugin) getMapPlugin("waypoint_tracking_map_plugin");
                w.zoomTo(map);
                unregisterMapPlugin("waypoint_tracking_map_plugin");
                stopService(trackingService);
                trackingService = null;
            } else {
                Toast.makeText(getApplicationContext(), "Somehow the trip tracking did not create a trip, maybe you started tracking for a crew ship.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Tracking not Started", Toast.LENGTH_SHORT).show();
        }

    }

    private void startTracking() {
        SharedPreferences s = getSharedPreferences(LogbookTabsActivity.LOGBOOK_PREFS, 0);
        final String boatString = s.getString(LogbookTabsActivity.LOGBOOK_BOAT_FAVOURED, "");

        if (!StringUtils.isEmpty(boatString) && trackingService == null) {

            Collection<? extends IModel> boat = mainController.getSingleDocument("boat", sessionManager.getSession(), UUID.fromString(boatString));
            if (!boat.isEmpty()) {


                LayoutInflater inflater = getLayoutInflater();
                final View view = inflater.inflate(R.layout.start_tracking_dialog, null);


                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.enter_some_trip_info)
                        .setView(view)
                        .setPositiveButton(R.string.tracking_start, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                EditText name = (EditText) view.findViewById(R.id.tracking_trip_name);
                                EditText from = (EditText) view.findViewById(R.id.tracking_trip_from);
                                EditText to = (EditText) view.findViewById(R.id.tracking_trip_to);


                                Trip t = new Trip();
                                t.setStartDate(System.currentTimeMillis());
                                t.setName(name.getText().toString());
                                t.setTo(to.getText().toString());
                                t.setFrom(from.getText().toString());
                                t.setBoat(boatString);
                                t = (Trip) mainController.creatDocument("trip", t, sessionManager.getSession());

                                trackingService = new Intent(MapActivityTemp.this, TrackingService.class);
                                UUID tripUuid = t.getUUID();
                                trackingService.putExtra(TrackingService.TRIP, t);
                                trackingService.putExtra(TrackingService.TRIP_UUID, tripUuid.toString());
                                trackingService.putExtra(TrackingService.BOAT_UUID, boatString);

                                registerReceiver(waypointBroadcastReceiver, new IntentFilter(TrackingService.WAYPOINT_BROADCAST_RECEIVER));
                                registerMapPlugin("waypoint_tracking_map_plugin", new WaypointDrawingMapPlugin(map, "#345212"));

                                Toast.makeText(getApplicationContext(), "Tracking Started", Toast.LENGTH_SHORT).show();

                                startService(trackingService);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });
                // Create the AlertDialog object and show it.
                builder.create().show();


            } else {
                Toast.makeText(getApplicationContext(), "You probably favoured a boat of your crew", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "No favoured boat or tracking already started", Toast.LENGTH_SHORT).show();
        }
    }

    private void closeRightDreawer() {
        if (drawer.isDrawerOpen(Gravity.END)) {
            drawer.closeDrawer(Gravity.END);
        }
    }

    private void toggleRightDrawer() {
        if (drawer.isDrawerOpen(Gravity.END)) {
            drawer.closeDrawer(Gravity.END);
        } else {
            drawer.openDrawer(Gravity.END);
            drawer.closeDrawer(Gravity.START);
        }
    }

    private void setCrosshairMarker(LatLng latLng) {

        if (crosshairMarker != null) {
            crosshairMarker.remove();
        }

        crosshairMarker = map.addMarker(crosshairMarkerOptions.position(latLng).snippet(latLng.toString()));

        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        v.vibrate(100);
    }

    private void selectChoosenMeunPoint(int position) {
        switch (position) {
            case 0:
                Intent search = new Intent(this, SearchActivity.class);
                startActivity(search);
                break;
            case 1:
                //Mark
                option = SelectedOption.MENU_MARK;
                break;
            case 2:
                // Route
                option = SelectedOption.MENU_ROUTE;
                break;
            case 3:
                // Distance
                option = SelectedOption.MENU_DISTANCE;
                break;
            case 4:
                // Goto Location
                goToLastKnownLocation(0);
                break;
            case 5:
                // Take Picture
                if (isIntentAvailable(getApplicationContext())) {
                    dispatchTakePictureIntent();
                } else {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.noCameraAvailable),
                            Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case 6:
                // Person over Board
                break;
            case 7:
                // Discard Target
                aimDirectionArrow.remove();
                aimDirectionTarget.remove();
                aimDirectionTarget = null;
                aimDirectionArrow = null;
                option = SelectedOption.NONE;
                break;
        }
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    /**
     * goToLastKnownLocation
     *
     * @param ZoomLevel value 0 keeps the current zoom level
     */
    private void goToLastKnownLocation(float ZoomLevel) {
        // get current position
        LatLng coordinate = getLastKnownLocation();

        if (ZoomLevel == 0) {
            ZoomLevel = map.getCameraPosition().zoom;
        }

        if (coordinate == null) {
            Toast.makeText(getApplicationContext(), getString(R.string.noLocationSignal), Toast.LENGTH_SHORT).show();
        } else {
            // set map view to current position
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, ZoomLevel);
            map.animateCamera(yourLocation);
        }
    }

    private LatLng getLastKnownLocation() {

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        List<String> providers = manager.getProviders(false);

        for (String provider : providers) {
            location = manager.getLastKnownLocation(provider);
            //maybe try adding some Criteria here
            if (location != null)
                return new LatLng(location.getLatitude(), location.getLongitude());
        }
        //at this point we've done all we can and no location is returned
        return null;
    }

    private void setupAimDirectionArrows() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = manager.getProviders(false);

        final LocationListener loactionListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                drawDirectionAndAimArrow(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        manager.requestLocationUpdates(providers.get(1), 0, 0, loactionListener);
    }

    private void drawDirectionAndAimArrow(Location location) {

        if (aimDirectionArrow != null) {
            drawAimArrow();
        }

        if (oldLocation == null) {
            oldLocation = location;
            return;
        }

        float angle = calualteArrowDirection(oldLocation, location);

        showMovingDirection(angle);
    }

    private void drawAimArrow() {

        Location targetPos = new Location("Target");
        targetPos.setLatitude(crosshairMarker.getPosition().latitude);
        targetPos.setLongitude(crosshairMarker.getPosition().longitude);

        Location myPos = map.getMyLocation();

        float angle = calualteArrowDirection(myPos, targetPos);

        showAimDirectionAndTarget(angle, targetPos);

        crosshairMarker.remove();
    }

    private void showMovingDirection(float direction) {

        if (movingDirectionMarker != null) {
            movingDirectionMarker.remove();
        }

        if (map.getMyLocation() != null) {

            LatLng position = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());

            movingDirectionMarker = map.addMarker(new MarkerOptions()
                    .position(position)
                    .rotation(direction)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.moving_direction))
            );
        }
    }

    private void showAimDirectionAndTarget(float direction, Location target) {

        if (aimDirectionArrow != null) {
            aimDirectionArrow.remove();
        }

        if (aimDirectionTarget != null) {
            aimDirectionTarget.remove();
        }

        if (map.getMyLocation() != null) {

            LatLng position = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());
            LatLng targetPos = new LatLng(target.getLatitude(), target.getLongitude());

            aimDirectionArrow = map.addMarker(new MarkerOptions()
                    .position(position)
                    .rotation(direction)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.aim_direction))
            );

            aimDirectionTarget = map.addMarker(new MarkerOptions()
                    .position(targetPos)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ann_distance))
            );
        }
    }

    private float calualteArrowDirection(Location location, Location markerPos) {

        double hypo = sqrt(pow(markerPos.getLongitude() - location.getLongitude(), 2) + pow(markerPos.getLatitude() - location.getLatitude(), 2));
        float sin = (float) asin((markerPos.getLongitude() - location.getLongitude()) / hypo);

        float angle = (float) toDegrees(sin);

        angle = markerPos.getLatitude() < location.getLatitude() ? 180 - angle : angle;

        return angle;
    }

    private void showPictureDialog() {

        // Get the layout inflater
        LayoutInflater inflater = LayoutInflater.from(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivityTemp.this);
        builder.setView(inflater.inflate(R.layout.picture_fragment, null));
        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                pictureMarker.remove();
            }
        });

        builder.create().show();
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
    }

    private enum SelectedOption {
        NONE, MARK, ROUTE, DISTANCE, GOAL, MENU_ROUTE, MENU_MARK, MENU_DISTANCE, MENU_GOAL
    }

    public class TrackingServiceWaypointBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            LatLng latLng = intent.getParcelableExtra(TrackingService.LAT_LNG);
            WaypointDrawingMapPlugin plugin = (WaypointDrawingMapPlugin) getMapPlugin("waypoint_tracking_map_plugin");
            if (plugin != null) {
                plugin.doAction(map, latLng);
                plugin.redraw(map);
            }

        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.drawer_menu_drawer_list_right) {
                selectChoosenMeunPoint(position);
                drawer.closeDrawer(Gravity.END);
            }
        }
    }


}
