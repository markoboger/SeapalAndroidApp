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

import roboguice.inject.ContextSingleton;
import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;

import de.htwg.seapal.database.IBoatDatabase;
import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.model.impl.Boat;

@ContextSingleton
public class TouchDBBoatDatabase implements IBoatDatabase {

	private static final String TAG = "Boat-TouchDB";
	private static final String DDOCNAME = "seapal-boats";
	private static final String VIEWNAME = "boats";
	private static final String DATABASE_NAME = "seapal_boats_db";

	private static TouchDBBoatDatabase touchDBBoatDatabase;
	private CouchDbConnector couchDbConnector;
	private TouchDBHelper dbHelper;

	@Inject
	public TouchDBBoatDatabase(Context ctx) {
		dbHelper = new TouchDBHelper(DATABASE_NAME);
		dbHelper.createDatabase(ctx);
		dbHelper.pullFromDatabase();
		couchDbConnector = dbHelper.getCouchDbConnector();

	}

	public static TouchDBBoatDatabase getInstance(Context ctx) {
		if (touchDBBoatDatabase == null)
			touchDBBoatDatabase = new TouchDBBoatDatabase(ctx);
		return touchDBBoatDatabase;
	}

	@Override
	public UUID create() {
		IBoat boat = new Boat();
		try {
			couchDbConnector.create(boat.getId(), boat);
		} catch (UpdateConflictException e) {
			Log.e(TAG, e.toString());
		}
		UUID id = UUID.fromString(boat.getId());
		Log.d(TAG, "Boat created: " + boat.getId());
		dbHelper.pushToDatabase();
		return id;
	}

	@Override
	public boolean save(IBoat boat) {
		try {
			couchDbConnector.update(boat);
			dbHelper.pushToDatabase();
		} catch (DocumentNotFoundException e) {
			Log.d(TAG, "Document not Found");
			Log.d(TAG, e.toString());
			return false;
		}
		Log.d(TAG, "Boat saved: " + boat.getId());
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
		Log.d(TAG, "Boat deleted");
	}

	@Override
	public IBoat get(UUID id) {
		IBoat boat;
		try {
			boat = couchDbConnector.get(Boat.class, id.toString());
		} catch (DocumentNotFoundException e) {
			Log.e(TAG, "Boat not found" + id.toString());
			return null;
		}
		return boat;
	}

	@Override
	public List<IBoat> loadAll() {
		List<IBoat> lst = new LinkedList<IBoat>();
		List<String> log = new LinkedList<String>();
		ViewQuery query = new ViewQuery().allDocs();
		ViewResult vr = couchDbConnector.queryView(query);

		for (Row r : vr.getRows()) {
			lst.add(get(UUID.fromString(r.getId())));
			log.add(r.getId());
		}
		Log.d(TAG, "All Boats: " + log.toString());
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

}
