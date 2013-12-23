package de.htwg.seapal.aview.gui.activity;


import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.cblite.router.CBLURLStreamHandlerFactory;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.inject.Inject;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.Services.TrackingService;
import de.htwg.seapal.aview.gui.fragment.MapDialogFragment;
import de.htwg.seapal.aview.gui.fragment.PictureDialogFragment;
import de.htwg.seapal.controller.ITripController;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;


public class MapActivity extends BaseDrawerActivity
implements OnMapLongClickListener, OnMapClickListener, OnMarkerClickListener, 
MapDialogFragment.MapDialogListener {

    @InjectView(R.id.drawer_menu_drawer_list_right)
    private ListView drawerListViewRight;

    @InjectResource(R.array.drawer_list_array_right)
    private String[] drawerActivityListRight;

    @Inject
    private ITripController tripController;
    private Intent trackingService;


    private enum SelectedOption {
		NONE, MARK, ROUTE, DISTANCE, GOAL, MENU_ROUTE, MENU_MARK, MENU_DISTANCE, MENU_GOAL
	}
	
	static {
		CBLURLStreamHandlerFactory.registerSelfIgnoreError();
		//needed for TouchDB
	}

	private GoogleMap map;
	public static Marker crosshairMarker = null;
	private Polyline route = null;
	private SelectedOption option = SelectedOption.NONE;
	private LatLng lastPos;
	private final List<Marker> calcDistanceMarker = new LinkedList<Marker>();
	private Polyline calcDistanceRoute = null;
	private double calcDistance;
    private DrawerLayout drawer;


    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		FragmentManager myFragmentManager = getSupportFragmentManager();
		SupportMapFragment myMapFragment  = (SupportMapFragment) myFragmentManager.findFragmentById(R.id.map);
		map = myMapFragment.getMap();

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

        drawer = (DrawerLayout) findViewById(R.id.drawer_menu_drawer_layout);

        setupDrawerForMapView();

        drawerListViewRight.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item_right, drawerActivityListRight));
        drawerListViewRight.setOnItemClickListener(new DrawerItemClickListener());
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        handleSmallCameraPhoto(data);
    }

    private void setupDrawerForMapView() {

        int actionBarHeight = getActionBarHeight();
        ListView listLeft = (ListView) findViewById(R.id.drawer_menu_drawer_list_left);
        ListView listRight = (ListView) findViewById(R.id.drawer_menu_drawer_list_right);

        listLeft.setBackgroundDrawable(getResources().getDrawable(R.drawable.tranparent_drawer_under_actionbar));
        listRight.setBackgroundDrawable(getResources().getDrawable(R.drawable.tranparent_drawer_under_actionbar));

        listLeft.setPadding(0,actionBarHeight,0,0);
        listRight.setPadding(0,actionBarHeight,0,0);

    }

    private int getActionBarHeight() {
        int actionBarHeight = 0;
        // Calculate ActionBar height
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(
                                tv.data,getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);

        // Add SearchWidget.
        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        SearchView searchView = (SearchView) menu.findItem( R.id.action_search ).getActionView();

        searchView.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );

        return super.onCreateOptionsMenu( menu );
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_goTo:
                goToLastKnownLocation(15);
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

    private void stopTracking() {
        LocationManager l = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (trackingService != null) {
            tripController.setEndTime(UUID.fromString(trackingService.getStringExtra(TrackingService.TRIP_UUID)),System.currentTimeMillis());
            stopService(trackingService);

        } else {
            Toast.makeText(getApplicationContext(), "Tracking not Started", Toast.LENGTH_SHORT).show();
        }

    }

    private void startTracking() {
        SharedPreferences s = getSharedPreferences(LogbookTabsActivity.LOGBOOK_PREFS,0);
        final String boatString = s.getString(LogbookTabsActivity.LOGBOOK_BOAT_FAVOURED,"");
        if (!StringUtils.isEmpty(boatString)) {
            UUID boat = UUID.fromString(boatString);
            UUID trip = tripController.newTrip(boat);
            tripController.setStartTime(trip, System.currentTimeMillis());
            tripController.setName(trip, RandomStringUtils.random(12));
            trackingService = new Intent(this, TrackingService.class);
            trackingService.putExtra(TrackingService.TRIP_UUID, trip.toString());
            startService(trackingService);
        } else {
            Toast.makeText(getApplicationContext(), "No favoured boat", Toast.LENGTH_SHORT).show();
        }



    }

    private void closeRightDreawer() {
        if(drawer.isDrawerOpen(Gravity.END)){
            drawer.closeDrawer(Gravity.END);
        }
    }

    private void toggleRightDrawer() {
        if(drawer.isDrawerOpen(Gravity.END)){
            drawer.closeDrawer(Gravity.END);
        }else{
            drawer.openDrawer(Gravity.END);
            drawer.closeDrawer(Gravity.START);
        }
    }

    @Override
	public void onMapClick(LatLng latlng) {
		switch (option) {
		case MARK:
			break;
		case ROUTE:
			map.addMarker(new MarkerOptions()
					.position(latlng)
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.ann_route)));
			List<LatLng> routelst = route.getPoints();
			routelst.add(latlng);
			route.setPoints(routelst);
			break;
		case DISTANCE:
			calcDistance += calcDistance(lastPos, latlng);
			lastPos = latlng;
			calcDistanceMarker.add(map.addMarker(new MarkerOptions().
					position(latlng).
					icon(BitmapDescriptorFactory.fromResource(R.drawable.ann_distance))));
			List<LatLng> calclst = calcDistanceRoute.getPoints();
			calclst.add(latlng);
			calcDistanceRoute.setPoints(calclst);
			Toast.makeText(getApplicationContext(),
                    Math.round(calcDistance) + "KM",
                    Toast.LENGTH_LONG).show();
			break;
        case MENU_ROUTE:
            setCrosshairMarker(latlng);
            onDialogSetRouteClick(new DialogFragment());
            option = SelectedOption.ROUTE;
            onMapClick(latlng);
            break;
        case MENU_MARK:
            setCrosshairMarker(latlng);
            onDialogSetMarkClick(new DialogFragment());
            option = SelectedOption.NONE;
            break;
        case MENU_DISTANCE:
            setCrosshairMarker(latlng);
            onDialogcalcDistanceClick(new DialogFragment());
            option = SelectedOption.DISTANCE;
            break;
		default:
			break;
		}


	}

    private void setCrosshairMarker(LatLng latLng){

        if(crosshairMarker != null) {
            crosshairMarker.remove();
        }

        crosshairMarker = map.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.haircross))
                .snippet(latLng.toString())
                .anchor(0.5f, 0.5f));

        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        v.vibrate(100);
    }

	@Override
	public void onMapLongClick(LatLng latLng) {
		option = SelectedOption.NONE;
		setCrosshairMarker(latLng);
        crosshairMarker.showInfoWindow();
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {		
		MapDialogFragment f = new MapDialogFragment();
		f.show(getFragmentManager(), "dialog");
		return false;
	}

	@Override
	public void onDialogSetMarkClick(DialogFragment dialog) {
		option = SelectedOption.MARK;
		map.addMarker(new MarkerOptions().position(crosshairMarker.getPosition()));
		crosshairMarker.remove();
	}

	@Override
	public void onDialogSetRouteClick(DialogFragment dialog) {
		option = SelectedOption.ROUTE;
		if(route != null) {
			route = null;

		}
		map.addMarker(new MarkerOptions().
				position(crosshairMarker.getPosition()).
				icon(BitmapDescriptorFactory.fromResource(R.drawable.ann_route)));

		route = map.addPolyline(new PolylineOptions().
				add(crosshairMarker.getPosition()).
				width(5).color(Color.RED));


		crosshairMarker.remove();
	}

	@Override
	public void onDialogcalcDistanceClick(DialogFragment dialog) {
		option = SelectedOption.DISTANCE;
		if(calcDistanceRoute != null) {
			calcDistanceRoute.remove();
			for(Marker m : calcDistanceMarker) {
				m.remove();
			}
			calcDistanceMarker.clear();
		}
		lastPos = crosshairMarker.getPosition();
		calcDistance = 0.0;
		calcDistanceMarker.add(map.addMarker(new MarkerOptions().
				position(crosshairMarker.getPosition()).
				icon(BitmapDescriptorFactory.fromResource(R.drawable.ann_distance))));
        String ORANGE = "#FFBB03";
        calcDistanceRoute = map.addPolyline(new PolylineOptions().add(lastPos).width(5).color(Color.parseColor(ORANGE)));
		crosshairMarker.remove();
	}

	@Override
	public void onDialogSetTargetClick(DialogFragment dialog) {
		option = SelectedOption.GOAL;
	}

	@Override
	public void onDialogDeleteClick(DialogFragment dialog) {
		crosshairMarker.remove();
		option = SelectedOption.NONE;
	}

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
            if(parent.getId() == R.id.drawer_menu_drawer_list_right){
                selectChoosenMeunPoint(position);
                drawer.closeDrawer(Gravity.END);
            }
        }
    }

    void selectChoosenMeunPoint(int position){
        switch(position){
            case 0:
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
                goToLastKnownLocation(15);
                break;
            case 5:
                // Take Picture
                if(isIntentAvailable(getApplicationContext(), MediaStore.ACTION_IMAGE_CAPTURE)){
                    dispatchTakePictureIntent(1);
                }else{
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
                crosshairMarker.remove();
                option = SelectedOption.NONE;
                break;


        }
    }

    private void showPictureDialogFragment() {
        FragmentManager fm = getSupportFragmentManager();
        PictureDialogFragment fragment = new PictureDialogFragment();
        //fragment.show(fm, "picture_dialog_fragment");
    }


    private void dispatchTakePictureIntent(int actionCode) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, actionCode);
    }

    public static boolean isIntentAvailable(Context context, String action) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(action);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    private void handleSmallCameraPhoto(Intent intent) {
        Bundle extras = intent.getExtras();
        Bitmap mImageBitmap = (Bitmap) extras.get("data");
        //mImageView.setImageBitmap(mImageBitmap);
    }

	Double calcDistance(LatLng pos1, LatLng pos2) {

		int R = 6371; // km earth radius
		double dLat = Math.toRadians(pos2.latitude - pos1.latitude);
		double dLon = Math.toRadians(pos2.longitude - pos1.longitude);
		double lat1 = Math.toRadians(pos1.latitude);
		double lat2 = Math.toRadians(pos2.latitude);

		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		return R * c;
	}

    public boolean onPrepareOptionsMenu (LatLng latLng)
    {

        //MenuInflater inflater = getMenuInflater();
        //getActionBar().

        //TextView title  = (TextView) findViewById(R.id.coordinates);
        //menu.getItem(0).setTitle("Lat: " + latLng.latitude + " Lng: " + latLng.longitude);

        return true;
    }

    private void goToLastKnownLocation(float ZoomLevel) {
        // get current position
        LatLng coordinate = getLastKnownLocation(false);

        if(coordinate == null){
            Toast.makeText(getApplicationContext(), getString(R.string.noLocationSignal), Toast.LENGTH_SHORT).show();
        }else{
            // set map view to current position
            CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, ZoomLevel);
            map.animateCamera(yourLocation);
        }
    }

    private LatLng getLastKnownLocation(boolean enabledProvidersOnly){

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = null;
        List<String> providers = manager.getProviders(enabledProvidersOnly);

        for(String provider : providers){
            location = manager.getLastKnownLocation(provider);
            //maybe try adding some Criteria here
            if(location != null) return new LatLng(location.getLatitude(), location.getLongitude());
        }
        //at this point we've done all we can and no location is returned
        return null;
    }
}
