package de.htwg.seapal.database.impl.hashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.htwg.seapal.database.IBoatDatabase;
import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.model.impl.Boat;

public class HashMapBoatDatabase implements IBoatDatabase {

	private final Map<UUID, IBoat> db;
	private static HashMapBoatDatabase hashMapBoatDatabase;

	private HashMapBoatDatabase() {
		db = new HashMap<UUID, IBoat>();
	}

	public static HashMapBoatDatabase getInstance() {
		if (hashMapBoatDatabase == null)
			hashMapBoatDatabase = new HashMapBoatDatabase();
		return hashMapBoatDatabase;
	}

	@Override
	public UUID create() {
		IBoat boat = new Boat();
		UUID id = boat.getUUID();
		db.put(id, boat);
		return id;
	}

	@Override
	public boolean save(IBoat boat) {
		return db.put(boat.getUUID(), boat) == null;
	}

	@Override
	public void delete(UUID id) {
		db.remove(id);
	}

	@Override
	public IBoat get(UUID id) {
		return db.get(id);
	}

	@Override
	public List<IBoat> loadAll() {
		Collection<IBoat> collection = db.values();
		return new ArrayList<IBoat>(collection);
	}

	@Override
	public boolean close() {
		return true;
	}

	@Override
	public boolean open() {
		return false;
	}

}
