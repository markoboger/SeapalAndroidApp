package de.htwg.seapal.database.impl;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.Database;
import com.couchbase.lite.View;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.ektorp.AttachmentInputStream;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewResult;
import org.ektorp.support.CouchDbRepositorySupport;
import org.ektorp.support.DesignDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.database.IMarkDatabase;
import de.htwg.seapal.database.impl.views.AllView;
import de.htwg.seapal.database.impl.views.OwnView;
import de.htwg.seapal.database.impl.views.SingleDocumentView;
import de.htwg.seapal.model.IMark;
import de.htwg.seapal.model.ModelDocument;
import de.htwg.seapal.model.impl.Mark;
import roboguice.inject.ContextSingleton;

@ContextSingleton
public class TouchDBMarkDatabase extends CouchDbRepositorySupport<Mark> implements IMarkDatabase {

    private static final String TAG = "Mark-TouchDB";

    private static TouchDBMarkDatabase touchDBMarkDatabase;
    private final CouchDbConnector connector;
    private final TouchDBHelper dbHelper;
    private final Database database;

    @Inject
    public TouchDBMarkDatabase(@Named("markCouchDbConnector") TouchDBHelper helper, Context ctx) {
        super(Mark.class, helper.getCouchDbConnector());
        initStandardDesignDocument();
        dbHelper = helper;
        connector = dbHelper.getCouchDbConnector();
        database = dbHelper.getTDDatabase();
        DesignDocument d = super.getDesignDocumentFactory().generateFrom(this);
        Log.i(TAG,"Views = " + d.getViews());

        View singleDoc = database.getView(String.format("%s/%s", "Mark", "singleDocument"));
        singleDoc.setMap(new SingleDocumentView(), "1");

        View ownDoc = database.getView(String.format("%s/%s", "Mark", "own"));
        ownDoc.setMap(new OwnView(), "1");

        View all = database.getView(String.format("%s/%s", "Mark", "all"));
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
    public boolean save(IMark data) {
        Mark entity = (Mark) data;

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
    public IMark get(UUID id) {
        List<? extends IMark> marks = queryViews("all", id.toString());
        if (marks.size() == 1) {
            return marks.get(0);

        }
        return null;
    }

    @Override
    public List<IMark> loadAll() {
        List<IMark> marks = new LinkedList<IMark>(getAll());
        return marks;
    }

    @Override
    public void delete(UUID id) {
        remove((Mark) get(id));
    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public List<? extends IMark> queryViews(final String viewName, final String key) {
        ViewResult vr = db.queryView(createQuery(viewName).key(key));
        List<Mark> marks = dbHelper.mapViewResultTo(vr, Mark.class);
        return marks;
    }

    @Override
    public void create(ModelDocument doc) {
        connector.create(doc);
    }

    @Override
    public void update(ModelDocument document) {
        connector.update(document);
    }

    @Override
    public boolean addPhoto(IMark mark, String contentType, File file) throws FileNotFoundException {
        AttachmentInputStream a = new AttachmentInputStream("photo", new FileInputStream(file), contentType);
        db.createAttachment(mark.getUUID().toString(), mark.getRevision(), a);
        return true;
    }

    @Override
    public InputStream getPhoto(UUID uuid) {
        return db.getAttachment(uuid.toString(), "photo");


    }

}
