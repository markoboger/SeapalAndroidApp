package de.htwg.seapal.database.impl.hashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.htwg.seapal.database.IMarkDatabase;
import de.htwg.seapal.model.IMark;
import de.htwg.seapal.model.impl.Mark;

public class HashMapMarkDatabase implements IMarkDatabase {

	private final Map<UUID, IMark> db;
	private static HashMapMarkDatabase hashMapMarkDatabase;

	private HashMapMarkDatabase() {
		db = new HashMap<UUID, IMark>();
	}

	public static HashMapMarkDatabase getInstance() {
		if (hashMapMarkDatabase == null)
			hashMapMarkDatabase = new HashMapMarkDatabase();
		return hashMapMarkDatabase;
	}

	@Override
	public UUID create() {
		IMark mark = new Mark();
		UUID id = mark.getUUID();
		db.put(id, mark);
		return id;
	}

	@Override
	public boolean save(IMark mark) {
		return db.put(mark.getUUID(), mark) == null;
	}

	@Override
	public void delete(UUID id) {
		db.remove(id);
	}

	@Override
	public IMark get(UUID id) {
		return db.get(id);
	}

	@Override
	public List<IMark> loadAll() {
		Collection<IMark> collection = db.values();
		return new ArrayList<IMark>(collection);
	}

	@Override
	public boolean close() {
		return true;
	}

	@Override
	public boolean open() {
		// TODO Auto-generated method stub
		return false;
	}
}
