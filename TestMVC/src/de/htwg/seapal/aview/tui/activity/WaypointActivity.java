package de.htwg.seapal.aview.tui.activity;

import java.util.UUID;

import android.os.Bundle;
import android.widget.TextView;
import de.htwg.seapal.R;
import de.htwg.seapal.aview.tui.states.waypoint.StartState;
import de.htwg.seapal.controller.IWaypointController;
import de.htwg.seapal.controller.impl.WaypointController;
import de.htwg.seapal.database.impl.HashMapWaypointDatabase;

public class WaypointActivity extends AActivity {

	private IWaypointController controller;
	private UUID trip;

	@Override
	protected void setup() {
		TextView header = (TextView) this.findViewById(R.id.header);
		header.setText("Waypoint");
		Bundle bundle = getIntent().getExtras();
		trip = UUID.fromString(bundle.getString("trip").toString());
		controller = new WaypointController(new HashMapWaypointDatabase());
		currenState = new StartState();
		controller.addObserver(this);
	}

	public IWaypointController getController() {
		return controller;
	}

	public UUID getTrip() {
		return trip;
	}
}
