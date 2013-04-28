package de.htwg.seapal.aview.tui.states.waypoint;

import java.util.UUID;

import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;

public class EditSelectState implements TuiState {

	private UUID waypoint;
	private static String[] editWaypoint = new String[] { "Name", "Headed for",
			"Maneuver", "HeadSail", "MainSail" };

	public EditSelectState(UUID waypoint) {
		this.waypoint = waypoint;
	}

	@Override
	public String buildString(StateContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append("q - quit\n");
		sb.append("<x> - edit Attribute\n");
		sb.append("------------------------------------------------\n");
		int i = 1;
		for (String element : editWaypoint) {
			sb.append(i + ") " + element + "\n");
			++i;
		}
		return sb.toString();
	}

	@Override
	public boolean process(StateContext context, String input) {
		Integer number;
		try {
			number = Integer.valueOf(input) - 1;
		} catch (NumberFormatException e) {
			if (input.equals("q"))
				context.setState(new ShowState(waypoint));
			return false;
		}
		if (number >= 0 && number < editWaypoint.length)
			context.setState(new EditState(number, waypoint));
		// TODO Fehlerbehandlung (Toast)
		return true;
	}

	public static String getEditWaypointElement(int position) {
		return editWaypoint[position];
	}

}
