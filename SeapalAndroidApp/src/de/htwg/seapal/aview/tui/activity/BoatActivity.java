package de.htwg.seapal.aview.tui.activity;

import roboguice.inject.InjectView;
import android.widget.TextView;

import com.google.inject.Inject;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.tui.states.boat.StartState;
import de.htwg.seapal.controller.IBoatController;
import de.htwg.seapal.utils.observer.IObserver;

public class BoatActivity extends AActivity implements IObserver {

	@Inject
	private IBoatController controller;
	@InjectView(R.id.header)
	private TextView header;

	@Override
	public void setup() {
		header.setText("Boat");
		currenState = new StartState();
		controller.addObserver(this);
	}

	public IBoatController getController() {
		return controller;
	}

}
