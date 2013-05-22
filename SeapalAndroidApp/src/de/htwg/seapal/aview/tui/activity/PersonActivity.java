package de.htwg.seapal.aview.tui.activity;

import android.widget.TextView;

import com.google.inject.Inject;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.tui.states.person.StartState;
import de.htwg.seapal.controller.IPersonController;

public class PersonActivity extends AActivity {

	@Inject
	private IPersonController controller;

	@Override
	public void setup() {
		TextView header = (TextView) this.findViewById(R.id.header);
		header.setText("Person");
		currenState = new StartState();
		controller.addObserver(this);
	}

	public IPersonController getController() {
		return controller;
	}

}
