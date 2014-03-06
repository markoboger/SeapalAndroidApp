package de.htwg.seapal.database;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.ektorp.CBLiteHttpClient;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.ektorp.CouchDbConnector;
import org.ektorp.ReplicationCommand;
import org.ektorp.ViewResult;
import org.ektorp.android.http.AndroidHttpClient;
import org.ektorp.http.HttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakub on 2/24/14.
 */
public abstract class TouchDBHelper {



    protected final String hostDB = "http://seapal.iriscouch.com/";

    protected static final String TAG = "TouchDB";
    protected final String DATABASE_NAME;
    protected StdCouchDbInstance dbInstance;
    protected CouchDbConnector couchDbConnector;
    protected ObjectMapper objectMapper;
    protected Database tdDB;
    protected Context ctx;

    protected TouchDBHelper(String database_name, Context context) {
        DATABASE_NAME = database_name;
        ctx = context;
    }


    protected void transitionToReplicationHelper() {

        Manager server = null;
        File filesDir = ctx.getFilesDir();
        Log.d(TAG, ctx.getFilesDir().getAbsolutePath());
        try {
            server = new Manager(filesDir, Manager.DEFAULT_OPTIONS);

        } catch (IOException e) {
            Log.e(TAG, "Error starting Boat-TDServer", e);
        }

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        try {
            tdDB = server.getDatabase(DATABASE_NAME);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }



        HttpClient h = new CBLiteHttpClient(server);

        StdObjectMapperFactory s = new StdObjectMapperFactory();
        dbInstance = new StdCouchDbInstance(h, s);


        // create a local database
        couchDbConnector = dbInstance.createConnector(DATABASE_NAME, true);

        objectMapper = s.createObjectMapper(couchDbConnector);

        Log.i(TAG, "Doc-Ids" + couchDbConnector.getAllDocIds());

        pullFromDatabase();
        pushToDatabase();

    }

    // Cannot Use nonReplication for account + person db cuz there are stille some serious problems there.
    protected void transitionToNonReplicationHelper() {
        // TouchDB
        Log.d(TAG, "Starting " + DATABASE_NAME);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        HttpClient h = null;

        for (int i = 0; i < 10; i++ ) {
            try {
                h = new AndroidHttpClient.Builder().url(hostDB).port(5984).socketTimeout(1000000).build();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }



        StdObjectMapperFactory s = new StdObjectMapperFactory();

        Log.i(TAG,"Creating CouchDbInstance");

        dbInstance = new StdCouchDbInstance(h, s);

        Log.i(TAG,"Creating CouchDbConnector");
        // create a local database
        couchDbConnector = dbInstance.createConnector(DATABASE_NAME, false);

        objectMapper = s.createObjectMapper(couchDbConnector);

        Log.i(TAG, "Doc-Ids" + couchDbConnector.getAllDocIds());
    }


    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }


    public CouchDbConnector getCouchDbConnector() {
        return this.couchDbConnector;
    }

    public Database getTDDatabase() {
        return this.tdDB;
    }

    public <T> List<T> mapViewResultTo(ViewResult viewResult, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        if (viewResult.getTotalRows() > 0) {
            for (ViewResult.Row row: viewResult.getRows()){
                ObjectMapper mapper = objectMapper;
                ObjectReader reader = mapper.reader(clazz);
                JsonNode valueAsNode =  row.getValueAsNode();
                try {
                    list.add((T) reader.readValue(valueAsNode));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }


    public void pullFromDatabase() {
        ReplicationCommand pullReplicationCommand = new ReplicationCommand.Builder()
                .source(hostDB + DATABASE_NAME)
                .target(DATABASE_NAME)
                .continuous(true)
                .createTarget(true)
                .build();

        dbInstance.replicate(pullReplicationCommand);

    }

    public void pushToDatabase() {
        ReplicationCommand pushReplicationCommand = new ReplicationCommand.Builder()
                .source(DATABASE_NAME)
                .target(hostDB + DATABASE_NAME)
                .continuous(true)
                .build();

        dbInstance.replicate(pushReplicationCommand);
    }


    public void pullFromDatabaseWithFilter(final String filter) {
        ReplicationCommand pullReplicationCommand = new ReplicationCommand.Builder()
                .source(hostDB + DATABASE_NAME)
                .target(DATABASE_NAME)
                .filter(filter)
                .continuous(true)
                .createTarget(true)
                .build();

        dbInstance.replicate(pullReplicationCommand);

    }

    public void pushToDatabaseWithFilter(final String filter) {
        ReplicationCommand pushReplicationCommand = new ReplicationCommand.Builder()
                .source(DATABASE_NAME)
                .filter(filter)
                .target(hostDB + DATABASE_NAME)
                .continuous(true)
                .build();

        dbInstance.replicate(pushReplicationCommand);
    }


}
