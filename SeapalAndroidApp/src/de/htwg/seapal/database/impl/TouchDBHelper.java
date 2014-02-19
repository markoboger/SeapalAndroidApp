package de.htwg.seapal.database.impl;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.ektorp.CBLiteHttpClient;
import com.google.inject.Inject;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.ektorp.CouchDbConnector;
import org.ektorp.ReplicationCommand;
import org.ektorp.ViewResult;
import org.ektorp.http.HttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TouchDBHelper {

    private static final String TAG = "TouchDB";
    private final String DATABASE_NAME;
    private final String hostDB = "http://192.168.0.107:5984/";
    private StdCouchDbInstance dbInstance;
    private CouchDbConnector couchDbConnector;
    private ObjectMapper  objectMapper;
    private Database tdDB;


    @Inject
    public TouchDBHelper(String dbName, Context ctx) {
        DATABASE_NAME = dbName;
        createDatabase(ctx);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void createDatabase(Context ctx) {

        if (couchDbConnector != null) {
            return;
        }
        // TouchDB
        Log.d(TAG, "Starting " + DATABASE_NAME);

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


        pullFromDatabase();
        pushToDatabase();
        Log.i(TAG, "Doc-Ids" + couchDbConnector.getAllDocIds());


    }

    public CouchDbConnector getCouchDbConnector() {
        return this.couchDbConnector;
    }

    public Database getTDDatabase() {
        return this.tdDB;
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

    public void pushToDatabase() {
        ReplicationCommand pushReplicationCommand = new ReplicationCommand.Builder()
                .source(DATABASE_NAME)
                .target(hostDB + DATABASE_NAME)
                .continuous(true)
                .build();

        dbInstance.replicate(pushReplicationCommand);
    }

}
