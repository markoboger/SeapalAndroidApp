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

import android.app.Application;
import android.util.Log;

import com.google.inject.Inject;

import de.htwg.seapal.database.IWaypointDatabase;
import de.htwg.seapal.model.IWaypoint;
import de.htwg.seapal.model.IWaypoint.ForeSail;
import de.htwg.seapal.model.IWaypoint.MainSail;
import de.htwg.seapal.model.IWaypoint.Maneuver;
import de.htwg.seapal.model.impl.Waypoint;

public class TouchDBWaypointDatabase implements IWaypointDatabase {

	private static final String TAG = "Waypoint-TouchDB";
	private static final String DDOCNAME = "seapal-waypoint";
	private static final String VIEWNAME = "waypoint";
	private static final String DATABASE_NAME = "seapal_waypoint_db";

	private static TouchDBWaypointDatabase touchDBWaypointDatabase;
	private CouchDbConnector couchDbConnector;
	private TouchDBHelper dbHelper;

	@Inject
	public TouchDBWaypointDatabase(Application ctx) {
		dbHelper = new TouchDBHelper(VIEWNAME, DATABASE_NAME, DDOCNAME);
		dbHelper.createDatabase(ctx);
		dbHelper.pullFromDatabase();
		couchDbConnector = dbHelper.getCouchDbConnector();

	}

	public static TouchDBWaypointDatabase getInstance(Application ctx) {
		if (touchDBWaypointDatabase == null)
			touchDBWaypointDatabase = new TouchDBWaypointDatabase(ctx);
		return touchDBWaypointDatabase;
	}

	@Override
	public UUID create() {
		IWaypoint waypoint = new Waypoint(Maneuver.NONE, ForeSail.NONE,
				MainSail.NONE);
		try {
			couchDbConnector.create(waypoint.getId(), waypoint);
		} catch (UpdateConflictException e) {
			Log.e(TAG, e.toString());
		}
		UUID id = UUID.fromString(waypoint.getId());
		Log.d(TAG, "Waypoint created: " + waypoint.getId());
		dbHelper.pushToDatabase();
		return id;
	}

	@Override
	public boolean save(IWaypoint waypoint) {
		try {
			couchDbConnector.update(waypoint);
			dbHelper.pushToDatabase();
		} catch (DocumentNotFoundException e) {
			Log.d(TAG, "Document not Found");
			Log.d(TAG, e.toString());
			return false;
		}
		Log.d(TAG, "Waypoint saved: " + waypoint.getId());
		return true;
	}

	@Override
	public void delete(UUID id) {
		try {
			couchDbConnector.delete(get(id));
			dbHelper.pushToDatabase();
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			return;
		}
		Log.d(TAG, "Waypoint deleted");
	}

	@Override
	public IWaypoint get(UUID id) {
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
	public List<IWaypoint> loadAll() {
		List<IWaypoint> lst = new LinkedList<IWaypoint>();
		List<String> log = new LinkedList<String>();
		ViewQuery query = new ViewQuery().allDocs();
		ViewResult vr = couchDbConnector.queryView(query);

		for (Row r : vr.getRows()) {
			lst.add(get(UUID.fromString(r.getId())));
			log.add(r.getId());
		}
		Log.d(TAG, "All Waypoints: " + log.toString());
		return lst;
	}

	@Override
	public boolean close() {
		return false;
	}

	@Override
	public boolean open() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IWaypoint> loadAllByTripId(UUID arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
