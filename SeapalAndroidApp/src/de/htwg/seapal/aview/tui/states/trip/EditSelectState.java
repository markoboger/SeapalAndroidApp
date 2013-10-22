package de.htwg.seapal.aview.tui.states.trip;

import java.util.UUID;

import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.TripActivity;

public class EditSelectState implements TuiState {

	private final UUID trip;
	private static final String[] editTrip = new String[] { "Name", "Start Location",
			"End Location", "Skipper", "Crew", "Motor (min)", "Fuel", "Notes" };

	public EditSelectState(UUID trip) {
		this.trip = trip;
	}

	@Override
	public String buildString(StateContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append("q - quit\n");
		sb.append("<x> - edit Attribute\n");
		sb.append("------------------------------------------------\n");
		int i = 1;
		for (String element : editTrip) {
			sb.append(i).append(") ").append(element).append("\n");
			++i;
		}
		return sb.toString();
	}

	@Override
	public void process(StateContext context, String input) {
		Integer number;
		try {
			number = Integer.valueOf(input) - 1;
		} catch (NumberFormatException e) {
			if (input.equals("q"))
				context.setState(new ShowState(trip));
			else
				Toast.makeText((TripActivity) context, "Unkown Option",
						Toast.LENGTH_SHORT).show();
            return;
		}
		if (number >= 0 && number < editTrip.length)
			context.setState(new EditState(number, trip));
		else
			Toast.makeText((TripActivity) context, "Unkown Option",
					Toast.LENGTH_SHORT).show();
    }

	public static String getEditTripElement(int position) {
		return editTrip[position];
	}

}
