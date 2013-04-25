package de.htwg.seapal.database;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.model.IBoat;


public interface IBoatDatabase {

	UUID newBoat();

	void saveBoat(IBoat boat);

	void deleteBoat(UUID id);

	IBoat getBoat(UUID id);

	List<IBoat> getBoats();

	boolean closeDB();
}