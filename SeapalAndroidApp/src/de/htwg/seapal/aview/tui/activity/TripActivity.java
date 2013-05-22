package de.htwg.seapal.aview.tui.activity;

import java.util.UUID;

import android.os.Bundle;
import android.widget.TextView;

import com.google.inject.Inject;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.tui.states.trip.StartState;
import de.htwg.seapal.controller.ITripController;
import de.htwg.seapal.utils.observer.IObserver;

public class TripActivity extends AActivity implements IObserver {

	@Inject
	private ITripController controller;
	private UUID boat;

	@Override
	public void setup() {
		TextView header = (TextView) this.findViewById(R.id.header);
		header.setText("Trip");
		Bundle bundle = getIntent().getExtras();
		boat = UUID.fromString(bundle.getString("boat").toString());
		currenState = new StartState();
		controller.addObserver(this);
	}

	public ITripController getController() {
		return controller;
	}

	public UUID getBoat() {
		return boat;
	}

}
