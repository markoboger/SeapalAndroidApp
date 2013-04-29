package de.htwg.seapal.aview.listener;

import java.util.Calendar;
import java.util.UUID;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import de.htwg.seapal.controller.IWaypointController;

public class MyLocationListener implements LocationListener {

	private UUID trip;
	private IWaypointController controller;

	public MyLocationListener(UUID trip, IWaypointController controller) {
		this.trip = trip;
		this.controller = controller;
	}

	@Override
	public void onLocationChanged(Location location) {
		Calendar calendar = Calendar.getInstance();
		controller.newWaypoint(trip, location, calendar);
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
