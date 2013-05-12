package de.htwg.seapal.aview.tui.activity;

import android.widget.TextView;
import de.htwg.seapal.R;
import de.htwg.seapal.aview.tui.states.person.StartState;
import de.htwg.seapal.controller.IPersonController;
import de.htwg.seapal.controller.impl.PersonController;
import de.htwg.seapal.database.mock.PersonDatabase;
import de.htwg.seapal.utils.Logger;

public class PersonActivity extends AActivity {

	private IPersonController controller;

	@Override
	public void setup() {
		TextView header = (TextView) this.findViewById(R.id.header);
		header.setText("Person");
		// this.controller = new
		// PersonController(HashMapPersonDatabase.getInstance());
		this.controller = new PersonController(new PersonDatabase(),
				new Logger());
		currenState = new StartState();
		controller.addObserver(this);
	}

	public IPersonController getController() {
		return controller;
	}

}
