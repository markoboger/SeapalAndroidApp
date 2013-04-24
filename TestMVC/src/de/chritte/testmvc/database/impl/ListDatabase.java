package de.chritte.testmvc.database.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.chritte.testmvc.database.IDatabase;
import de.chritte.testmvc.model.IBoat;
import de.chritte.testmvc.model.impl.Boat;

public class ListDatabase implements IDatabase {
	
	Map<UUID, IBoat> db = new HashMap<UUID, IBoat>();

	@Override
	public UUID newBoat() {
		IBoat boat = new Boat();
		UUID id = boat.getId();
		db.put(id, boat);
		return id;
	}

	@Override
	public void saveBoat(IBoat boat) {
		db.put(boat.getId(), boat);
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
		List<IBoat> values = (List<IBoat>) db.values();
		return values;
	}

	@Override
	public boolean closeDB() {
		return true;
	}

}
