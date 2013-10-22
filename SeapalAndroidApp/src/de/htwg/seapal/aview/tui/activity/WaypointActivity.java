package de.htwg.seapal.aview.tui.activity;

import java.util.UUID;

import android.os.Bundle;
import android.widget.TextView;

import com.google.inject.Inject;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.tui.states.waypoint.StartState;
import de.htwg.seapal.controller.IWaypointController;

public class WaypointActivity extends AActivity {

	@Inject
	private IWaypointController controller;
	private UUID trip;

	@Override
	protected void setup() {
		TextView header = (TextView) this.findViewById(R.id.header);
		header.setText("Waypoint");
		Bundle bundle = getIntent().getExtras();
		trip = UUID.fromString(bundle.getString("trip"));
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
