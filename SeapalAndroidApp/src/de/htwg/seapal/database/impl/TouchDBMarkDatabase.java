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

import de.htwg.seapal.database.IMarkDatabase;
import de.htwg.seapal.model.IMark;
import de.htwg.seapal.model.impl.Mark;

@ContextSingleton
public class TouchDBMarkDatabase implements IMarkDatabase {

	private static final String TAG = "Mark-TouchDB";
	private static final String DATABASE_NAME = "seapal_mark_db";

	private static TouchDBMarkDatabase touchDBMarkDatabase;
	private CouchDbConnector couchDbConnector;
	private TouchDBHelper dbHelper;

	@Inject
	public TouchDBMarkDatabase(Context ctx) {
		dbHelper = new TouchDBHelper(DATABASE_NAME);
		dbHelper.createDatabase(ctx);
		dbHelper.pullFromDatabase();
		couchDbConnector = dbHelper.getCouchDbConnector();
	}

	public static TouchDBMarkDatabase getInstance(Context ctx) {
		if (touchDBMarkDatabase == null)
			touchDBMarkDatabase = new TouchDBMarkDatabase(ctx);
		return touchDBMarkDatabase;
	}

	@Override
	public boolean open() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UUID create() {
		IMark mark = new Mark();
		try {
			couchDbConnector.create(mark.getId(), mark);
		} catch (UpdateConflictException e) {
			Log.e(TAG, e.toString());
		}
		UUID id = UUID.fromString(mark.getId());
		Log.d(TAG, "Mark created: " + mark.getId());
		dbHelper.pushToDatabase();
		return id;
	}

	@Override
	public boolean save(IMark data) {
		try {
			couchDbConnector.update(data);
			dbHelper.pushToDatabase();
		} catch (DocumentNotFoundException e) {
			Log.d(TAG, "Document not Found");
			Log.d(TAG, e.toString());
			return false;
		}
		Log.d(TAG, "Mark saved: " + data.getId());
		return true;
	}

	@Override
	public IMark get(UUID id) {
		IMark mark;
		try {
			mark = couchDbConnector.get(Mark.class, id.toString());
		} catch (DocumentNotFoundException e) {
			Log.e(TAG, "Mark not found" + id.toString());
			return null;
		}
		return mark;
	}

	@Override
	public List<IMark> loadAll() {
		List<IMark> lst = new LinkedList<IMark>();
		List<String> log = new LinkedList<String>();
		ViewQuery query = new ViewQuery().allDocs();
		ViewResult vr = couchDbConnector.queryView(query);

		for (Row r : vr.getRows()) {
			lst.add(get(UUID.fromString(r.getId())));
			log.add(r.getId());
		}
		Log.d(TAG, "All Marks: " + log.toString());
		return lst;
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
		Log.d(TAG, "Mark deleted");
	}

	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		return false;
	}

}
