package de.chritte.testmvc.aview.tui.states.map;

import android.content.Context;
import android.content.Intent;
import de.chritte.testmvc.aview.tui.StateContext;
import de.chritte.testmvc.aview.tui.TuiState;
import de.chritte.testmvc.aview.tui.activity.BoatActivity;
import de.chritte.testmvc.aview.tui.states.boat.ShowState;

public class StartState implements TuiState {

	@Override
	public String buildString(StateContext context) {
		return "l  -  logbuch\n" + "d  -  Dashboard\n" + "m  -  marks\n"
				+ "r  -  routes";
	}

	@Override
	public boolean process(StateContext context, String input) {
		Intent intent = null;
		char c = input.charAt(0);
		switch (c) {
		case 'l':
			intent = new Intent((Context) context, BoatActivity.class);
			((Context) context).startActivity(intent);
			break;
		case 's':
			context.setState(new ShowState());
			break;
		default:
			return false;
		}
		return true;
	}

}
