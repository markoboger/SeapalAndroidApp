package de.htwg.seapal.database.impl;

import java.util.List;
import java.util.UUID;

import com.couchbase.touchdb.router.TDURLStreamHandlerFactory;

import de.htwg.seapal.model.IPerson;
import android.content.Context;
import android.test.AndroidTestCase;

public class TouchDBPersonDatabaseTest extends AndroidTestCase {

	{
		TDURLStreamHandlerFactory.registerSelfIgnoreError();
	}
	TouchDBPersonDatabase db;
	UUID id;

	protected void setUp() throws Exception {
		super.setUp();
		Context ctx = getContext().getApplicationContext();
		db = TouchDBPersonDatabase.getInstance(ctx);
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
		IPerson person = db.get(id);
		person.setFirstname("Testperson");
		db.save(person);
		assertEquals("Testperson", db.get(id).getFirstname());
	}

	public void delete() {
		db.delete(id);
		assertNull(db.get(id));
	}

	public void get() {
		assertEquals(id, db.get(id).getUUID());
	}

	public void testLoadAll() {
		List<IPerson> lst = db.loadAll();
		for(IPerson person: lst) {
			assertNotNull(db.get(person.getUUID()));
		}
	}

}
