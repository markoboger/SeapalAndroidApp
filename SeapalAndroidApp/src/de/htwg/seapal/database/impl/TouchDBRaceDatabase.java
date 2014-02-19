package de.htwg.seapal.database.impl;

import android.content.Context;
import android.util.Log;

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

import de.htwg.seapal.database.IRaceDatabase;
import de.htwg.seapal.model.ModelDocument;
import de.htwg.seapal.model._IRace;
import de.htwg.seapal.model.impl._Race;
import roboguice.inject.ContextSingleton;

/**
 * Created by jakub on 2/15/14.
 */
@ContextSingleton
public class TouchDBRaceDatabase extends CouchDbRepositorySupport<_Race> implements IRaceDatabase {


    private static final String TAG =  "Race-TouchDB";
    private final CouchDbConnector connector;

    @Inject
    protected TouchDBRaceDatabase(@Named("raceCouchDbConnector") TouchDBHelper helper, Context ctx) {
        super(_Race.class, helper.getCouchDbConnector());
        super.initStandardDesignDocument();
        connector = helper.getCouchDbConnector();

        DesignDocument d = super.getDesignDocumentFactory().generateFrom(this);
        Log.i(TAG, "Views = " + d.getViews());

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
    public boolean save(_IRace data) {
        _Race entity = (_Race) data;

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
    public _IRace get(UUID id) {
        try {
            return get(id.toString());
        } catch (DocumentNotFoundException e) {
            return null;
        }
    }

    @Override
    public List<_IRace> loadAll() {
        List<_IRace> races = new LinkedList<_IRace>(getAll());
        return races;
    }

    @Override
    public void delete(UUID id) {
        remove((_Race) get(id));
    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public List<? extends _IRace> queryViews(final String viewName, final String key) {
        try {
            return super.queryView(viewName, key);
        } catch (DocumentNotFoundException e) {
            return new ArrayList<_Race>();
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
