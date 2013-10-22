package de.htwg.seapal.database.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.UpdateConflictException;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.ViewResult.Row;

import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.util.Log;

import com.couchbase.touchdb.TDDatabase;
import com.couchbase.touchdb.TDView;
import com.couchbase.touchdb.TDViewMapBlock;
import com.couchbase.touchdb.TDViewMapEmitBlock;
import com.google.inject.Inject;

import de.htwg.seapal.database.ITripDatabase;
import de.htwg.seapal.model.ITrip;
import de.htwg.seapal.model.impl.Trip;

@ContextSingleton
public class TouchDBTripDatabase implements ITripDatabase {

	private static final String TAG = "Trip-TouchDB";
	private static final String DDOCNAME = "Trip";
	private static final String VIEWNAME = "by_boat";
	private static final String DATABASE_NAME = "seapal_trips_db";

	private static TouchDBTripDatabase touchDBTripDatabase;
	private final CouchDbConnector couchDbConnector;
	private final TouchDBHelper dbHelper;

	@Inject
	public TouchDBTripDatabase(Context ctx) {
		dbHelper = new TouchDBHelper(DATABASE_NAME);
		dbHelper.createDatabase(ctx);
		dbHelper.pullFromDatabase();
		couchDbConnector = dbHelper.getCouchDbConnector();

		TDDatabase tdDB = dbHelper.getTDDatabase();

		TDView view = tdDB.getViewNamed(String.format("%s/%s", DDOCNAME,
				VIEWNAME));

		view.setMapReduceBlocks(new TDViewMapBlock() {
			@Override
			public void map(Map<String, Object> document,
					TDViewMapEmitBlock emitter) {
				Object Boat = document.get("boat");
				if (Boat != null) {
					emitter.emit(document.get("boat"), document.get("_id"));
				}

			}
		}, null, "1.0");

	}

	public static TouchDBTripDatabase getInstance(Context ctx) {
		if (touchDBTripDatabase == null)
			touchDBTripDatabase = new TouchDBTripDatabase(ctx);
		return touchDBTripDatabase;
	}

	@Override
	public UUID create() {
		ITrip trip = new Trip();
		try {
			couchDbConnector.create(trip.getId(), trip);
		} catch (UpdateConflictException e) {
			Log.e(TAG, e.toString());
		}
		UUID id = UUID.fromString(trip.getId());
		Log.d(TAG, "Trip created: " + trip.getId());
		dbHelper.pushToDatabase();
		return id;
	}

	@Override
	public boolean save(ITrip trip) {
		try {
			couchDbConnector.update(trip);
			dbHelper.pushToDatabase();
		} catch (DocumentNotFoundException e) {
			Log.d(TAG, "Document not Found");
			Log.d(TAG, e.toString());
			return false;
		}
		Log.d(TAG, "Trip saved: " + trip.getId());
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
		Log.d(TAG, "Trip deleted");
	}

	@Override
	public ITrip get(UUID id) {
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
	public List<ITrip> loadAll() {
		List<ITrip> lst = new LinkedList<ITrip>();
		List<String> log = new LinkedList<String>();
		ViewQuery query = new ViewQuery().allDocs();
		ViewResult vr = couchDbConnector.queryView(query);

		for (Row r : vr.getRows()) {
			lst.add(get(UUID.fromString(r.getId())));
			log.add(r.getId());
		}
		Log.d(TAG, "All Trips: " + log.toString());
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
	public List<ITrip> findByBoat(UUID boatId) {
		List<ITrip> lst = new LinkedList<ITrip>();
		List<ITrip> log = new LinkedList<ITrip>();

		ViewQuery viewQuery = new ViewQuery()
				.designDocId("_design/" + DDOCNAME).viewName(VIEWNAME);

		ViewResult vr = couchDbConnector.queryView(viewQuery);
		for (Row r : vr.getRows()) {

			if (r.getKey() != null && !r.getKey().isEmpty()) {
				if (boatId.equals(UUID.fromString(r.getKey()))) {
					lst.add(get(UUID.fromString(r.getValue())));
					log.add(get(UUID.fromString(r.getValue())));
				}
			}

		}
		Log.d(TAG, "All Trips: " + log.toString());
		return lst;
	}

}