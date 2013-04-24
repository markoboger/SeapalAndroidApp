package de.chritte.testmvc.Aview.activity;

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
import de.chritte.testmvc.R;
import de.chritte.testmvc.controller.ITripController;
import de.chritte.testmvc.controller.IWaypointController;
import de.chritte.testmvc.controller.impl.TripController;
import de.chritte.testmvc.controller.impl.WaypointController;
import de.chritte.testmvc.observer.Event;
import de.chritte.testmvc.observer.IObserver;

public class WaypointActivity extends Activity implements IObserver {

	private IWaypointController controller;
	private EditText in;
	private TextView out;
	private OnKeyListener onKeyListener;
	private UUID uuid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waypoint);

		controller = new WaypointController();
		controller.addObserver(this);

		in = (EditText) findViewById(R.id.input);
		out = (TextView) findViewById(R.id.output);
		printTUI();

		onKeyListener = listener();
		in.setOnKeyListener(onKeyListener);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void processInputLine() {

		

	}

	private String userInput(String message) {
		final String[] userInput = new String[1];
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Your Input");
		alert.setMessage(message);

		final EditText input = new EditText(this);
		alert.setView(input);

		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				userInput[0] = input.getText().toString();
			}
		});

		alert.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				});

		alert.show();
		return userInput[0];
	}

	@Override
	public void update(Event e) {
		printTUI();
	}

	private void printTUI() {
		out.setText("l - List Trips\n" + "s - Set TripName\n" + "q - quit");
	}

	private OnKeyListener listener() {
		return new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				boolean handled = false;
				if (event.getAction() == KeyEvent.ACTION_DOWN
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {

					processInputLine();
					handled = true;
				}
				return handled;
			}
		};
	}
}
