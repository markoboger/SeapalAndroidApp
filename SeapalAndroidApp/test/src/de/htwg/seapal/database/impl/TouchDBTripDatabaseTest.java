

package de.htwg.seapal.database.impl;

import java.util.List;
import java.util.UUID;

import com.couchbase.touchdb.router.TDURLStreamHandlerFactory;

import de.htwg.seapal.model.ITrip;
import android.content.Context;
import android.test.AndroidTestCase;

public class TouchDBTripDatabaseTest extends AndroidTestCase {

	{
		TDURLStreamHandlerFactory.registerSelfIgnoreError();
	}
	TouchDBTripDatabase db;
	UUID id;

	protected void setUp() throws Exception {
		super.setUp();
		Context ctx = getContext().getApplicationContext();
		db = TouchDBTripDatabase.getInstance(ctx);
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
		ITrip trip = db.get(id);
		trip.setName("TestTrip");
		db.save(trip);
		assertEquals("TestTrip", db.get(id).getName());
	}

	public void delete() {
		db.delete(id);
		assertNull(db.get(id));
	}

	public void get() {
		assertEquals(id, db.get(id).getUUID());
	}

	public void testLoadAll() {
		List<ITrip> lst = db.loadAll();
		for(ITrip trip: lst) {
			assertNotNull(db.get(trip.getUUID()));
		}
	}

}



