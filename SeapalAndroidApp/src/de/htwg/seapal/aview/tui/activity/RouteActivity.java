package de.htwg.seapal.aview.tui.activity;

import android.widget.TextView;
import de.htwg.seapal.R;
import de.htwg.seapal.aview.tui.states.route.StartState;
import de.htwg.seapal.controller.IRouteController;
import de.htwg.seapal.controller.impl.RouteController;
import de.htwg.seapal.database.mock.RouteDatabase;
import de.htwg.seapal.utils.logging.Logger;
import de.htwg.seapal.utils.observer.IObserver;

public class RouteActivity extends AActivity implements IObserver {

	private IRouteController controller;

	@Override
	protected void setup() {
		TextView header = (TextView) this.findViewById(R.id.header);
		header.setText("Route");
		controller = new RouteController(new RouteDatabase(), new Logger());
		currenState = new StartState();
		controller.addObserver(this);

	}

	public IRouteController getController() {
		return controller;
	}

}
