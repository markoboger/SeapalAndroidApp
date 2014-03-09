package de.htwg.seapal.database.impl.hashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.htwg.seapal.database.IWaypointDatabase;
import de.htwg.seapal.model.IWaypoint;
import de.htwg.seapal.model.ModelDocument;
import de.htwg.seapal.model.impl.Waypoint;

public class HashMapWaypointDatabase implements IWaypointDatabase {

	private final Map<UUID, IWaypoint> db;
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
		IWaypoint waypoint = new Waypoint();
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
		return new ArrayList<IWaypoint>(collection);
	}

	@Override
	public boolean close() {
		return true;
	}

    @Override
    public void create(ModelDocument modelDocument) {

    }

    @Override
    public List<? extends IWaypoint> queryViews(String s, String s2) {
        return null;
    }

    @Override
    public void update(ModelDocument modelDocument) {

    }

    @Override
	public boolean open() {
		// TODO Auto-generated method stub
		return false;
	}

	public List<IWaypoint> findByTrip(UUID tripId) {
		// TODO Auto-generated method stub
		return null;
	}
}
