package de.htwg.seapal.database.impl;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.couchbase.lite.Database;
import com.couchbase.lite.View;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.ektorp.CouchDbConnector;
import org.ektorp.ViewResult;
import org.ektorp.support.CouchDbRepositorySupport;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import de.htwg.seapal.database.IAccountDatabase;
import de.htwg.seapal.database.TouchDBHelper;
import de.htwg.seapal.database.impl.views.AllView;
import de.htwg.seapal.database.impl.views.account.ByEmailView;
import de.htwg.seapal.database.impl.views.account.FriendsView;
import de.htwg.seapal.database.impl.views.account.GoogleIDView;
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


        View friends = database.getView(String.format("%s/%s", dDocName, "friends"));
        friends.setMap(new FriendsView(), "1");


        View googlID = database.getView(String.format("%s/%s", dDocName, "googleID"));
        googlID.setMap(new GoogleIDView(), "1");

        View all = database.getView(String.format("%s/%s", dDocName, "all"));
        all.setMap(new AllView(), "1");

        dbHelper.pullFromDatabase();
        dbHelper.pushToDatabase();


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
    public IAccount get(final UUID uuid) {
        AsyncTask<UUID,Void, IAccount> asyncTask = new AsyncTask<UUID, Void, IAccount>() {
            @Override
            protected IAccount doInBackground(UUID... params) {
                UUID accountUuid = params[0];
                return TouchDBAccountDatabase.super.get(accountUuid.toString());
            }
        };
        try {
            return asyncTask.execute(uuid).get(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        } catch (TimeoutException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<IAccount> loadAll() {
        AsyncTask<Void,Void, List<Account>> asyncTask = new AsyncTask<Void, Void, List<Account>>() {
            @Override
            protected List<Account> doInBackground(Void... params) {
                return TouchDBAccountDatabase.super.getAll();
            }
        };
        try {
             return new LinkedList<IAccount>(asyncTask.execute().get(1, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Collections.emptyList();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return Collections.emptyList();
        } catch (TimeoutException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void delete(final UUID uuid) {
        Log.i(TAG, "Removing entity with UUID: " + uuid.toString());
        AsyncTask<Void,Void, IAccount> getAccount = new AsyncTask<Void, Void, IAccount>() {
            @Override
            protected IAccount doInBackground(Void... params) {
                return TouchDBAccountDatabase.super.get(uuid.toString());
            }
        };
        try {
            final Account account = (Account) getAccount.execute().get(1, TimeUnit.SECONDS);

            AsyncTask<Void,Void, Void> removeAccount = new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... params) {
                    TouchDBAccountDatabase.super.remove(account);
                    return null;
                }
            };
            removeAccount.execute();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean close() {
        return true;
    }

    @Override
    public void create(ModelDocument modelDocument) {

    }

    @Override
    public List<? extends IAccount> queryViews(final String viewName, final String key) {
        AsyncTask<Void,Void, ViewResult> getAccount = new AsyncTask<Void, Void, ViewResult>() {
            @Override
            protected ViewResult doInBackground(Void... params) {
                ViewResult vr = db.queryView(createQuery(viewName).key(key));
                return vr;
            }
        };
        ViewResult vr = null;
        try {
            vr = getAccount.execute().get(1, TimeUnit.SECONDS);
            List<Account> accounts = dbHelper.mapViewResultTo(vr, Account.class);
            return accounts;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Collections.emptyList();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return Collections.emptyList();
        } catch (TimeoutException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void update(ModelDocument modelDocument) {

    }


}
