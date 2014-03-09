package de.htwg.seapal.database.impl;

import android.content.Context;

import com.couchbase.lite.Database;
import com.couchbase.lite.View;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.ektorp.CouchDbConnector;
import org.ektorp.ViewResult;
import org.ektorp.support.CouchDbRepositorySupport;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.database.IBoatDatabase;
import de.htwg.seapal.database.TouchDBHelper;
import de.htwg.seapal.database.impl.views.AllView;
import de.htwg.seapal.database.impl.views.OwnView;
import de.htwg.seapal.database.impl.views.SingleDocumentView;
import de.htwg.seapal.events.session.LogOutEvent;
import de.htwg.seapal.events.session.LoginEvent;
import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.model.ModelDocument;
import de.htwg.seapal.model.impl.Boat;
import roboguice.event.Observes;
import roboguice.inject.ContextSingleton;

@ContextSingleton
public class TouchDBBoatDatabase extends CouchDbRepositorySupport<Boat> implements IBoatDatabase {

    private static final String TAG = "Boat-TouchDB";

    private final CouchDbConnector connector;
    private final TouchDBHelper dbHelper;
    private final Database database;

    @Inject
    public TouchDBBoatDatabase(@Named("boatCouchDbConnector") TouchDBHelper helper, Context ctx) {
        super(Boat.class, helper.getCouchDbConnector());
        super.initStandardDesignDocument();
        dbHelper = helper;
        connector = dbHelper.getCouchDbConnector();
        database = dbHelper.getTDDatabase();

        View singleDoc = database.getView(String.format("%s/%s", "Boat", "singleDocument"));
        singleDoc.setMap(new SingleDocumentView(), "1");

        View ownDoc = database.getView(String.format("%s/%s", "Boat", "own"));
        ownDoc.setMap(new OwnView(), "1");


        View all = database.getView(String.format("%s/%s", "Boat", "all"));
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
    public boolean save(IBoat data) {
        Boat entity = (Boat) data;

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
    public IBoat get(UUID id) {
        List<? extends IBoat> boats = queryViews("all", id.toString());
        if (boats.size() == 1) {
            return boats.get(0);

        }
        return null;
    }

    @Override
    public List<IBoat> loadAll() {
        List<IBoat> boats = new LinkedList<IBoat>(getAll());
        return boats;
    }

    @Override
    public void delete(UUID id) {
        remove((Boat) get(id));
    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public List<? extends IBoat> queryViews(final String viewName, final String key) {
        ViewResult vr = db.queryView(createQuery(viewName).key(key));
        List<Boat> boats = dbHelper.mapViewResultTo(vr, Boat.class);
        return boats;
    }

    @Override
    public void create(ModelDocument doc) {
        connector.create(doc);
    }

    @Override
    public void update(ModelDocument document) {
        connector.update(document);
    }


    public void onLogin(@Observes LoginEvent event) {

    }

    public void onLogout(@Observes LogOutEvent event) {

    }


}
