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

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.database.IAccountDatabase;
import de.htwg.seapal.database.impl.views.account.ByEmailView;
import de.htwg.seapal.model.IAccount;
import de.htwg.seapal.model.ModelDocument;
import de.htwg.seapal.model.impl.Account;
import roboguice.inject.ContextSingleton;

/**
 * Created by jakub on 2/15/14.
 */
@ContextSingleton
public class TouchDBAccountDatabase extends CouchDbRepositorySupport<Account> implements IAccountDatabase {


    public static final String dDocName = "Account";
    private static final String TAG = "Account-TouchDB";
    private static Database database;
    private final CouchDbConnector couchDbConnector;
    private final TouchDBHelper dbHelper;

    @Inject
    public TouchDBAccountDatabase(@Named("accountCouchDbConnector") TouchDBHelper helper, Context ctx) {
        super(Account.class, helper.getCouchDbConnector());
        initStandardDesignDocument();
        dbHelper = helper;
        couchDbConnector = dbHelper.getCouchDbConnector();
        database = dbHelper.getTDDatabase();


        View by_email = database.getView(String.format("%s/%s", dDocName, "by_email"));
        by_email.setMap(new ByEmailView(), "1");

        Log.i(TAG, "Views = " + dbHelper.getTDDatabase().getAllViews());
    }

    @Override
    public IAccount getAccount(String email) {
        List<? extends IAccount> accounts = queryViews("by_email", email);
        if (accounts.size() == 1) {
            return accounts.get(0);
        }
        return null;
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
    public boolean save(IAccount iAccount) {
        Account entity = (Account) iAccount;
        if (entity.isNew()) {
            entity.setRevision(null);
            add(entity);
            return true;
        } else {
            Log.i(TAG, "Updating entity with UUID: " + entity.getId());
            update(entity);
            return false;
        }
    }

    @Override
    public IAccount get(UUID uuid) {
        try {
            return get(uuid.toString());
        } catch (DocumentNotFoundException e) {
            return null;
        }
    }

    @Override
    public List<IAccount> loadAll() {
        List<IAccount> Accounts = new LinkedList<IAccount>(getAll());
        Log.i(TAG, "Loaded entities. Count: " + Accounts.size());
        return Accounts;
    }

    @Override
    public void delete(UUID uuid) {
        Log.i(TAG, "Removing entity with UUID: " + uuid.toString());
        remove((Account) get(uuid));

    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public void create(ModelDocument modelDocument) {

    }

    @Override
    public List<? extends IAccount> queryViews(String viewName, String key) {
        ViewResult vr = db.queryView(createQuery(viewName).key(key).queryParam("debug", "true"));
        List<Account> accounts = dbHelper.mapViewResultTo(vr, Account.class);
        return accounts;
    }

    @Override
    public void update(ModelDocument modelDocument) {

    }
}
