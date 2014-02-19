package de.htwg.seapal.database.impl;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.View;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.DesignDocument;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.database.IRouteDatabase;
import de.htwg.seapal.database.impl.views.OwnView;
import de.htwg.seapal.database.impl.views.SingleDocumentView;
import de.htwg.seapal.model.IRoute;
import de.htwg.seapal.model.ModelDocument;
import de.htwg.seapal.model.impl.Route;
import roboguice.inject.ContextSingleton;

@ContextSingleton
public class TouchDBRouteDatabase extends CouchDbRepositorySupport<Route> implements IRouteDatabase {

    private static final String TAG = "Route-TouchDB";

    private static TouchDBRouteDatabase touchDBRouteDatabase;
    private final CouchDbConnector connector;
    private final TouchDBHelper dbHelper;
    private final Database database;

    @Inject
    public TouchDBRouteDatabase(@Named("routeCouchDbConnector") TouchDBHelper helper, Context ctx) {
        super(Route.class, helper.getCouchDbConnector());
        super.initStandardDesignDocument();
        dbHelper = helper;
        database = helper.getTDDatabase();
        connector = dbHelper.getCouchDbConnector();
        DesignDocument d = super.getDesignDocumentFactory().generateFrom(this);
        Log.i(TAG, "Views = " + d.getViews());


        View singleDoc = database.getView(String.format("%s/%s", "Route", "singleDocument"));
        singleDoc.setMap(new SingleDocumentView(), "1");

        View ownDoc = database.getView(String.format("%s/%s", "Route", "own"));
        ownDoc.setMap(new OwnView(), "1");
        try {
            singleDoc.updateIndex();
            ownDoc.updateIndex();
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
    public boolean save(IRoute data) {
        Route entity = (Route) data;

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
    public IRoute get(UUID id) {
        try {
            return get(id.toString());
        } catch (DocumentNotFoundException e) {
            return null;
        }
    }

    @Override
    public List<IRoute> loadAll() {
        List<IRoute> routes = new LinkedList<IRoute>(getAll());
        return routes;
    }

    @Override
    public void delete(UUID id) {
        remove((Route) get(id));
    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public List<? extends IRoute> queryViews(final String viewName, final String key) {
        return super.queryView(viewName, key);
    }

    @Override
    public List<Route> queryView(final String viewName, final String key) {
        try {
            return super.queryView(viewName, key);
        } catch (DocumentNotFoundException e) {
            return new ArrayList<Route>();
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
