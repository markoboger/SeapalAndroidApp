package de.htwg.seapal.aview.gui.activity;

import android.os.Bundle;
import android.view.Menu;
import de.htwg.seapal.R;

public class TestDrawerMapActivity extends MenuActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return super.onPrepareOptionsMenu(menu);
	}
}
