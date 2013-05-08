package de.htwg.seapal.aview.tui.activity;

import android.widget.TextView;
import de.htwg.seapal.R;
import de.htwg.seapal.aview.tui.states.route.StartState;
import de.htwg.seapal.controller.IRouteController;
import de.htwg.seapal.controller.impl.RouteController;
import de.htwg.seapal.database.impl.HashMapRouteDatabase;
import de.htwg.seapal.observer.IObserver;

public class RouteActivity extends AActivity implements IObserver {

	private IRouteController controller;

	@Override
	protected void setup() {
		TextView header = (TextView) this.findViewById(R.id.header);
		header.setText("Route");
		controller = new RouteController(HashMapRouteDatabase.getInstance());
		currenState = new StartState();
		controller.addObserver(this);
		
	}

	public IRouteController getController() {
		return controller;
	}

}
