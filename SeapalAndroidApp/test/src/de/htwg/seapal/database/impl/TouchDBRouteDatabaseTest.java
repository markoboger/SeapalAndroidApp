package de.htwg.seapal.database.impl;

import java.util.List;
import java.util.UUID;

import com.couchbase.touchdb.router.TDURLStreamHandlerFactory;

import de.htwg.seapal.model.IRoute;
import android.content.Context;
import android.test.AndroidTestCase;

public class TouchDBRouteDatabaseTest extends AndroidTestCase {


	{
		TDURLStreamHandlerFactory.registerSelfIgnoreError();
	}
	TouchDBRouteDatabase db;
	UUID id;

	protected void setUp() throws Exception {
		super.setUp();
		Context ctx = getContext().getApplicationContext();
		db = TouchDBRouteDatabase.getInstance(ctx);
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
		IRoute route = db.get(id);
		route.setName("TestRoute");
		db.save(route);
		assertEquals("TestRoute", db.get(id).getName());
	}

	public void delete() {
		db.delete(id);
		assertNull(db.get(id));
	}

	public void get() {
		assertEquals(id, db.get(id).getUUID());
	}

	public void testLoadAll() {
		List<IRoute> lst = db.loadAll();
		for(IRoute person: lst) {
			assertNotNull(db.get(person.getUUID()));
		}
	}

}
