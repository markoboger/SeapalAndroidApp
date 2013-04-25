package en.htwg.seapal.aview.tui.activity;

import de.chritte.testmvc.R;
import en.htwg.seapal.aview.tui.states.boat.StartState;
import en.htwg.seapal.controller.IBoatController;
import en.htwg.seapal.controller.impl.BoatController;
import en.htwg.seapal.database.impl.HashMapBoatDatabase;
import en.htwg.seapal.observer.IObserver;

public class BoatActivity extends AActivity implements IObserver {

	private IBoatController controller;

	@Override
	public void setup() {
		setContentView(R.layout.boat);
		this.controller = new BoatController(new HashMapBoatDatabase());
		currenState = new StartState();
	}

	public IBoatController getController() {
		return controller;
	}
}
