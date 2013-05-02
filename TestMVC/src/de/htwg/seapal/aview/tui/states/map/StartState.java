package de.htwg.seapal.aview.tui.states.map;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.BoatActivity;

public class StartState implements TuiState {

	@Override
	public String buildString(StateContext context) {
		return "l  -  logbuch\n" + "d  -  Dashboard\n" + "m  -  marks\n"
				+ "r  -  routes";
	}

	@Override
	public boolean process(StateContext context, String input) {
		Intent intent = null;
		switch (input.charAt(0)) {
		case 'l':
			intent = new Intent((Context) context, BoatActivity.class);
			((Context) context).startActivity(intent);
			break;
		default:
			Toast.makeText((BoatActivity) context, "Unkown Option",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

}
