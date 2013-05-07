package de.htwg.seapal.database.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.UpdateConflictException;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.ViewResult.Row;

import android.content.Context;
import android.util.Log;
import de.htwg.seapal.database.ITripDatabase;
import de.htwg.seapal.model.ITrip;
import de.htwg.seapal.model.impl.Trip;

public class TouchDBTripDatabase implements ITripDatabase {

	private static final String TAG = "Trip-TouchDB";
	private static final String DDOCNAME = "seapal-trips";
	private static final String VIEWNAME = "trips";
	private static final String DATABASE_NAME = "seapal_trips_db";
	
	private static TouchDBTripDatabase touchDBTripDatabase;
	private CouchDbConnector couchDbConnector;
	private TouchDBHelper dbHelper;

	public TouchDBTripDatabase(Context ctx) {
		dbHelper = new TouchDBHelper(VIEWNAME, DATABASE_NAME, DDOCNAME);
		dbHelper.createDatabase(ctx);
		dbHelper.pullFromDatabase();
		couchDbConnector = dbHelper.getCouchDbConnector();
		
	}
	public static TouchDBTripDatabase getInstance(Context ctx)	{
		if (touchDBTripDatabase == null)
			touchDBTripDatabase = new TouchDBTripDatabase(ctx);
		return touchDBTripDatabase;
	}
	@Override
	public UUID newTrip() {
		ITrip trip = new Trip();
		try {
			couchDbConnector.create(trip.getId(), trip);
		} catch (UpdateConflictException e) {
			Log.e(TAG, e.toString());
		}
		UUID idx = UUID.fromString(trip.getId());
		Log.d(TAG, "Trip created: " + trip.getId());
		dbHelper.pushToDatabase();
		return idx;
	}
	@Override
	public void saveTrip(ITrip trip) {
		try {
			couchDbConnector.update(trip);
			dbHelper.pushToDatabase();
		} catch (DocumentNotFoundException e) {
			Log.d(TAG, "Document not Found");
			Log.d(TAG, e.toString());
			return;
		}
		Log.d(TAG, "Trip saved: " + trip.getId());
	}
	@Override
	public void deleteTrip(UUID id) {
		try {
			couchDbConnector.delete(getTrip(id));
			dbHelper.pushToDatabase();
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			return;
		}
		Log.d(TAG, "Trip deleted");
	}
	@Override
	public ITrip getTrip(UUID id) {
		ITrip trip;
		try {
			trip = couchDbConnector.get(Trip.class, id.toString());
		} catch (DocumentNotFoundException e) {
			Log.e(TAG, "Trip not found" + id.toString());
			return null;
		}
		return trip;
	}
	@Override
	public List<ITrip> getTrips() {
		List<ITrip> lst = new LinkedList<ITrip>();
		List<String> log = new LinkedList<String>();
		ViewQuery query = new ViewQuery().allDocs();		
		ViewResult vr = couchDbConnector.queryView(query);
		
		
		for(Row r : vr.getRows()) {
			lst.add(getTrip(UUID.fromString(r.getId())));
			log.add(r.getId());
		}
		Log.d(TAG, "All Trips: " + log.toString());
		return lst;
	}
	@Override
	public boolean closeDB() {
		return false;
	}
	

}
