package de.htwg.seapal.aview.tui.states.menu;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.BoatActivity;
import de.htwg.seapal.aview.tui.activity.MarkActivity;
import de.htwg.seapal.aview.tui.activity.RouteActivity;

public class StartState implements TuiState {

	@Override
	public String buildString(StateContext context) {
		return "l  -  logbuch\n" + "m  -  marks\n" + "r  -  routes\n"
				+ "g - guiLogbuch\n x - maps\n d - drawer";
	}

	@Override
	public void process(StateContext context, String input) {
		Intent intent = null;
		switch (input.charAt(0)) {
		case 'l':
			intent = new Intent((Context) context, BoatActivity.class);
			((Context) context).startActivity(intent);
			break;
		case 'm':
			intent = new Intent((Context) context, MarkActivity.class);
			((Context) context).startActivity(intent);
			break;
		case 'r':
			intent = new Intent((Context) context, RouteActivity.class);
			((Context) context).startActivity(intent);
			break;
		case 'g':
			intent = new Intent((Context) context,
					de.htwg.seapal.aview.gui.activity.BoatActivity.class);
			((Context) context).startActivity(intent);
			break;
		case 'x':
			intent = new Intent((Context) context,
					de.htwg.seapal.aview.gui.activity.MapActivity.class);
			((Context) context).startActivity(intent);
			break;
		case 'd':
			intent = new Intent((Context) context,
					de.htwg.seapal.aview.gui.activity.MapActivity.class);
			((Context) context).startActivity(intent);
			break;
		default:
			Toast.makeText((BoatActivity) context, "Unkown Option",
					Toast.LENGTH_SHORT).show();
        }
    }

}
