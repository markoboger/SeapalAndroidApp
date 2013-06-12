package de.htwg.seapal.aview.gui.activity;


import java.util.List;
import com.couchbase.touchdb.router.TDURLStreamHandlerFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.fragment.MapDialogFragment;
import de.htwg.seapal.controller.IMarkController;
import de.htwg.seapal.controller.IWaypointController;


public class MapActivity extends BaseDrawerActivity
						implements OnMapLongClickListener, OnMapClickListener, OnMarkerClickListener, 
									MapDialogFragment.MapDialogListener {

	private enum SelectedOption {
		NONE, MARK, ROUTE, DISTANCE, GOAL
	}

	// TODO here or in BaseDrawerActivity ????
	{
		TDURLStreamHandlerFactory.registerSelfIgnoreError();
	}

	@Inject
	private IMarkController controller;
	@Inject
	private IWaypointController wController;
	private GoogleMap map;
	public static Marker crosshairMarker = null;
	private Polyline route = null;
	private SelectedOption option = SelectedOption.NONE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		FragmentManager myFragmentManager = getFragmentManager();
		MapFragment myMapFragment 
		= (MapFragment)myFragmentManager.findFragmentById(R.id.map);
		map = myMapFragment.getMap();

		map.setMyLocationEnabled(true);

		map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		//myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		//myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		//myMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

		map.setOnMapClickListener(this);
		map.setOnMapLongClickListener(this);
		map.setOnMarkerClickListener(this);
	}

	@Override
	public void onMapClick(LatLng arg0) {
		switch (option) {
		case MARK:
			map.addMarker(new MarkerOptions().position(arg0));
			break;
		case ROUTE:
			List<LatLng> lst = route.getPoints();
			lst.add(arg0);
			route.setPoints(lst);
		default:
			break;
		}


	}

	@Override
	public void onMapLongClick(LatLng arg0) {

		if(crosshairMarker != null) {
			crosshairMarker.remove();
		}
		crosshairMarker = map.addMarker(new MarkerOptions()
		.position(arg0)
		.icon(BitmapDescriptorFactory.fromResource(R.drawable.haircross))
		.snippet(arg0.toString()));

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
		if(route == null) {
			route = map.addPolyline(new PolylineOptions().add(crosshairMarker.getPosition()).width(5).color(Color.BLUE));

		} else {
			List<LatLng> lst = route.getPoints();
			lst.add(crosshairMarker.getPosition());
			route.setPoints(lst);
		}

		crosshairMarker.remove();
	}

	@Override
	public void onDialogcalcDistanceClick(DialogFragment dialog) {
		option = SelectedOption.DISTANCE;
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
}
