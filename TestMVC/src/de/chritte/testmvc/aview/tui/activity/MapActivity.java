package de.chritte.testmvc.aview.tui.activity;

import de.chritte.testmvc.R;
import de.chritte.testmvc.aview.tui.states.map.StartState;

public class MapActivity extends AActivity {

	@Override
	protected void setup() {
		setContentView(R.layout.map);
		currenState = new StartState();
	}
}
