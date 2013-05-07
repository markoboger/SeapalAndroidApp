package de.htwg.seapal.aview.tui.activity;

import java.util.UUID;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import de.htwg.seapal.R;
import de.htwg.seapal.aview.listener.TrackLocationListener;
import de.htwg.seapal.aview.tui.states.recordTrip.StartState;
import de.htwg.seapal.controller.impl.WaypointController;
import de.htwg.seapal.database.impl.HashMapWaypointDatabase;
import de.htwg.seapal.database.impl.TouchDBWaypointDatabase;

public class TripRecordActivity extends AActivity {

	private LocationManager locationMgr;
	private WaypointController controller;
	private UUID trip;
	private LocationListener trackLocationListener;

	@Override
	protected void setup() {
		TextView header = (TextView) this.findViewById(R.id.header);
		header.setText("Trip Record");

		Bundle bundle = getIntent().getExtras();
		trip = UUID.fromString(bundle.getString("trip").toString());

//		controller = new WaypointController(
//				HashMapWaypointDatabase.getInstance());
		controller = new WaypointController(TouchDBWaypointDatabase.getInstance(getApplicationContext()));
		currenState = new StartState();
		controller.addObserver(this);

		trackLocationListener = new TrackLocationListener(trip, controller);
		locationMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	}

	public LocationManager getLocationMgr() {
		return locationMgr;
	}

	public LocationListener getTrackLocationListener() {
		return trackLocationListener;
	}

	public WaypointController getController() {
		return controller;
	}

	public UUID getTrip() {
		return trip;
	}
}
