package de.htwg.seapal.database.impl.hashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.htwg.seapal.database.IPersonDatabase;
import de.htwg.seapal.model.IPerson;
import de.htwg.seapal.model.ModelDocument;
import de.htwg.seapal.model.impl.Person;

public class HashMapPersonDatabase implements IPersonDatabase {

	private final Map<UUID, IPerson> db;
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
	public UUID create() {
		IPerson person = new Person();
		UUID id = person.getUUID();
		db.put(id, person);
		return id;
	}

	@Override
	public boolean save(IPerson person) {
		return db.put(person.getUUID(), person) == null;
	}

	@Override
	public void delete(UUID id) {
		db.remove(id);
	}

	@Override
	public IPerson get(UUID id) {
		return db.get(id);
	}

	@Override
	public List<IPerson> loadAll() {
		Collection<IPerson> collection = db.values();
		return new ArrayList<IPerson>(collection);
	}

	@Override
	public boolean close() {
		return true;
	}

    @Override
    public void create(ModelDocument modelDocument) {

    }

    @Override
    public List<? extends IPerson> queryViews(String s, String s2) {
        return null;
    }

    @Override
    public void update(ModelDocument modelDocument) {

    }

    @Override
	public boolean open() {
		// TODO Auto-generated method stub
		return false;
	}

}
