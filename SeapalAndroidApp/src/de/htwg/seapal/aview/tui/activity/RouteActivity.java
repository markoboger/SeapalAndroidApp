package de.htwg.seapal.aview.tui.activity;

import android.widget.TextView;

import com.google.inject.Inject;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.tui.states.route.StartState;
import de.htwg.seapal.controller.IRouteController;
import de.htwg.seapal.utils.observer.IObserver;

public class RouteActivity extends AActivity implements IObserver {

	@Inject
	private IRouteController controller;

	@Override
	protected void setup() {
		TextView header = (TextView) this.findViewById(R.id.header);
		header.setText("Route");
		currenState = new StartState();
		controller.addObserver(this);

	}

	public IRouteController getController() {
		return controller;
	}

}
