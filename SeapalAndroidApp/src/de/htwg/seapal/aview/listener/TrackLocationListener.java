package de.htwg.seapal.aview.listener;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import java.util.UUID;

import de.htwg.seapal.controller.IMainController;
import de.htwg.seapal.model.impl.Trip;
import de.htwg.seapal.model.impl.Waypoint;

public class TrackLocationListener implements LocationListener {

	private final Trip trip;
	private final IMainController controller;

	public TrackLocationListener(UUID trip, IMainController controller) {
		this.trip = (Trip) controller.getSingleDocument("trip","", trip);
		this.controller = controller;
	}

	@Override
	public void onLocationChanged(Location location) {
		long date = System.currentTimeMillis() / 1000L;
        Waypoint w = new Waypoint();
        w.setDate(date);
        w.setLatitude(location.getLatitude());
        w.setLongitude(location.getLongitude());
		controller.creatDocument("waypoint", w, "");
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
