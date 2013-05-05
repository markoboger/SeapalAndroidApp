package de.htwg.seapal.database.impl;

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
	public UUID newRoute() {
		IRoute route = new Route();
		UUID id = route.getId();
		db.put(id, route);
		return id;
	}

	@Override
	public void saveRoute(IRoute route) {
		db.put(route.getId(), route);
	}

	@Override
	public void deleteRoute(UUID id) {
		db.remove(id);
	}

	@Override
	public IRoute getRoute(UUID id) {
		return db.get(id);
	}

	@Override
	public List<IRoute> getRoutes() {
		Collection<IRoute> collection = db.values();
		List<IRoute> values = new ArrayList<IRoute>(collection);
		return values;
	}

	@Override
	public boolean closeDB() {
		return true;
	}

}
