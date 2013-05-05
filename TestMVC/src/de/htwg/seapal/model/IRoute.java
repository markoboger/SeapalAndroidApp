package de.htwg.seapal.model;

import java.util.List;
import java.util.UUID;

public interface IRoute {

	UUID getId();

	String getName();

	void setName(String name);

	long getDate();

	void setDate(long date);

	List<UUID> getMarks();

	void setMark(UUID mark);

	UUID getRouteEntryPoint();

	void setRouteEntryPoint(UUID mark);

	double getDistance();

	void setDistance(double distance);

}
