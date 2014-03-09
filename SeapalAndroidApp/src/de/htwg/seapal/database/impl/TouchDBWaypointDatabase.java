package de.htwg.seapal.database.impl;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.Database;
import com.couchbase.lite.View;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.ViewResult;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.DesignDocument;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.database.IWaypointDatabase;
import de.htwg.seapal.database.TouchDBHelper;
import de.htwg.seapal.database.impl.views.AllView;
import de.htwg.seapal.database.impl.views.BoatView;
import de.htwg.seapal.database.impl.views.OwnView;
import de.htwg.seapal.database.impl.views.SingleDocumentView;
import de.htwg.seapal.database.impl.views.TripView;
import de.htwg.seapal.events.session.LogOutEvent;
import de.htwg.seapal.events.session.LoginEvent;
import de.htwg.seapal.model.IWaypoint;
import de.htwg.seapal.model.ModelDocument;
import de.htwg.seapal.model.impl.Waypoint;
import roboguice.event.Observes;
import roboguice.inject.ContextSingleton;


@ContextSingleton
public class TouchDBWaypointDatabase extends CouchDbRepositorySupport<Waypoint> implements IWaypointDatabase {

    private static final String TAG = "Waypoint-TouchDB";
    private final CouchDbConnector connector;
    private final TouchDBHelper dbHelper;
    private final Database database;

    @Inject
    public TouchDBWaypointDatabase(@Named("waypointCouchDbConnector") TouchDBHelper helper, Context ctx) {
        super(Waypoint.class, helper.getCouchDbConnector());
        initStandardDesignDocument();
        dbHelper = helper;
        connector = dbHelper.getCouchDbConnector();

        database = dbHelper.getTDDatabase();

        Log.i(TAG, "Doc Ids " + super.getDesignDocumentFactory().generateFrom(this).getViews());
        DesignDocument d = super.getDesignDocumentFactory().generateFrom(this);
        Log.i(TAG, "Views = " + d.getViews());


        View singleDoc = database.getView(String.format("%s/%s", "Waypoint", "singleDocument"));
        singleDoc.setMap(new SingleDocumentView(), "1");

        View ownDoc = database.getView(String.format("%s/%s", "Waypoint", "own"));
        ownDoc.setMap(new OwnView(), "1");


        View boatDoc = database.getView(String.format("%s/%s", "Waypoint", "boat"));
        boatDoc.setMap(new BoatView(), "1");

        View tripDoc = database.getView(String.format("%s/%s", "Waypoint", "trip"));
        tripDoc.setMap(new TripView(), "1");

        View all = database.getView(String.format("%s/%s", "Waypoint", "all"));
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
    public boolean save(IWaypoint data) {
        Waypoint entity = (Waypoint) data;

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
    public Waypoint get(UUID id) {
        try {
            return get(id.toString());
        } catch (DocumentNotFoundException e) {
            return null;
        }
    }

    @Override
    public List<IWaypoint> loadAll() {
        List<IWaypoint> waypoints = new LinkedList<IWaypoint>(getAll());
        return waypoints;
    }

    @Override
    public void delete(UUID id) {
        remove(get(id));
    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public List<? extends IWaypoint> queryViews(final String viewName, final String key) {
        ViewResult vr = db.queryView(createQuery(viewName).key(key));
        List<Waypoint> waypoints = dbHelper.mapViewResultTo(vr, Waypoint.class);
        return waypoints;
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
