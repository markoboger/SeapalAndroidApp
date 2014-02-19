package de.htwg.seapal.database.impl;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.Database;
import com.couchbase.lite.View;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewResult;
import org.ektorp.support.CouchDbRepositorySupport;

import java.io.IOException;
import java.util.ArrayList;
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


    private static final String TAG = "Account-TouchDB";

    private static Database database;
    private final CouchDbConnector couchDbConnector;
    private final TouchDBHelper dbHelper;
    public static final String dDocName = "Account";
    public static final String dDocId = "_design/" + dDocName;

    @Inject
    public TouchDBAccountDatabase(@Named("accountCouchDbConnector") TouchDBHelper helper, Context ctx) {
        super(Account.class, helper.getCouchDbConnector());
        initStandardDesignDocument();
        dbHelper = helper;
        couchDbConnector = dbHelper.getCouchDbConnector();
        database = dbHelper.getTDDatabase();


        View by_email = database.getView(String.format("%s/%s", dDocName,"by_email"));
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
        return false;
    }

    @Override
    public UUID create() {
        return null;
    }

    @Override
    public boolean save(IAccount iAccount) {
        return false;
    }

    @Override
    public IAccount get(UUID uuid) {
        return null;
    }

    @Override
    public List<IAccount> loadAll() {
        return null;
    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public boolean close() {
        return false;
    }

    @Override
    public void create(ModelDocument modelDocument) {

    }

    @Override
    public List<? extends IAccount> queryViews(String viewName, String key) {
        ViewResult vr = db.queryView(createQuery(viewName).key(key));
        List<Account> accounts = new ArrayList<Account>();
        if (vr.getTotalRows() > 0) {
            for (ViewResult.Row row: vr.getRows()){
                ObjectMapper mapper = dbHelper.getObjectMapper();
                ObjectReader reader = mapper.reader(Account.class);
                JsonNode valueAsNode =  row.getValueAsNode();
                try {
                    accounts.add((Account) reader.readValue(valueAsNode));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return accounts;
    }

    @Override
    public void update(ModelDocument modelDocument) {

    }
}
