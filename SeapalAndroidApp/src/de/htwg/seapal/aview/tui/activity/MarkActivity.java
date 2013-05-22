package de.htwg.seapal.aview.tui.activity;

import android.widget.TextView;

import com.google.inject.Inject;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.tui.states.mark.StartState;
import de.htwg.seapal.controller.IMarkController;
import de.htwg.seapal.utils.observer.IObserver;

public class MarkActivity extends AActivity implements IObserver {

	@Inject
	private IMarkController controller;

	@Override
	protected void setup() {
		TextView header = (TextView) this.findViewById(R.id.header);
		header.setText("Mark");
		currenState = new StartState();
		controller.addObserver(this);
	}

	public IMarkController getController() {
		return controller;
	}

}
