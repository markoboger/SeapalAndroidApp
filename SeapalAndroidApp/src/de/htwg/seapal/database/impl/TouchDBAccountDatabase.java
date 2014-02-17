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

import de.htwg.seapal.database.IAccountDatabase;
import de.htwg.seapal.model.IAccount;
import de.htwg.seapal.model.ModelDocument;
import de.htwg.seapal.model.impl.Account;
import roboguice.inject.ContextSingleton;

/**
 * Created by jakub on 2/15/14.
 */
@Views({
        @View(name = "by_email", map = "views/account/by_email.js"),
        @View(name = "friends", map = "views/account/friends.js"),
        @View(name = "this", map = "function(doc) { return emit(doc._id, doc); }"),
        @View(name = "googleID", map = "views/account/googleID.js"),
        @View(name = "resetToken", map = "views/account/resetToken.js")
})
@ContextSingleton
public class TouchDBAccountDatabase extends CouchDbRepositorySupport<Account> implements IAccountDatabase {


    private static final String TAG = "Account-TouchDB";

    private static TouchDBBoatDatabase touchDBBoatDatabase;
    private final CouchDbConnector couchDbConnector;
    private final TouchDBHelper dbHelper;


    @Inject
    public TouchDBAccountDatabase(@Named("accountCouchDbConnector") TouchDBHelper helper, Context ctx) {
        super(Account.class, helper.getCouchDbConnector());
        initStandardDesignDocument();
        dbHelper = helper;
        couchDbConnector = dbHelper.getCouchDbConnector();
        Log.i(TAG, "Doc Ids " + super.getDesignDocumentFactory().generateFrom(this).getViews());
    }

    @Override
    public IAccount getAccount(String email) {
        open();
        List<Account> accounts = queryView("by_email", email);
        close();
        if (accounts.size() > 1 || accounts.size() < 1) {
            return null;
        } else {
            return accounts.get(0);
        }


    }

    @Override
    public boolean open() {
        Log.i(TAG, "Database connection opened");
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
            open();
            entity.setRevision(null);
            add(entity);
            close();
            return true;
        } else {
            open();
            Log.i(TAG, "Updating entity with UUID: " + entity.getUUID());
            update(entity);
            close();
            return false;
        }
    }

    @Override
    public IAccount get(UUID uuid) {
        try {
            open();
            IAccount a = get(uuid.toString());
            close();
            return a;
        } catch (DocumentNotFoundException e) {
            return null;
        }
    }

    @Override
    public List<IAccount> loadAll() {
        open();
        List<IAccount> Accounts = new LinkedList<IAccount>(getAll());
        Log.i(TAG, "Loaded entities. Count:  " + Accounts.size());
        close();
        return Accounts;

    }

    @Override
    public void delete(UUID uuid) {
        open();
        Log.i(TAG, "Removing entity with UUID: " + uuid);
        remove((Account) get(uuid));
        close();


    }

    @Override
    public boolean close() {
        Log.i(TAG, "Closing database");
        return true;
    }

    @Override
    public void create(ModelDocument modelDocument) {


    }

    @Override
    public List<? extends IAccount> queryViews(String viewName, String key) {
        try {
            open();
            initStandardDesignDocument();
            List<Account> a = queryView(viewName, key);
            close();
            return a;

        } catch (DocumentNotFoundException e) {
            return new ArrayList<Account>();
        }
    }

    @Override
    public void update(ModelDocument modelDocument) {

    }
}
