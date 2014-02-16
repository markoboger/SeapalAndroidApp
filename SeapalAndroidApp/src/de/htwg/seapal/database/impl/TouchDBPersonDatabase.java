package de.htwg.seapal.database.impl;

import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.support.CouchDbRepositorySupport;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.database.IPersonDatabase;
import de.htwg.seapal.model.IPerson;
import de.htwg.seapal.model.ModelDocument;
import de.htwg.seapal.model.impl.Person;
import roboguice.inject.ContextSingleton;

@ContextSingleton
public class TouchDBPersonDatabase extends CouchDbRepositorySupport<Person> implements IPersonDatabase {

    private static final String TAG = "person-TouchDB";

    private CouchDbConnector connector;


    @Inject
    public TouchDBPersonDatabase(@Named("personCouchDbConnector") TouchDBHelper helper, Context ctx) {
        super(Person.class, helper.getCouchDbConnector());
        super.initStandardDesignDocument();
        helper.pullFromDatabase();
        connector = helper.getCouchDbConnector();
    }

    @Override
    public boolean open() {
        return true;
    }

    @Override
    public UUID create() {
        return null;
    }

    @Override
    public boolean save(IPerson data) {
        Person entity = (Person) data;

        if (entity.isNew()) {
            // ensure that the id is generated and revision is null for saving a new entity
            entity.setId(UUID.randomUUID().toString());
            entity.setRevision(null);
            add(entity);
            return true;
        }

        update(entity);
        return false;
    }

    @Override
    public IPerson get(UUID id) {
        try {
            return get(id.toString());
        } catch (DocumentNotFoundException e) {
            return null;
        }
    }

    @Override
    public List<IPerson> loadAll() {
        List<IPerson> persons = new LinkedList<IPerson>(getAll());
        return persons;
    }

    @Override
    public void delete(UUID id) {
        remove((Person) get(id));
    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public List<? extends IPerson> queryViews(final String viewName, final String key) {
        try {
            return super.queryView(viewName, key);
        } catch (DocumentNotFoundException e) {
            return new ArrayList<Person>();
        }
    }

    @Override
    public void create(ModelDocument doc) {
        connector.create(doc);
    }

    @Override
    public void update(ModelDocument document) {
        connector.update(document);
    }
}
