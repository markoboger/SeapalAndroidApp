package de.htwg.seapal.aview.gui.activity;

import android.app.FragmentManager;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import de.htwg.seapal.R;

import roboguice.activity.RoboActivity;

public class MyMapActivity extends RoboActivity 
implements OnMapClickListener, OnMapLongClickListener{

	final int RQS_GooglePlayServices = 1;
	private GoogleMap myMap;

	Location myLocation;
	TextView tvLocInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

//		tvLocInfo = (TextView)findViewById(R.id.locinfo);

		FragmentManager myFragmentManager = getFragmentManager();
		MapFragment myMapFragment 
		= (MapFragment)myFragmentManager.findFragmentById(R.id.map);
		myMap = myMapFragment.getMap();

		myMap.setMyLocationEnabled(true);

		myMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		//myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		//myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		//myMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

		myMap.setOnMapClickListener(this);
		myMap.setOnMapLongClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.actionbar, menu);
		return true;
	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//		case R.id.menu_legalnotices:
//			String LicenseInfo = GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(
//					getApplicationContext());
//			AlertDialog.Builder LicenseDialog = new AlertDialog.Builder(MainActivity.this);
//			LicenseDialog.setTitle("Legal Notices");
//			LicenseDialog.setMessage(LicenseInfo);
//			LicenseDialog.show();
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

		if (resultCode == ConnectionResult.SUCCESS){
			Toast.makeText(getApplicationContext(), 
					"isGooglePlayServicesAvailable SUCCESS", 
					Toast.LENGTH_LONG).show();
		}else{
			GooglePlayServicesUtil.getErrorDialog(resultCode, this, RQS_GooglePlayServices);
		}

	}

	@Override
	public void onMapClick(LatLng point) {
		tvLocInfo.setText(point.toString());
		myMap.animateCamera(CameraUpdateFactory.newLatLng(point));
	}

	@Override
	public void onMapLongClick(LatLng point) {
		tvLocInfo.setText("New marker added@" + point.toString());
		myMap.addMarker(new MarkerOptions().position(point).title(point.toString()));
	}

}
