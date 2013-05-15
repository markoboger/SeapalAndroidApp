package de.htwg.seapal.database;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.model.IWaypoint;

/**
 * The waypoint database interface.
 */
public interface IWaypointDatabase extends IDatabase<IWaypoint> {
	List<IWaypoint> loadAllByTripId(UUID tripId);
}
