package de.chritte.testmvc.Aview.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;
import de.chritte.testmvc.R;

public class MapActivity extends Activity {

	private EditText in;
	private TextView out;
	private OnKeyListener onKeyListener;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		in = (EditText) findViewById(R.id.input);
		out = (TextView) findViewById(R.id.output);
		onKeyListener = listener();

		in.setOnKeyListener(onKeyListener);

		printTUI();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void processInputLine() {
		if (in.getText().toString().equals("l")) {

			Intent intent = new Intent(this, BoatActivity.class);
			startActivity(intent);
		}
	}

	private void printTUI() {
		out.setText("l  -  logbuch\n" +
					"d  -  Dashboard\n" + 
					"m  -  marks\n" +
					"r  -  routes");
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
