package de.htwg.seapal.database.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.ektorp.CouchDbConnector;
import org.ektorp.ReplicationCommand;
import org.ektorp.http.HttpClient;
import org.ektorp.impl.StdCouchDbInstance;

import android.app.Application;
import android.util.Log;

import com.couchbase.touchdb.TDDatabase;
import com.couchbase.touchdb.TDServer;
import com.couchbase.touchdb.TDView;
import com.couchbase.touchdb.TDViewMapBlock;
import com.couchbase.touchdb.TDViewMapEmitBlock;
import com.couchbase.touchdb.TDViewReduceBlock;
import com.couchbase.touchdb.ektorp.TouchDBHttpClient;

public class TouchDBHelper {

	private static final String TAG = "TouchDB";
	private String DDOCNAME;
	private String VIEWNAME;
	private String DATABASE_NAME;
	private StdCouchDbInstance dbInstance;
	private CouchDbConnector couchDbConnector;

	public TouchDBHelper(String viewName, String dbName, String dDocName) {
		DATABASE_NAME = dbName;
		DDOCNAME = dDocName;
		VIEWNAME = viewName;
	}

	public boolean createDatabase(Application ctx) {
		
		if(couchDbConnector != null) {
			return true;
		}
		// TouchDB
		Log.d(TAG, "Starting " + DATABASE_NAME);
		TDServer server = null;
		String filesDir = ctx.getFilesDir().getAbsolutePath();
		Log.d(TAG, ctx.getFilesDir().getAbsolutePath());
		try {
			server = new TDServer(filesDir);
		} catch (IOException e) {
			Log.e(TAG, "Error starting Boat-TDServer", e);
		}

		// start TouchDB-Ektorp adapter
		HttpClient httpClient = new TouchDBHttpClient(server);
		dbInstance = new StdCouchDbInstance(httpClient);

		// create a local database
		couchDbConnector = dbInstance.createConnector(DATABASE_NAME, true);

		TDDatabase db = server.getDatabaseNamed(DATABASE_NAME);

		TDView view = db.getViewNamed(String
				.format("%s/%s", DDOCNAME, VIEWNAME));

		view.setMapReduceBlocks(new TDViewMapBlock() {

			@Override
			public void map(Map<String, Object> document,
					TDViewMapEmitBlock emitter) {
			}
		}, new TDViewReduceBlock() {
			public Object reduce(List<Object> keys, List<Object> values,
					boolean rereduce) {
				return null;
			}

		}, "1.0");
		return false;

	}

	public CouchDbConnector getCouchDbConnector() {
		return this.couchDbConnector;
	}

	public void pullFromDatabase() {
		ReplicationCommand pullReplicationCommand = new ReplicationCommand.Builder()
				.source("http://roroettg.iriscouch.com/" + DATABASE_NAME)
				.target(DATABASE_NAME).continuous(true).build();

		dbInstance.replicate(pullReplicationCommand);

	}

	public void pushToDatabase() {
		ReplicationCommand pushReplicationCommand = new ReplicationCommand.Builder()
				.source(DATABASE_NAME)
				.target("http://roroettg.iriscouch.com/" + DATABASE_NAME)
				.continuous(true).build();

		dbInstance.replicate(pushReplicationCommand);
	}

}
