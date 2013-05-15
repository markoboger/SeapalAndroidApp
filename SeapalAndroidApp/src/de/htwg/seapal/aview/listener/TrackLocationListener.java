package de.htwg.seapal.aview.listener;

import java.util.UUID;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import de.htwg.seapal.controller.IWaypointController;

public class TrackLocationListener implements LocationListener {

	private UUID trip;
	private IWaypointController controller;

	public TrackLocationListener(UUID trip, IWaypointController controller) {
		this.trip = trip;
		this.controller = controller;
	}

	@Override
	public void onLocationChanged(Location location) {
		long date = System.currentTimeMillis() / 1000L;
		controller.newWaypoint(trip, date, location.getLongitude(), location.getLatitude());
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

}
