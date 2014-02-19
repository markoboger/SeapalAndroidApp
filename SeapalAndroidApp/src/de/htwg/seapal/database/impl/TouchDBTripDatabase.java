package de.htwg.seapal.database.impl;

import android.content.Context;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.View;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.ViewResult;
import org.ektorp.support.CouchDbRepositorySupport;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.database.ITripDatabase;
import de.htwg.seapal.database.impl.views.BoatView;
import de.htwg.seapal.database.impl.views.OwnView;
import de.htwg.seapal.database.impl.views.SingleDocumentView;
import de.htwg.seapal.model.ITrip;
import de.htwg.seapal.model.ModelDocument;
import de.htwg.seapal.model.impl.Trip;
import roboguice.inject.ContextSingleton;

@ContextSingleton
public class TouchDBTripDatabase extends CouchDbRepositorySupport<Trip> implements ITripDatabase {

    private static final String TAG = "Trip-TouchDB";


    private final CouchDbConnector connector;
    private final TouchDBHelper dbHelper;
    private final Database database;

    @Inject
    public TouchDBTripDatabase(@Named("tripCouchDbConnector") TouchDBHelper helper, Context ctx) {
        super(Trip.class, helper.getCouchDbConnector());
        dbHelper = helper;
        connector = dbHelper.getCouchDbConnector();

        database = dbHelper.getTDDatabase();


        View singleDoc = database.getView(String.format("%s/%s", "Trip", "singleDocument"));
        singleDoc.setMap(new SingleDocumentView(), "1");

        View ownDoc = database.getView(String.format("%s/%s", "Trip", "own"));
        ownDoc.setMap(new OwnView(), "1");


        View boatDoc = database.getView(String.format("%s/%s", "Trip", "boat"));
        boatDoc.setMap(new BoatView(), "1");

        try {
            singleDoc.updateIndex();
            ownDoc.updateIndex();
            boatDoc.updateIndex();
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }

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
    public boolean save(ITrip data) {
        Trip entity = (Trip) data;

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
    public ITrip get(UUID id) {
        try {
            return get(id.toString());
        } catch (DocumentNotFoundException e) {
            return null;
        }
    }

    @Override
    public List<ITrip> loadAll() {
        List<ITrip> trips = new LinkedList<ITrip>(getAll());
        return trips;
    }

    @Override
    public void delete(UUID id) {
        remove((Trip) get(id));
    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public List<? extends ITrip> queryViews(final String viewName, final String key) {
        ViewResult vr = db.queryView(createQuery(viewName).key(key));
        List<Trip> trips = dbHelper.mapViewResultTo(vr, Trip.class);
        return trips;
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