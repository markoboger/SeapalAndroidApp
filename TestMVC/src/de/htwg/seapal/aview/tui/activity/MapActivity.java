package de.htwg.seapal.aview.tui.activity;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.tui.states.map.StartState;

public class MapActivity extends AActivity {

	@Override
	protected void setup() {
		setContentView(R.layout.map);
		currenState = new StartState();
	}
}
