package de.htwg.seapal.database.impl;

import java.util.List;
import java.util.UUID;

import com.couchbase.touchdb.router.TDURLStreamHandlerFactory;

import de.htwg.seapal.model.IWaypoint;
import android.content.Context;
import android.test.AndroidTestCase;

public class TouchDBWaypointDatabaseTest extends AndroidTestCase {

	{
		TDURLStreamHandlerFactory.registerSelfIgnoreError();
	}
	TouchDBWaypointDatabase db;
	UUID id;

	protected void setUp() throws Exception {
		super.setUp();
		Context ctx = getContext().getApplicationContext();
		db = TouchDBWaypointDatabase.getInstance(ctx);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCRUD() {
		create();
		get();
		save();
		delete();
	}
	public void create() {
		id = db.create();
		assertEquals(id, db.get(id).getUUID());
	}

	public void save() {
		IWaypoint waypoint = db.get(id);
		waypoint.setName("TestWaypoint");
		db.save(waypoint);
		assertEquals("TestWaypoint", db.get(id).getName());
	}

	public void delete() {
		db.delete(id);
		assertNull(db.get(id));
	}

	public void get() {
		assertEquals(id, db.get(id).getUUID());
	}

	public void testLoadAll() {
		List<IWaypoint> lst = db.loadAll();
		for(IWaypoint waypoint: lst) {
			assertNotNull(db.get(waypoint.getUUID()));
		}
	}

}
