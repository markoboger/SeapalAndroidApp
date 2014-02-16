package de.htwg.seapal.database.impl;

import android.content.Context;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.support.CouchDbRepositorySupport;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.database.IAccountDatabase;
import de.htwg.seapal.model.IAccount;
import de.htwg.seapal.model.ModelDocument;
import de.htwg.seapal.model.impl.Account;

/**
 * Created by jakub on 2/15/14.
 */
public class TouchDBAccountDatabase extends CouchDbRepositorySupport<Account> implements IAccountDatabase {


    private static final String TAG = "Account-TouchDB";

    private static TouchDBBoatDatabase touchDBBoatDatabase;
    private final CouchDbConnector couchDbConnector;
    private final TouchDBHelper dbHelper;


    @Inject
    public TouchDBAccountDatabase(@Named("accountCouchDbConnector") TouchDBHelper helper, Context ctx) {
        super(Account.class, helper.getCouchDbConnector());
        super.initStandardDesignDocument();
        dbHelper = helper;
        dbHelper.pullFromDatabase();
        couchDbConnector = dbHelper.getCouchDbConnector();

    }

    @Override
    public IAccount getAccount(String email) {
        List<Account> accounts = super.queryView("by_email", email);
        if (accounts.size() > 1 || accounts.size() < 1) {
            return null;
        } else {
            return accounts.get(0);
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
    public boolean save(IAccount iAccount) {
        Account entity = (Account) iAccount;
        if (entity.isNew()) {
            entity.setRevision(null);
            add(entity);
            return true;
        } else {
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
        return Accounts;

    }

    @Override
    public void delete(UUID uuid) {
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
        try {
            return super.queryView(viewName, key);
        } catch (DocumentNotFoundException e) {
            return new ArrayList<Account>();
        }
    }

    @Override
    public void update(ModelDocument modelDocument) {

    }
}
