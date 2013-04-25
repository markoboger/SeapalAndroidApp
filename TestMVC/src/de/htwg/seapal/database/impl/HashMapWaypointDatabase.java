package de.htwg.seapal.database.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.htwg.seapal.database.IWaypointDatabase;
import de.htwg.seapal.model.IWaypoint;
import de.htwg.seapal.model.impl.Waypoint;


public class HashMapWaypointDatabase implements IWaypointDatabase {

	Map<UUID, IWaypoint> db = new HashMap<UUID, IWaypoint>();

	@Override
	public UUID newWaypoint() {
		IWaypoint waypoint = new Waypoint();
		UUID id = waypoint.getId();
		db.put(id, waypoint);
		return id;
	}

	@Override
	public void saveWaypoint(IWaypoint waypoint) {
		db.put(waypoint.getId(), waypoint);
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
