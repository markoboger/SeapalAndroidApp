package de.htwg.seapal.database.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.htwg.seapal.database.IPersonDatabase;
import de.htwg.seapal.model.IPerson;
import de.htwg.seapal.model.impl.Person;

public class HashMapPersonDatabase implements IPersonDatabase {

	private Map<UUID, IPerson> db;
	private static HashMapPersonDatabase hashMapPersonDatabase;

	private HashMapPersonDatabase() {
		db = new HashMap<UUID, IPerson>();
	}

	public static HashMapPersonDatabase getInstance() {
		if (hashMapPersonDatabase == null)
			hashMapPersonDatabase = new HashMapPersonDatabase();
		return hashMapPersonDatabase;
	}

	@Override
	public UUID newPerson() {
		IPerson person = new Person();
		UUID id = person.getUUId();
		db.put(id, person);
		return id;
	}

	@Override
	public void savePerson(IPerson person) {
		db.put(person.getUUId(), person);
	}

	@Override
	public void deletePerson(UUID id) {
		db.remove(id);
	}

	@Override
	public IPerson getPerson(UUID id) {
		return db.get(id);
	}

	@Override
	public List<IPerson> getPersons() {
		Collection<IPerson> collection = db.values();
		List<IPerson> values = new ArrayList<IPerson>(collection);
		return values;
	}

	@Override
	public boolean closeDB() {
		return true;
	}

}
