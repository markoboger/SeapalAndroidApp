package de.htwg.seapal.aview.gui.activity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;
import de.htwg.seapal.R;
import de.htwg.seapal.controller.IMarkController;
import de.htwg.seapal.controller.IWaypointController;

public class MapActivity extends BaseDrawerActivity implements OnMapLongClickListener, OnMapClickListener, OnMarkerClickListener {

	@Inject
	private IMarkController controller;
	@Inject
	private IWaypointController wController;
	private GoogleMap map;
	private View view;
	private Marker crosshairMarker = null;

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
		view = myMapFragment.getView();
		registerForContextMenu(view);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, 
			ContextMenuInfo menuInfo) {
		menu.add(Menu.NONE, 1, Menu.NONE, "MARK");
		menu.add(Menu.NONE, 2, Menu.NONE, "Remove");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			Toast.makeText(getApplicationContext(), "mark", Toast.LENGTH_LONG).show();
			return true;
		case 2:
			Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_LONG).show();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	@Override
	public void onMapClick(LatLng arg0) {
		if(crosshairMarker != null) {
			crosshairMarker.remove();
		}
		crosshairMarker = map.addMarker(new MarkerOptions()
				.position(arg0)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.haircross))
				.title(arg0.toString())
				.snippet(arg0.toString()));
		crosshairMarker.setDraggable(true);
		
	}

	@Override
	public void onMapLongClick(LatLng arg0) {
		openContextMenu(view);
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		openContextMenu(view);
		return false;
	}
}

//map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
//.getMap();
//
////List<IMark> lst = controller.getAllMarks();
////for(IMark m : lst) {
////map.addMarker(new MarkerOptions().position(new LatLng(m.getLatitude(), m.getLongitude())).title(m.getName()));
////}
////List<IWaypoint> lstW = wController.getAllWaypoints();
////
////Polyline line = map.addPolyline(new PolylineOptions()
////.add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
////.width(5)
////.color(Color.RED));
////
////Polyline line2;
////
////for(IWaypoint w : lstW) {
////line = map.addPolyline(new PolylineOptions().
////	add(new LatLng(w.getLatitude(), w.getLongitude())).
////	width(5).color(Color.RED));
////}