package de.htwg.seapal.aview.tui.activity;

import android.widget.TextView;
import de.htwg.seapal.R;
import de.htwg.seapal.aview.tui.states.menu.StartState;

public class MenuActivity extends AActivity {

	@Override
	protected void setup() {
		TextView header = (TextView) this.findViewById(R.id.header);
		header.setText("Menu");
		currenState = new StartState();
	}
}
