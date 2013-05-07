package de.htwg.seapal.database.impl;

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

	private Map<UUID, IBoat> db ;
	private static HashMapBoatDatabase hashMapBoatDatabase;

	private HashMapBoatDatabase() {
		db = new HashMap<UUID, IBoat>();
	}

	public static HashMapBoatDatabase getInstance()	{
		if (hashMapBoatDatabase == null)
			hashMapBoatDatabase = new HashMapBoatDatabase();
		return hashMapBoatDatabase;
	}
	
	@Override
	public UUID newBoat() {
		IBoat boat = new Boat();
		UUID id = boat.getUUId();
		db.put(id, boat);
		return id;
	}

	@Override
	public void saveBoat(IBoat boat) {
		db.put(boat.getUUId(), boat);
	}

	@Override
	public void deleteBoat(UUID id) {
		db.remove(id);
	}

	@Override
	public IBoat getBoat(UUID id) {
		return db.get(id);
	}

	@Override
	public List<IBoat> getBoats() {
		Collection<IBoat> collection = db.values();
		List<IBoat> values = new ArrayList<IBoat>(collection);
		return values;
	}

	@Override
	public boolean closeDB() {
		return true;
	}

}
