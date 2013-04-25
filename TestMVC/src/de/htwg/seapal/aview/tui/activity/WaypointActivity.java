package de.htwg.seapal.aview.tui.activity;

import de.htwg.seapal.R;
import de.htwg.seapal.controller.IWaypointController;
import de.htwg.seapal.controller.impl.WaypointController;
import de.htwg.seapal.database.impl.HashMapWaypointDatabase;

public class WaypointActivity extends AActivity {

	private IWaypointController controller;

	@Override
	protected void setup() {
		setContentView(R.layout.waypoint);

		controller = new WaypointController(new HashMapWaypointDatabase());
		controller.addObserver(this);
	}
}
