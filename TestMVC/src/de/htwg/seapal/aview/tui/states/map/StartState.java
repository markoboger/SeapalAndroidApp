package de.htwg.seapal.aview.tui.states.map;

import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.BoatActivity;
import de.htwg.seapal.aview.tui.states.boat.ShowState;
import android.content.Context;
import android.content.Intent;

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
			return false;
		}
		return true;
	}

}
