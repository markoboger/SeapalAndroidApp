package de.htwg.seapal.aview.tui.states.trip;

import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.TripActivity;
import de.htwg.seapal.aview.tui.activity.WaypointActivity;

public class ShowState implements TuiState {
	private UUID trip;

	public ShowState(UUID trip) {
		this.trip = trip;
	}

	@Override
	public String buildString(StateContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append("q - quit\n");
		sb.append("d - delete trip\n");
		sb.append("e - edit trip\n");
		sb.append("w - show waypoints\n");
		sb.append("--------------------------------------------------\n");
		sb.append(((TripActivity) context).getController().getString(trip));
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
			((TripActivity) context).getController().deleteTrip(trip);
			break;
		case 'e':
			context.setState(new EditSelectState(trip));
			break;
		case 'w':
			Intent intent = new Intent((Context) context,
					WaypointActivity.class);
			intent.putExtra("trip", trip.toString());
			((Context) context).startActivity(intent);
			break;
		default:
			return false;
		}

		return true;
	}

}