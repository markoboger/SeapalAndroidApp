package de.htwg.seapal.aview.tui.activity;

import java.util.UUID;

import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.inject.Inject;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.listener.TrackLocationListener;
import de.htwg.seapal.aview.tui.states.recordTrip.StartState;
import de.htwg.seapal.controller.IWaypointController;

public class TripRecordActivity extends AActivity {

	@Inject
	private LocationManager locationMgr;

	@Inject
	private IWaypointController controller;
	private UUID trip;
	private LocationListener trackLocationListener;

	@Override
	protected void setup() {
		TextView header = (TextView) this.findViewById(R.id.header);
		header.setText("Trip Record");

		Bundle bundle = getIntent().getExtras();
		trip = UUID.fromString(bundle.getString("trip").toString());

		currenState = new StartState();
		controller.addObserver(this);

		trackLocationListener = new TrackLocationListener(trip, controller);
	}

	public LocationManager getLocationMgr() {
		return locationMgr;
	}

	public LocationListener getTrackLocationListener() {
		return trackLocationListener;
	}

	public IWaypointController getController() {
		return controller;
	}

	public UUID getTrip() {
		return trip;
	}
}
