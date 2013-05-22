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
import android.app.Application;
import android.util.Log;

import com.google.inject.Inject;

import de.htwg.seapal.database.IRouteDatabase;
import de.htwg.seapal.model.IRoute;
import de.htwg.seapal.model.impl.Route;

@ContextSingleton
public class TouchDBRouteDatabase implements IRouteDatabase {

	private static final String TAG = "Route-TouchDB";
	private static final String DDOCNAME = "seapal-routes";
	private static final String VIEWNAME = "routes";
	private static final String DATABASE_NAME = "seapal_route_db";

	private static TouchDBRouteDatabase touchDBRouteDatabase;
	private CouchDbConnector couchDbConnector;
	private TouchDBHelper dbHelper;

	@Inject
	public TouchDBRouteDatabase(Application ctx) {
		dbHelper = new TouchDBHelper(VIEWNAME, DATABASE_NAME, DDOCNAME);
		dbHelper.createDatabase(ctx);
		dbHelper.pullFromDatabase();
		couchDbConnector = dbHelper.getCouchDbConnector();

	}

	public static TouchDBRouteDatabase getInstance(Application ctx) {
		if (touchDBRouteDatabase == null)
			touchDBRouteDatabase = new TouchDBRouteDatabase(ctx);
		return touchDBRouteDatabase;
	}

	@Override
	public boolean open() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UUID create() {
		IRoute route = new Route();
		try {
			couchDbConnector.create(route.getId(), route);
		} catch (UpdateConflictException e) {
			Log.e(TAG, e.toString());
		}
		UUID id = UUID.fromString(route.getId());
		Log.d(TAG, "Route created: " + route.getId());
		dbHelper.pushToDatabase();
		return id;
	}

	@Override
	public boolean save(IRoute data) {
		try {
			couchDbConnector.update(data);
			dbHelper.pushToDatabase();
		} catch (DocumentNotFoundException e) {
			Log.d(TAG, "Document not Found");
			Log.d(TAG, e.toString());
			return false;
		}
		Log.d(TAG, "Route saved: " + data.getId());
		return true;
	}

	@Override
	public IRoute get(UUID id) {
		IRoute route;
		try {
			route = couchDbConnector.get(Route.class, id.toString());
		} catch (DocumentNotFoundException e) {
			Log.e(TAG, "Boat not found" + id.toString());
			return null;
		}
		return route;
	}

	@Override
	public List<IRoute> loadAll() {
		List<IRoute> lst = new LinkedList<IRoute>();
		List<String> log = new LinkedList<String>();
		ViewQuery query = new ViewQuery().allDocs();
		ViewResult vr = couchDbConnector.queryView(query);

		for (Row r : vr.getRows()) {
			lst.add(get(UUID.fromString(r.getId())));
			log.add(r.getId());
		}
		Log.d(TAG, "All Routes: " + log.toString());
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
		Log.d(TAG, "Route deleted");

	}

	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		return false;
	}

}
