package de.htwg.seapal.database;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.model.IPerson;

public interface IPersonDatabase {

	UUID newPerson();

	void savePerson(IPerson person);

	void deletePerson(UUID id);

	IPerson getPerson(UUID id);

	List<IPerson> getPersons();

	boolean closeDB();
}
