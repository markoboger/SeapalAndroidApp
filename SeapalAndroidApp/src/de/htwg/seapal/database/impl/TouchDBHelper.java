package de.htwg.seapal.database.impl;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.ektorp.CBLiteHttpClient;
import com.google.inject.Inject;

import org.ektorp.CouchDbConnector;
import org.ektorp.ReplicationCommand;
import org.ektorp.http.HttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import java.io.File;
import java.io.IOException;


public class TouchDBHelper {

	private static final String TAG = "TouchDB";
	private final String DATABASE_NAME;
    private final String hostDB = "http://192.168.0.107/";
    private StdCouchDbInstance dbInstance;
	private CouchDbConnector couchDbConnector;
	private Database tdDB;


    @Inject
    public TouchDBHelper(String dbName, Context ctx) {
		DATABASE_NAME = dbName;
        createDatabase(ctx);
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

		// start TouchDB-Ektorp adapter
		HttpClient httpClient = new CBLiteHttpClient(server);
		dbInstance = new StdCouchDbInstance(httpClient);

		// create a local database
		couchDbConnector = dbInstance.createConnector(DATABASE_NAME, true);


        if (server != null) {
            try {
                tdDB = server.getDatabase(DATABASE_NAME);
            } catch (CouchbaseLiteException e) {
                e.printStackTrace();
            }
        }


        pullFromDatabase();
        pushToDatabase();



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

}
