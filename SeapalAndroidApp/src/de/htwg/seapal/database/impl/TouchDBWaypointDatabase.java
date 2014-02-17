package de.htwg.seapal.database.impl;

import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.View;
import org.ektorp.support.Views;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.database.IWaypointDatabase;
import de.htwg.seapal.model.IWaypoint;
import de.htwg.seapal.model.ModelDocument;
import de.htwg.seapal.model.impl.Waypoint;
import roboguice.inject.ContextSingleton;


@Views({
        @View(name = "singleDocument", map = "views/singleDocument.js"),
        @View(name = "own", map = "views/own.js"),
        @View(name = "boat", map = "views/waypoint/boat.js"),
        @View(name = "trip", map = "views/waypoint/trip.js")
})
@ContextSingleton
public class TouchDBWaypointDatabase extends CouchDbRepositorySupport<Waypoint> implements IWaypointDatabase {

    private static final String TAG = "Waypoint-TouchDB";
    private static final String DDOCNAME = "Waypoint";
    private static final String VIEWNAME = "by_trip";
    private static final String DATABASE_NAME = "seapal_waypoint_db";

    private static TouchDBWaypointDatabase touchDBWaypointDatabase;
    private final CouchDbConnector connector;
    private final TouchDBHelper dbHelper;

    @Inject
    public TouchDBWaypointDatabase(@Named("waypointCouchDbConnector") TouchDBHelper helper, Context ctx) {
        super(Waypoint.class, helper.getCouchDbConnector());
        super.initStandardDesignDocument();
        dbHelper = helper;
        connector = dbHelper.getCouchDbConnector();
        Log.i(TAG, "Doc Ids " + super.getDesignDocumentFactory().generateFrom(this).getViews());


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
        try {
            return super.queryView(viewName, key);
        } catch (DocumentNotFoundException e) {
            return new ArrayList<Waypoint>();
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
