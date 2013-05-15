package de.htwg.seapal.database.impl.hashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.htwg.seapal.database.IWaypointDatabase;
import de.htwg.seapal.model.IWaypoint;
import de.htwg.seapal.model.IWaypoint.ForeSail;
import de.htwg.seapal.model.IWaypoint.MainSail;
import de.htwg.seapal.model.IWaypoint.Maneuver;
import de.htwg.seapal.model.impl.Waypoint;

public class HashMapWaypointDatabase implements IWaypointDatabase {

	Map<UUID, IWaypoint> db;
	private static HashMapWaypointDatabase hashMapWaypointDatabase;

	private HashMapWaypointDatabase() {
		db = new HashMap<UUID, IWaypoint>();
	}

	public static HashMapWaypointDatabase getInstance() {
		if (hashMapWaypointDatabase == null)
			hashMapWaypointDatabase = new HashMapWaypointDatabase();
		return hashMapWaypointDatabase;
	}

	@Override
	public UUID create() {
		IWaypoint waypoint = new Waypoint(Maneuver.NONE, ForeSail.NONE,
				MainSail.NONE);
		UUID id = UUID.fromString(waypoint.getId());
		db.put(id, waypoint);
		return id;
	}

	@Override
	public boolean save(IWaypoint waypoint) {
		return db.put(UUID.fromString(waypoint.getId()), waypoint) == null;
	}

	@Override
	public void delete(UUID id) {
		db.remove(id);
	}

	@Override
	public IWaypoint get(UUID id) {
		return db.get(id);
	}

	@Override
	public List<IWaypoint> loadAll() {
		Collection<IWaypoint> collection = db.values();
		List<IWaypoint> values = new ArrayList<IWaypoint>(collection);
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
	public List<IWaypoint> loadAllByTripId(UUID tripId) {
		// TODO Auto-generated method stub
		return null;
	}
}
