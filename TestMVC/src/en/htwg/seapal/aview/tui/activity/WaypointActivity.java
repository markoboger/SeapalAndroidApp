package en.htwg.seapal.aview.tui.activity;

import de.htwg.seapal.R;
import en.htwg.seapal.controller.IWaypointController;
import en.htwg.seapal.controller.impl.WaypointController;
import en.htwg.seapal.database.impl.HashMapWaypointDatabase;

public class WaypointActivity extends AActivity {

	private IWaypointController controller;

	@Override
	protected void setup() {
		setContentView(R.layout.waypoint);

		controller = new WaypointController(new HashMapWaypointDatabase());
		controller.addObserver(this);
	}
}
