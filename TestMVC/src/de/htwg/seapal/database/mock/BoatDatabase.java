package de.htwg.seapal.database.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.htwg.seapal.database.IBoatDatabase;
import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.model.impl.Boat;

public class BoatDatabase implements IBoatDatabase {

	Map<UUID, IBoat> db = new HashMap<UUID, IBoat>();
	private IBoat newBoat;

	public BoatDatabase() {
		UUID id = newBoatP();
		newBoat = getBoat(id);
		newBoat.setBoatName("Boat-NEW");
		saveBoat(newBoat);
		for (int i = 1; i < 10; i++) {
			id = newBoatP();
			IBoat boat = getBoatP(id);
			boat.setBoatName("Boat-" + i);
			saveBoat(boat);
		}
	}

	@Override
	public UUID newBoat() {
		return newBoat.getId();
	}

	private UUID newBoatP() {
		IBoat boat = new Boat();
		UUID id = boat.getId();
		db.put(id, boat);
		return id;
	}

	@Override
	public void saveBoat(IBoat boat) {
	}

	@Override
	public void deleteBoat(UUID id) {
	}

	@Override
	public IBoat getBoat(UUID id) {
		return new Boat(db.get(id));
	}

	private IBoat getBoatP(UUID id) {
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
