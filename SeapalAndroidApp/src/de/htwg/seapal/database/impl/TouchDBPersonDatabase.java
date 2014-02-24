package de.htwg.seapal.database.impl;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.Database;
import com.couchbase.lite.View;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.ektorp.CouchDbConnector;
import org.ektorp.ViewResult;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.DesignDocument;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.database.IPersonDatabase;
import de.htwg.seapal.database.impl.views.AllView;
import de.htwg.seapal.database.impl.views.OwnView;
import de.htwg.seapal.database.impl.views.SingleDocumentView;
import de.htwg.seapal.model.IPerson;
import de.htwg.seapal.model.ModelDocument;
import de.htwg.seapal.model.impl.Person;
import roboguice.inject.ContextSingleton;

@ContextSingleton
public class TouchDBPersonDatabase extends CouchDbRepositorySupport<Person> implements IPersonDatabase {

    private static final String TAG = "person-TouchDB";
    private final Database database;
    private final TouchDBHelper dbHelper;

    private CouchDbConnector connector;


    @Inject
    public TouchDBPersonDatabase(@Named("personCouchDbConnector") TouchDBHelper helper, Context ctx) {
        super(Person.class, helper.getCouchDbConnector(), "Person");
        initStandardDesignDocument();
        connector = helper.getCouchDbConnector();
        database = helper.getTDDatabase();
        dbHelper = helper;

        DesignDocument d = super.getDesignDocumentFactory().generateFrom(this);
        Log.i(TAG, "Views = " + d.getViews());

        View singleDoc = database.getView(String.format("%s/%s", "Person", "singleDocument"));
        singleDoc.setMap(new SingleDocumentView(), "1");

        View ownDoc = database.getView(String.format("%s/%s", "Person", "own"));
        ownDoc.setMap(new OwnView(), "1");


        View all = database.getView(String.format("%s/%s", "Person", "all"));
        all.setMap(new AllView(), "1");

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
        List<? extends IPerson> persons = queryViews("all", id.toString());
        if (persons.size() == 1) {
            return persons.get(0);

        }
        return null;
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
        ViewResult vr = db.queryView(createQuery(viewName).key(key));
        List<Person> persons = dbHelper.mapViewResultTo(vr, Person.class);
        return persons;
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
