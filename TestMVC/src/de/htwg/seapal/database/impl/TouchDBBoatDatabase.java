package de.htwg.seapal.database.impl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.ektorp.CouchDbConnector;
import org.ektorp.DocumentNotFoundException;
import org.ektorp.ReplicationCommand;
import org.ektorp.UpdateConflictException;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.ViewResult.Row;
import org.ektorp.http.HttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.support.CouchDbDocument;

import com.couchbase.touchdb.TDDatabase;
import com.couchbase.touchdb.TDServer;
import com.couchbase.touchdb.TDView;
import com.couchbase.touchdb.TDViewMapBlock;
import com.couchbase.touchdb.TDViewMapEmitBlock;
import com.couchbase.touchdb.TDViewReduceBlock;
import com.couchbase.touchdb.ektorp.TouchDBHttpClient;

import android.content.Context;
import android.util.Log;

import de.htwg.seapal.database.IBoatDatabase;
import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.model.impl.Boat;

public class TouchDBBoatDatabase extends CouchDbDocument implements IBoatDatabase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final String TAG = "TouchDB";
	public static final String dDocName = "seapal-boats";
	public static final String dDocId = "_design/" + dDocName;
	public static final String viewName = "boats";
	private static final String DATABASE_NAME = "seapaldb";
	
	private static TouchDBBoatDatabase touchDBBoatDatabase;
	private CouchDbConnector couchDbConnector;
	private StdCouchDbInstance dbInstance;

	public TouchDBBoatDatabase(Context ctx) {
		super();
		//TouchDB
		Log.d(TAG , "Starting TouchDB");
		TDServer server = null;
		String filesDir = ctx.getFilesDir().getAbsolutePath();
		Log.d(TAG, ctx.getFilesDir().getAbsolutePath());
		try {
			server = new TDServer(filesDir);
		} catch (IOException e) {
			Log.e(TAG, "Error starting TDServer", e);
		}

		// start TouchDB-Ektorp adapter
		HttpClient httpClient = new TouchDBHttpClient(server);
		dbInstance = new StdCouchDbInstance(httpClient);

		// create a local database
		couchDbConnector = dbInstance.createConnector(DATABASE_NAME, true);

		TDDatabase db = server.getDatabaseNamed(DATABASE_NAME);

		TDView view = db.getViewNamed(String.format("%s/%s", dDocName, viewName));

		view.setMapReduceBlocks(new TDViewMapBlock() {

			@Override
			public void map(Map<String, Object> document, TDViewMapEmitBlock emitter) {
				String type = (String)document.get("type");
		          if("person".equals(type)) {
		              emitter.emit(null, document.get("_id"));
		          }
			}
		}, new TDViewReduceBlock() {
			public Object reduce(List<Object> keys, List<Object> values, boolean rereduce) {
				return null;
			}

		}, "1.0");
		
		ReplicationCommand pullReplicationCommand = new ReplicationCommand.Builder()
		.source("http://roroettg.iriscouch.com/seapaldb")
		.target(DATABASE_NAME)
		.continuous(true)
		.build();
		
		dbInstance.replicate(pullReplicationCommand);
		

	}
	public static TouchDBBoatDatabase getInstance(Context ctx)	{
		if (touchDBBoatDatabase == null)
			touchDBBoatDatabase = new TouchDBBoatDatabase(ctx);
		return touchDBBoatDatabase;
	}
	@Override
	public UUID newBoat() {
		IBoat boat = new Boat();
		try {
			couchDbConnector.create(boat.getId(), boat);
		} catch (UpdateConflictException e) {
			Log.d(TAG, e.toString());
		}
		UUID idx = UUID.fromString(boat.getId());
		Log.d(TAG, "Boat created: " + boat.getId());
		pushToDatabase();
		return idx;
	}
	@Override
	public void saveBoat(IBoat boat) {
		Log.d(TAG, "Boat saving: " + boat.getId());
		try {
			couchDbConnector.update(boat);
		} catch (DocumentNotFoundException e) {
			Log.d(TAG, "Document not Found");
			Log.d(TAG, e.toString());
			return;
		}
		
	}
	@Override
	public void deleteBoat(UUID id) {
		try {
			couchDbConnector.delete(getBoat(id));
		} catch (Exception e) {
			Log.d(TAG, e.toString());
			return;
		}
		Log.d(TAG, "Boat deleted");
	}
	@Override
	public IBoat getBoat(UUID id) {
		IBoat boat;
		try {
			boat = couchDbConnector.get(Boat.class, id.toString());
		} catch (DocumentNotFoundException e) {
			Log.d(TAG, "Boat not found" + id.toString());
			return null;
		}
		return boat;
	}
	@Override
	public List<IBoat> getBoats() {
		List<IBoat> lst = new LinkedList<IBoat>();
		ViewQuery query = new ViewQuery().allDocs();		
		ViewResult vr = couchDbConnector.queryView(query);
		
		Log.d(TAG, vr.getTotalRows() + "");
		for(Row r : vr.getRows()) {
			lst.add(getBoat(UUID.fromString(r.getId())));
		}
		return lst;
	}
	@Override
	public boolean closeDB() {
		return false;
	}
	
	public void pushToDatabase() {
		ReplicationCommand pushReplicationCommand = new ReplicationCommand.Builder()
		.source(DATABASE_NAME)
		.target("http://roroettg.iriscouch.com/seapaldb")
		.continuous(true)
		.build();
		
		dbInstance.replicate(pushReplicationCommand);
	}

}
