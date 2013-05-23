package de.htwg.seapal.database.impl;

import java.util.List;
import java.util.UUID;

import com.couchbase.touchdb.router.TDURLStreamHandlerFactory;

import de.htwg.seapal.model.IBoat;
import android.content.Context;
import android.test.AndroidTestCase;

public class TouchDBBoatDatabaseTest extends AndroidTestCase {
	
	{
	TDURLStreamHandlerFactory.registerSelfIgnoreError();
	}
	TouchDBBoatDatabase db;
	IBoat boat;
	UUID id;

	protected void setUp() throws Exception {
		super.setUp();
		Context ctx = getContext().getApplicationContext();
		db = TouchDBBoatDatabase.getInstance(ctx);
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
		IBoat boat = db.get(id);
		boat.setBoatName("TestBoat");
		db.save(boat);
		assertEquals("TestBoat", db.get(id).getBoatName());
	}

	public void delete() {
		db.delete(id);
		assertNull(db.get(id));
	}

	public void get() {
		assertEquals(id, db.get(id).getUUID());
	}

	public void testLoadAll() {
		List<IBoat> lst = db.loadAll();
		for(IBoat boat: lst) {
			assertNotNull(db.get(boat.getUUID()));
		}
	}

}
