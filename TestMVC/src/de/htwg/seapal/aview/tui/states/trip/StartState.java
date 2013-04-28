package de.htwg.seapal.aview.tui.states.trip;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.TripActivity;
import de.htwg.seapal.controller.ITripController;

public class StartState implements TuiState {

	private UUID boat;
	private List<UUID> trips;

	@Override
	public String buildString(StateContext context) {
		ITripController controller = ((TripActivity) context).getController();
		boat = ((TripActivity) context).getBoat();
		StringBuilder sb = new StringBuilder();
		sb.append("n \t- New Trip\n");
		sb.append("<X> \t- Show Trip\n");
		sb.append("---------------------------------------\n");
		trips = controller.getTrips(boat);
		int i = 1;
		for (UUID uuid : trips) {
			sb.append(i++).append(")\t").append(controller.getName(uuid))
					.append("\n");
		}
		return sb.toString();
	}

	@Override
	public boolean process(StateContext context, String input) {
		Integer number;
		try {
			number = Integer.valueOf(input) - 1;
		} catch (NumberFormatException e) {
			if (input.equals("n"))
				context.setState(new NewState());
			return false;
		}
		if (number < trips.size() && number >= 0)
			context.setState(new ShowState(trips.get(number)));
		return false;
	}

}
