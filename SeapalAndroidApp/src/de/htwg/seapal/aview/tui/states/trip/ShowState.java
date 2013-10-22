package de.htwg.seapal.aview.tui.states.trip;

import java.util.UUID;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.TripActivity;
import de.htwg.seapal.aview.tui.activity.TripRecordActivity;
import de.htwg.seapal.aview.tui.activity.WaypointActivity;

public class ShowState implements TuiState {
	private final UUID trip;

	public ShowState(UUID trip) {
		this.trip = trip;
	}

	@Override
	public String buildString(StateContext context) {

		StringBuilder sb = new StringBuilder();
		sb.append("q - quit\n");
		sb.append("r - record trip\n");
		sb.append("d - delete trip\n");
		sb.append("e - edit trip\n");
		sb.append("w - show waypoints\n");
		sb.append("--------------------------------------------------\n");
		sb.append(((TripActivity) context).getController().getString(trip));
		return sb.toString();
	}

	@Override
	public void process(StateContext context, String input) {

		switch (input.charAt(0)) {
		case 'q':
			context.setState(new StartState());
			break;
		case 'r':
			Intent intentR = new Intent((Context) context,
					TripRecordActivity.class);
			intentR.putExtra("trip", trip.toString());
			((Context) context).startActivity(intentR);
			break;
		case 'd':
			context.setState(new StartState());
			((TripActivity) context).getController().deleteTrip(trip);
			break;
		case 'e':
			context.setState(new EditSelectState(trip));
			break;
		case 'w':
			Intent intentW = new Intent((Context) context,
					WaypointActivity.class);
			intentW.putExtra("trip", trip.toString());
			((Context) context).startActivity(intentW);
			break;
		default:
			Toast.makeText((TripActivity) context, "Unkown Option",
					Toast.LENGTH_SHORT).show();
        }

    }

}