package de.htwg.seapal.controller;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.observer.IObservable;

public interface IRouteController extends IObservable {
	
	UUID getId(UUID id);

	String getName(UUID id);

	void setName(UUID id, String name);

	String getDate(UUID id);

	void setDate(UUID id, long date);

	List<UUID> getMarks(UUID id);

	void setMark(UUID id, UUID mark);

	UUID getRouteEntryPoint(UUID id);

	void setRouteEntryPoint(UUID id, UUID mark);

	double getDistance(UUID id);

	void setDistance(UUID id, double distance);
	
	void deleteRoute(UUID id);

	void closeDB();

	List<UUID> getRoutes();
	
	UUID newRoute();

	String getString(UUID id);

}
