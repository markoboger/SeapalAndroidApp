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

import de.htwg.seapal.database.IWaypointDatabase;
import de.htwg.seapal.model.IWaypoint;
import de.htwg.seapal.model.IWaypoint.ForeSail;
import de.htwg.seapal.model.IWaypoint.MainSail;
import de.htwg.seapal.model.IWaypoint.Maneuver;
import de.htwg.seapal.model.impl.Waypoint;

public class TouchDBWaypointDatabase implements	IWaypointDatabase {
	
	private static final String TAG = "Waypoint-TouchDB";
	private static final String DDOCNAME = "seapal-waypoint";
	private static final String VIEWNAME = "waypoint";
	private static final String DATABASE_NAME = "seapal_waypoint_db";
	
	private static TouchDBWaypointDatabase touchDBWaypointDatabase;
	private CouchDbConnector couchDbConnector;
	private TouchDBHelper dbHelper;

	public TouchDBWaypointDatabase(Context ctx) {
		dbHelper = new TouchDBHelper(VIEWNAME, DATABASE_NAME, DDOCNAME);
		dbHelper.createDatabase(ctx);
		dbHelper.pullFromDatabase();
		couchDbConnector = dbHelper.getCouchDbConnector();
		
	}
	public static TouchDBWaypointDatabase getInstance(Context ctx)	{
		if (touchDBWaypointDatabase == null)
			touchDBWaypointDatabase = new TouchDBWaypointDatabase(ctx);
		return touchDBWaypointDatabase;
	}

	@Override
	public UUID newWaypoint() {
		IWaypoint waypoint = new Waypoint(Maneuver.NONE, ForeSail.NONE, MainSail.NONE);
		try {
			couchDbConnector.create(waypoint.getId(), waypoint);
		} catch (UpdateConflictException e) {
			Log.e(TAG, e.toString());
		}
		UUID idx = UUID.fromString(waypoint.getId());
		Log.d(TAG, "Waypoint created: " + waypoint.getId());
		dbHelper.pushToDatabase();
		return idx;
	}

	@Override
	public void saveWaypoint(IWaypoint waypoint) {
		try {
			couchDbConnector.update(waypoint);
			dbHelper.pushToDatabase();
		} catch (DocumentNotFoundException e) {
			Log.d(TAG, "Document not Found");
			Log.d(TAG, e.toString());
			return;
		}
		Log.d(TAG, "Waypoint saved: " + waypoint.getId());		
	}

	@Override
	public void deleteWaypoint(UUID id) {
		try {
			couchDbConnector.delete(getWaypoint(id));
			dbHelper.pushToDatabase();
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			return;
		}
		Log.d(TAG, "Waypoint deleted");
	}

	@Override
	public IWaypoint getWaypoint(UUID id) {
		IWaypoint waypoint;
		try {
			waypoint = couchDbConnector.get(Waypoint.class, id.toString());
		} catch (DocumentNotFoundException e) {
			Log.e(TAG, "Waypoint not found" + id.toString());
			return null;
		}
		return waypoint;
	}

	@Override
	public List<IWaypoint> getWaypoints() {
		List<IWaypoint> lst = new LinkedList<IWaypoint>();
		List<String> log = new LinkedList<String>();
		ViewQuery query = new ViewQuery().allDocs();		
		ViewResult vr = couchDbConnector.queryView(query);
		
		
		for(Row r : vr.getRows()) {
			lst.add(getWaypoint(UUID.fromString(r.getId())));
			log.add(r.getId());
		}
		Log.d(TAG, "All Waypoints: " + log.toString());
		return lst;
	}

	@Override
	public boolean closeDB() {
		return false;
	}

}
