package de.htwg.seapal.database.impl;

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

	private Map<UUID, IMark> db;
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
	public UUID newMark() {
		IMark mark = new Mark();
		UUID id = mark.getId();
		db.put(id, mark);
		return id;
	}

	@Override
	public void saveMark(IMark mark) {
		db.put(mark.getId(), mark);
	}

	@Override
	public void deleteMark(UUID id) {
		db.remove(id);
	}

	@Override
	public IMark getMark(UUID id) {
		return db.get(id);
	}

	@Override
	public List<IMark> getMarks() {
		Collection<IMark> collection = db.values();
		List<IMark> values = new ArrayList<IMark>(collection);
		return values;
	}

	@Override
	public boolean closeDB() {
		return true;
	}
}
