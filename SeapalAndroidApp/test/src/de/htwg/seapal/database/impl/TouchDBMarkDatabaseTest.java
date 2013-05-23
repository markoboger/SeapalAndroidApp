package de.htwg.seapal.database.impl;

import java.util.List;
import java.util.UUID;

import com.couchbase.touchdb.router.TDURLStreamHandlerFactory;

import de.htwg.seapal.model.IMark;
import android.content.Context;
import android.test.AndroidTestCase;

public class TouchDBMarkDatabaseTest extends AndroidTestCase {

	{
		TDURLStreamHandlerFactory.registerSelfIgnoreError();
	}
	TouchDBMarkDatabase db;
	UUID id;

	protected void setUp() throws Exception {
		super.setUp();
		Context ctx = getContext().getApplicationContext();
		db = TouchDBMarkDatabase.getInstance(ctx);
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
		IMark mark = db.get(id);
		mark.setName("TestMark");
		db.save(mark);
		assertEquals("TestMark", db.get(id).getName());
	}

	public void delete() {
		db.delete(id);
		assertNull(db.get(id));
	}

	public void get() {
		assertEquals(id, db.get(id).getUUID());
	}

	public void testLoadAll() {
		List<IMark> lst = db.loadAll();
		for(IMark mark: lst) {
			assertNotNull(db.get(mark.getUUID()));
		}
	}

}
