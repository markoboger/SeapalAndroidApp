package en.htwg.seapal.aview.tui.activity;

<<<<<<< HEAD
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;
import de.htwg.seapal.R;
import en.htwg.seapal.controller.ITripController;
=======
import de.chritte.testmvc.R;
>>>>>>> refs/remotes/origin/master
import en.htwg.seapal.controller.IWaypointController;
import en.htwg.seapal.controller.impl.WaypointController;
import en.htwg.seapal.database.impl.HashMapWaypointDatabase;

public class WaypointActivity extends AActivity {

	private IWaypointController controller;

	@Override
	protected void setup() {
		setContentView(R.layout.waypoint);

		controller = new WaypointController(new HashMapWaypointDatabase());
		controller.addObserver(this);
	}
}
