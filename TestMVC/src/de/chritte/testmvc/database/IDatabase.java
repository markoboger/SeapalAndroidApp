package de.chritte.testmvc.database;

import java.util.List;
import java.util.UUID;

import de.chritte.testmvc.model.IBoat;

public interface IDatabase {

	UUID newBoat();

	void saveBoat(IBoat boat);

	void deleteBoat(UUID id);

	IBoat getBoat(UUID id);

	List<IBoat> getBoats();

	boolean closeDB();
}