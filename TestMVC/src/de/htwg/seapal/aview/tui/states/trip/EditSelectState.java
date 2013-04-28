package de.htwg.seapal.aview.tui.states.trip;

import java.util.UUID;

import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;

public class EditSelectState implements TuiState {

	private UUID trip;
	private static String[] editTrip = new String[] { "Name", "Start Location",
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
			sb.append(i + ") " + element + "\n");
			++i;
		}
		return sb.toString();
	}

	@Override
	public boolean process(StateContext context, String input) {
		Integer number;
		try {
			number = Integer.valueOf(input);
		} catch (NumberFormatException e) {
			if (input.equals("q"))
				context.setState(new ShowState(trip));
			return false;
		}
		context.setState(new EditState(number - 1, trip));

		return true;
	}

	public static String getEditTripElement(int position) {
		return editTrip[position];
	}

}
