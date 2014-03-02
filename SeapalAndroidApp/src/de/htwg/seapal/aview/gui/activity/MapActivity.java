package de.htwg.seapal.aview.gui.activity;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.inject.Inject;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.adapter.SideDrawerListAdapter;
import de.htwg.seapal.controller.IMainController;
import de.htwg.seapal.events.map.OnMapRestoreInstanceEvent;
import de.htwg.seapal.events.map.OnMapSaveInstanceEvent;
import de.htwg.seapal.events.map.picturemanager.PlacePictureOnMapEvent;
import de.htwg.seapal.events.map.picturemanager.RequestTakePictureEvent;
import de.htwg.seapal.events.map.picturemanager.ShowPictureDialogEvent;
import de.htwg.seapal.events.map.trackingmanager.StartTrackingEvent;
import de.htwg.seapal.events.map.trackingmanager.StopTrackingEvent;
import de.htwg.seapal.manager.SessionManager;
import de.htwg.seapal.manager.map.AimDirectionManager;
import de.htwg.seapal.manager.map.PolylineManager;
import de.htwg.seapal.manager.map.TakePictureManager;
import de.htwg.seapal.manager.map.TrackingManager;
import de.htwg.seapal.manager.mapstate.Statelike;
import de.htwg.seapal.manager.mapstate.impl.DistanceState;
import de.htwg.seapal.manager.mapstate.impl.MarkState;
import de.htwg.seapal.manager.mapstate.impl.RouteDrawingState;
import roboguice.RoboGuice;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;


public class MapActivity extends BaseDrawerActivity implements OnMapLongClickListener, OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private static final String TAG = "MapActivity";

    @InjectView(R.id.drawer_menu_drawer_list_right)
    private ListView drawerListViewRight;

    @InjectResource(R.array.drawer_list_array_right)
    private String[] drawerActivityListRight;

    private DrawerLayout drawer;

    @Inject
    private IMainController mainController;
    @Inject
    private SessionManager sessionManager;

    /**
     * MapActivity State
     */
    private GoogleMap map;

    @Inject
    private Statelike state;

    /**
     * TrackingManager ist Stateful so it needs to be saved to Instance
     */
    @Inject
    private TrackingManager trackingManager;

    @Inject
    private PolylineManager polylineManager;

    @Inject
    private TakePictureManager pictureManager;

    @Inject
    private AimDirectionManager aimDirectionManager;

    private static enum SelectedOption {
        SEARCH, MARK, ROUTE, DISTANCE, GOTO_CURRENT, TAKE_PICTURE, PERSON_OVER_BOARD, MENU_ROUTE, MENU_MARK, MENU_DISTANCE, MENU_GOAL
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.drawer_menu_drawer_list_right) {
                SelectedOption option = SelectedOption.values()[position];
                switch (option) {
                    case SEARCH:
                        Log.i(TAG, "SEARCH");
                        break;
                    case MARK:
                        Log.i(TAG, "MARK");
                        state = RoboGuice.getInjector(MapActivity.this).getInstance(MarkState.class);
                        break;
                    case ROUTE:
                        Log.i(TAG, "ROUTE");
                        state = RoboGuice.getInjector(MapActivity.this).getInstance(RouteDrawingState.class);
                        break;
                    case DISTANCE:
                        Log.i(TAG, "DISTANCE");
                        state = RoboGuice.getInjector(MapActivity.this).getInstance(DistanceState.class);
                        break;
                    case GOTO_CURRENT:
                        goToLastKnownLocation(12);
                        Log.i(TAG, "GOTO_CURRENT");
                        break;
                    case TAKE_PICTURE:
                        eventManager.fire(new RequestTakePictureEvent(MapActivity.this));
                        Log.i(TAG, "TAKE_PICTURE");
                        break;
                    case PERSON_OVER_BOARD:
                        Log.i(TAG, "PERSON_OVER_BOARD");
                        break;
                }

                drawer.closeDrawer(Gravity.END);
            }
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

        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_menu_drawer_layout);

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

        setupDrawerForMapView();
        goToLastKnownLocation(9);

        if (savedInstanceState != null){
            onRestoreInstanceState(savedInstanceState);

        }
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

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        List<Statelike> l = new LinkedList<Statelike>();
        l.add(state);
        outState.putSerializable("map_state", (Serializable) l);
        eventManager.fire(new OnMapSaveInstanceEvent(outState));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        List<Statelike> s = (List<Statelike>) savedInstanceState.getSerializable("map_state");
        if (s !=null && !s.isEmpty()) {
            Statelike state = s.get(0);
            // needs to get injected because for listeners and observers
            this.state = RoboGuice.getInjector(this).getInstance(state.getClass());

        }
        eventManager.fire(new OnMapRestoreInstanceEvent(map, savedInstanceState));
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
        return super.onSearchRequested();
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_goTo:
                goToLastKnownLocation(12);
                break;
            case R.id.start_tracking:
                eventManager.fire(new StartTrackingEvent(this, map));
                break;
            case R.id.stop_tracking:
                eventManager.fire(new StopTrackingEvent(this));
                break;
            case R.id.action_show_right_drawer:
                toggleRightDrawer();
                break;
            case android.R.id.home:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onMapClick(LatLng latlng) {
        state.doAction(this, map, latlng);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == TakePictureManager.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            eventManager.fire(new PlacePictureOnMapEvent(this, map, data));
            eventManager.fire(new ShowPictureDialogEvent(this));

        }

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


}
