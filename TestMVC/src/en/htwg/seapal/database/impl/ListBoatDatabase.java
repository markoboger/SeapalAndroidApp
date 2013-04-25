package en.htwg.seapal.database.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import en.htwg.seapal.database.IBoatDatabase;
import en.htwg.seapal.model.IBoat;
import en.htwg.seapal.model.impl.Boat;

public class ListBoatDatabase implements IBoatDatabase {
	
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
		Collection<IBoat> collection = db.values();
		List<IBoat> values =  new ArrayList<IBoat>(collection);
		return values;
	}

	@Override
	public boolean closeDB() {
		return true;
	}

}
