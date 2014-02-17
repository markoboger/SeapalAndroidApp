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

import de.htwg.seapal.database.IRouteDatabase;
import de.htwg.seapal.model.IRoute;
import de.htwg.seapal.model.ModelDocument;
import de.htwg.seapal.model.impl.Route;
import roboguice.inject.ContextSingleton;

@Views({
        @View(name = "singleDocument", map = "views/singleDocument.js"),
        @View(name = "own", map = "views/own.js")
})
@ContextSingleton
public class TouchDBRouteDatabase extends CouchDbRepositorySupport<Route> implements IRouteDatabase {

    private static final String TAG = "Route-TouchDB";

    private static TouchDBRouteDatabase touchDBRouteDatabase;
    private final CouchDbConnector connector;
    private final TouchDBHelper dbHelper;

    @Inject
    public TouchDBRouteDatabase(@Named("routeCouchDbConnector") TouchDBHelper helper, Context ctx) {
        super(Route.class, helper.getCouchDbConnector());
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
