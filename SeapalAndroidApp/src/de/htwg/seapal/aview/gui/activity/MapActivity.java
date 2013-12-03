package de.htwg.seapal.aview.gui.activity;



import java.util.LinkedList;

import java.util.List;
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

import android.app.DialogFragment;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.fragment.MapDialogFragment;


public class MapActivity extends BaseDrawerActivity
implements OnMapLongClickListener, OnMapClickListener, OnMarkerClickListener, 
MapDialogFragment.MapDialogListener {

	private enum SelectedOption {
		NONE, MARK, ROUTE, DISTANCE, GOAL
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

        setupDrawerForMapView();
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
            case R.id.action_show_right_drawer:
                toggleRightDrawer();
                break;
            case android.R.id.home:
                closeRightDreawer();
                break;

        }
        return super.onMenuItemSelected(featureId, item);
    }

    private void closeRightDreawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_menu_drawer_layout);
        if(drawer.isDrawerOpen(Gravity.END)){
            drawer.closeDrawer(Gravity.END);
        }
    }

    private void toggleRightDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_menu_drawer_layout);
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
		default:
			break;
		}


	}

	@Override
	public void onMapLongClick(LatLng arg0) {
		option = SelectedOption.NONE;

		if(crosshairMarker != null) {
			crosshairMarker.remove();
		}
		route = null;
		crosshairMarker = map.addMarker(new MarkerOptions()
		.position(arg0)
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.haircross))
		.snippet(arg0.toString())
        .anchor(0.5f, 0.5f));

        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        v.vibrate(100);

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
		option = SelectedOption.NONE;
		crosshairMarker.remove();
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
