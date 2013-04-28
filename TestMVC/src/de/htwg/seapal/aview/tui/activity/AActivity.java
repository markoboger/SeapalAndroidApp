package de.htwg.seapal.aview.tui.activity;

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
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.observer.Event;
import de.htwg.seapal.observer.IObserver;

public abstract class AActivity extends Activity implements IObserver, StateContext {

	protected EditText in;
	protected TextView out;
	protected OnKeyListener onKeyListener;
	protected TuiState currenState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tui);
		
		setup();
		
		in = (EditText) findViewById(R.id.input);
		out = (TextView) findViewById(R.id.output);
		printTUI();
		
		onKeyListener = listener();
		in.setOnKeyListener(onKeyListener);
	}

	protected abstract void setup();
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void setState(TuiState newState) {
		currenState = newState;
	}

	protected void processInputLine() {
		String input = in.getText().toString();
		in.setText("");
		currenState.process(this, input);
		printTUI();
	}

	public String userInput(String message) {
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

	protected void printTUI() {
		out.setText(currenState.buildString(this));
	}

	protected OnKeyListener listener() {
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
