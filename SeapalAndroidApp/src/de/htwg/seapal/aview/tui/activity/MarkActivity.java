package de.htwg.seapal.aview.tui.activity;

import android.widget.TextView;
import de.htwg.seapal.R;
import de.htwg.seapal.aview.tui.states.mark.StartState;
import de.htwg.seapal.controller.IMarkController;
import de.htwg.seapal.controller.impl.MarkController;
import de.htwg.seapal.database.impl.HashMapMarkDatabase;
import de.htwg.seapal.utils.Logger;
import de.htwg.seapal.utils.observer.IObserver;

public class MarkActivity extends AActivity implements IObserver {

	private IMarkController controller;

	@Override
	protected void setup() {
		TextView header = (TextView) this.findViewById(R.id.header);
		header.setText("Mark");
		controller = new MarkController(HashMapMarkDatabase.getInstance(),
				new Logger());
		currenState = new StartState();
		controller.addObserver(this);
	}

	public IMarkController getController() {
		return controller;
	}

}
