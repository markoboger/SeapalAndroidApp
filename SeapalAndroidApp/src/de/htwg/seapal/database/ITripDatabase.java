package de.htwg.seapal.database;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.model.ITrip;

public interface ITripDatabase extends IDatabase<ITrip> {
	
	List<UUID> loadAllById(UUID boatId);
}