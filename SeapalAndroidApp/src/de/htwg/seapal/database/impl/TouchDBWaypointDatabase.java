package de.htwg.seapal.database.impl;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.Database;
import com.couchbase.lite.Emitter;
import com.couchbase.lite.Mapper;
import com.couchbase.lite.View;
import com.google.inject.Inject;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.UpdateConflictException;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.ViewResult.Row;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.htwg.seapal.database.IWaypointDatabase;
import de.htwg.seapal.model.IWaypoint;
import de.htwg.seapal.model.ModelDocument;
import de.htwg.seapal.model.impl.Waypoint;
import roboguice.inject.ContextSingleton;


@ContextSingleton
public class TouchDBWaypointDatabase implements IWaypointDatabase {

	private static final String TAG = "Waypoint-TouchDB";
	private static final String DDOCNAME = "Waypoint";
	private static final String VIEWNAME = "by_trip";
	private static final String DATABASE_NAME = "seapal_waypoint_db";

	private static TouchDBWaypointDatabase touchDBWaypointDatabase;
	private final CouchDbConnector couchDbConnector;
	private final TouchDBHelper dbHelper;

	@Inject
	public TouchDBWaypointDatabase(Context ctx) {
		dbHelper = new TouchDBHelper(DATABASE_NAME);
		dbHelper.createDatabase(ctx);
		dbHelper.pullFromDatabase();
		couchDbConnector = dbHelper.getCouchDbConnector();

		Database tdDB = dbHelper.getTDDatabase();

		View view = tdDB.getView(String.format("%s/%s", DDOCNAME,
                VIEWNAME));

		view.setMapReduce(new Mapper() {
            @Override
            public void map(Map<String, Object> document,
                            Emitter emitter) {
                Object Trip = document.get("trip");
                Map<Object, Object> m = new HashMap<Object, Object>();
                if (Trip != null) {
                    m.put(document.get("trip"), document.get("date"));
                    emitter.emit(m, document.get("_id"));
                }

            }
        }, null, "1.0");

	}

	public static TouchDBWaypointDatabase getInstance(Context ctx) {
		if (touchDBWaypointDatabase == null)
			touchDBWaypointDatabase = new TouchDBWaypointDatabase(ctx);
		return touchDBWaypointDatabase;
	}

	@Override
	public UUID create() {
		IWaypoint waypoint = new Waypoint();
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
			if (r.getId().contains("_design")) {
				continue;
			}
			lst.add(get(UUID.fromString(r.getId())));
			log.add(get(UUID.fromString(r.getId())).toString());
		}
		Log.d(TAG, "All Waypoints: " + lst.size());
		Log.d(TAG, "All Waypoints: " + log.toString());

		return lst;
	}

	@Override
	public boolean close() {
		return false;
	}

    @Override
    public void create(ModelDocument modelDocument) {

    }

    @Override
    public List<? extends IWaypoint> queryViews(String s, String s2) {
        return null;
    }

    @Override
    public void update(ModelDocument modelDocument) {

    }

    @Override
	public boolean open() {
		// TODO Auto-generated method stub
		return false;
	}

	public List<IWaypoint> findByTrip(UUID tripId) {
		List<IWaypoint> lst = new LinkedList<IWaypoint>();
		List<IWaypoint> log = new LinkedList<IWaypoint>();

		ViewQuery viewQuery = new ViewQuery()
				.designDocId("_design/" + DDOCNAME).viewName(VIEWNAME);

		ViewResult vr = couchDbConnector.queryView(viewQuery);
		for (Row r : vr.getRows()) {

			String[] s = r.getKey().split(":");
			s[0] = s[0].replace("\"", "");
			s[0] = s[0].replace("{", "");

			if (r.getKey() != null && !r.getKey().isEmpty()) {
				if (tripId.equals(UUID.fromString(s[0]))) {
					lst.add(get(UUID.fromString(r.getValue())));
					log.add(get(UUID.fromString(r.getValue())));
				}
			}

		}
		Log.d(TAG, "All Trips: " + log.toString());
		return lst;
	}

}
