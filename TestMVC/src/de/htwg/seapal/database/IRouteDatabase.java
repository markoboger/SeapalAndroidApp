package de.htwg.seapal.database;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.model.IRoute;

public interface IRouteDatabase {

	UUID newRoute();

	void saveRoute(IRoute route);

	void deleteRoute(UUID id);

	IRoute getRoute(UUID id);

	List<IRoute> getRoutes();

	boolean closeDB();
}
