package de.htwg.seapal.database.impl;

import java.io.IOException;
import org.ektorp.CouchDbConnector;
import org.ektorp.ReplicationCommand;
import org.ektorp.http.HttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import android.content.Context;
import android.util.Log;

import com.couchbase.cblite.*;
import com.couchbase.cblite.ektorp.*;

class TouchDBHelper {

	private static final String TAG = "TouchDB";
	private final String DATABASE_NAME;
    private final String hostDB = "http://roroettg.iriscouch.com/";
    private StdCouchDbInstance dbInstance;
	private CouchDbConnector couchDbConnector;
	private CBLDatabase tdDB;


    public TouchDBHelper(String dbName) {
		DATABASE_NAME = dbName;		
	}

	public void createDatabase(Context ctx) {

		if (couchDbConnector != null) {
			return;
		}
		// TouchDB
		Log.d(TAG, "Starting " + DATABASE_NAME);
		CBLServer server = null;
		String filesDir = ctx.getFilesDir().getAbsolutePath();
		Log.d(TAG, ctx.getFilesDir().getAbsolutePath());
		try {
			server = new CBLServer(filesDir);
		} catch (IOException e) {
			Log.e(TAG, "Error starting Boat-TDServer", e);
		}

		// start TouchDB-Ektorp adapter
		HttpClient httpClient = new CBLiteHttpClient(server);
		dbInstance = new StdCouchDbInstance(httpClient);

		// create a local database
		couchDbConnector = dbInstance.createConnector(DATABASE_NAME, true);

        if (server != null) {
            tdDB = server.getDatabaseNamed(DATABASE_NAME);
        }



    }

	public CouchDbConnector getCouchDbConnector() {
		return this.couchDbConnector;
	}
	
	public CBLDatabase getTDDatabase() {
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
