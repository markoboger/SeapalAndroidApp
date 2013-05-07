package de.htwg.seapal.database.impl;

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
	public UUID newWaypoint() {
		IWaypoint waypoint = new Waypoint(Maneuver.NONE, ForeSail.NONE, MainSail.NONE);
		UUID id = UUID.fromString(waypoint.getId());
		db.put(id, waypoint);
		return id;
	}

	@Override
	public void saveWaypoint(IWaypoint waypoint) {
		db.put(UUID.fromString(waypoint.getId()), waypoint);
	}

	@Override
	public void deleteWaypoint(UUID id) {
		db.remove(id);
	}

	@Override
	public IWaypoint getWaypoint(UUID id) {
		return db.get(id);
	}

	@Override
	public List<IWaypoint> getWaypoints() {
		Collection<IWaypoint> collection = db.values();
		List<IWaypoint> values = new ArrayList<IWaypoint>(collection);
		return values;
	}

	@Override
	public boolean closeDB() {
		return true;
	}
}
