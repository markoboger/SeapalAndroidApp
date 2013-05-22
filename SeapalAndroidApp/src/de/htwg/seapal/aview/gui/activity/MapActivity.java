package de.htwg.seapal.aview.gui.activity;

import roboguice.activity.RoboActivity;
import android.os.Bundle;
import de.htwg.seapal.R;

public class MapActivity extends RoboActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);
	}
}
