package de.htwg.seapal.aview.tui.activity;

import roboguice.inject.InjectView;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.TextView;

import com.couchbase.cblite.router.CBLURLStreamHandlerFactory;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.activity.BaseDrawerActivity;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.utils.observer.Event;
import de.htwg.seapal.utils.observer.IObserver;

public abstract class AActivity extends BaseDrawerActivity implements
		IObserver, StateContext {

	@InjectView(R.id.input)
    private EditText in;
	@InjectView(R.id.output)
    private TextView out;
    TuiState currenState;

	static {
		CBLURLStreamHandlerFactory.registerSelfIgnoreError();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tui);
		setup();

		printTUI();
		out.setMovementMethod(ScrollingMovementMethod.getInstance());
		out.setFocusable(false);
        OnKeyListener onKeyListener = listener();
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

	void processInputLine() {
        String input = "";
        if (in != null && in.getText() != null) input = in.getText().toString();
        if (in != null) {
            in.setText("");
        }
        currenState.process(this, input);
		printTUI();
	}

	@Override
	public void update(Event e) {
		printTUI();
	}

	void printTUI() {
		out.setText(currenState.buildString(this));
		// in.requestFocus();
	}

	OnKeyListener listener() {
		return new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				boolean handled = false;
				if (event.getAction() == KeyEvent.ACTION_DOWN
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					if (in != null && in.getText() != null && !"".equals(in.getText().toString()))
						processInputLine();

					handled = true;
				}
				return handled;
			}
		};
	}
}
