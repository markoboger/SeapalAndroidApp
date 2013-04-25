package de.chritte.testmvc.aview.tui.activity;

import de.chritte.testmvc.R;
import de.chritte.testmvc.aview.tui.states.boat.StartState;
import de.chritte.testmvc.controller.IBoatController;
import de.chritte.testmvc.controller.impl.BoatController;
import de.chritte.testmvc.database.impl.ListBoatDatabase;
import de.chritte.testmvc.observer.IObserver;

public class BoatActivity extends AActivity implements IObserver {

	private IBoatController controller;

	@Override
	public void setup() {
		setContentView(R.layout.boat);
		setController(new BoatController(new ListBoatDatabase()));
		currenState = new StartState();
	}

	public IBoatController getController() {
		return controller;
	}

	public void setController(IBoatController controller) {
		this.controller = controller;
	}
	
	
}
