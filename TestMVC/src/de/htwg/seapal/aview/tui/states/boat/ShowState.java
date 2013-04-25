package de.htwg.seapal.aview.tui.states.boat;

import java.util.UUID;

import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.BoatActivity;
import de.htwg.seapal.aview.tui.activity.TripActivity;

import android.content.Context;
import android.content.Intent;


public class ShowState implements TuiState {

	private UUID boat;

	public ShowState(UUID boat) {
		this.boat = boat;
	}

	@Override
	public String buildString(StateContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append("q - quit\n");
		sb.append("d - delete boat\n");
		sb.append("e - edit\n");
		sb.append("t - show trips\n");
		sb.append("--------------------------------------------------\n");
		sb.append(((BoatActivity) context).getController().getString(boat));
		return sb.toString();
	}

	@Override
	public boolean process(StateContext context, String input) {

		switch (input.charAt(0)) {
		case 'q':
			context.setState(new StartState());
			break;
		case 'd':
			context.setState(new StartState());
			((BoatActivity)context).getController().deleteBoat(boat);
			break;
		case 'e':
			context.setState(new EditSelectState(boat)); 
			break;
		case 't':
			Intent intent = new Intent((Context) context, TripActivity.class);
			intent.putExtra("boat", boat.toString());
			((Context) context).startActivity(intent);
			break;
		default:
			return false;
		}
		
		return true;
	}

}
