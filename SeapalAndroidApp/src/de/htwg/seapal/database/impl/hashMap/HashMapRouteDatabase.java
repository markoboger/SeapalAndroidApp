package de.htwg.seapal.database.impl.hashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.htwg.seapal.database.IRouteDatabase;
import de.htwg.seapal.model.IRoute;
import de.htwg.seapal.model.impl.Route;

public class HashMapRouteDatabase implements IRouteDatabase {

	private Map<UUID, IRoute> db;
	private static HashMapRouteDatabase hashMapRouteDatabase;

	private HashMapRouteDatabase() {
		db = new HashMap<UUID, IRoute>();
	}

	public static HashMapRouteDatabase getInstance() {
		if (hashMapRouteDatabase == null)
			hashMapRouteDatabase = new HashMapRouteDatabase();
		return hashMapRouteDatabase;
	}

	@Override
	public UUID create() {
		IRoute route = new Route();
		UUID id = route.getUUID();
		db.put(id, route);
		return id;
	}

	@Override
	public boolean save(IRoute route) {
		return db.put(route.getUUID(), route) == null;
	}

	@Override
	public void delete(UUID id) {
		db.remove(id);
	}

	@Override
	public IRoute get(UUID id) {
		return db.get(id);
	}

	@Override
	public List<IRoute> loadAll() {
		Collection<IRoute> collection = db.values();
		List<IRoute> values = new ArrayList<IRoute>(collection);
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

}
