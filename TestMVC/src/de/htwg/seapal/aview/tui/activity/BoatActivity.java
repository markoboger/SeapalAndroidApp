package de.htwg.seapal.aview.tui.activity;

import android.widget.TextView;
import de.htwg.seapal.R;
import de.htwg.seapal.aview.tui.states.boat.StartState;
import de.htwg.seapal.controller.IBoatController;
import de.htwg.seapal.controller.impl.BoatController;
import de.htwg.seapal.database.impl.HashMapBoatDatabase;
import de.htwg.seapal.observer.IObserver;

public class BoatActivity extends AActivity implements IObserver {

	private IBoatController controller;

	@Override
	public void setup() {
		TextView header = (TextView) this.findViewById(R.id.header);
		header.setText("Boat");
		this.controller = new BoatController(HashMapBoatDatabase.getInstance());
		currenState = new StartState();
		controller.addObserver(this);
	}

	public IBoatController getController() {
		return controller;
	}
}
