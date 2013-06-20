package de.htwg.seapal.database.impl.hashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.htwg.seapal.database.ITripDatabase;
import de.htwg.seapal.model.ITrip;
import de.htwg.seapal.model.impl.Trip;

public class HashMapTripDatabase implements ITripDatabase {

	private Map<UUID, ITrip> db;;
	private static HashMapTripDatabase hashMapTripDatabase;

	private HashMapTripDatabase() {
		db = new HashMap<UUID, ITrip>();
	}

	public static HashMapTripDatabase getInstance() {
		if (hashMapTripDatabase == null)
			hashMapTripDatabase = new HashMapTripDatabase();
		return hashMapTripDatabase;
	}

	@Override
	public UUID create() {
		ITrip trip = new Trip();
		UUID id = UUID.fromString(trip.getId());
		db.put(id, trip);
		return id;
	}

	@Override
	public boolean save(ITrip trip) {
		return db.put(UUID.fromString(trip.getId()), trip) == null;
	}

	@Override
	public void delete(UUID id) {
		db.remove(id);
	}

	@Override
	public ITrip get(UUID id) {
		return db.get(id);
	}

	@Override
	public List<ITrip> loadAll() {
		Collection<ITrip> collection = db.values();
		List<ITrip> values = new ArrayList<ITrip>(collection);
		return values;
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

	@Override
	public List<ITrip> findByBoat(UUID boatId) {
		return null;
	}

}
