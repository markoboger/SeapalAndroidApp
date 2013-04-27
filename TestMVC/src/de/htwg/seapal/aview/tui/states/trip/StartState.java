package de.htwg.seapal.aview.tui.states.trip;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.TripActivity;
import de.htwg.seapal.controller.ITripController;


public class StartState implements TuiState {

	private UUID boat;
	
	@Override
	public String buildString(StateContext context) {
		ITripController controller = ((TripActivity) context).getController();
		boat = ((TripActivity) context).getBoat();
		StringBuilder sb = new StringBuilder();
		sb.append("n \t- New Trip\n");
		sb.append("<X> \t- Show Trip\n");
		sb.append("---------------------------------------\n");
		List<UUID> trips = controller.getTrips(boat);
		int i = 1;
		for (UUID uuid : trips) {
			sb.append(i++).append(")\t").append(controller.getName(uuid))
					.append("\n");
		}
		return sb.toString();
	}

	@Override
	public boolean process(StateContext context, String input) {
		// TODO Auto-generated method stub
		return false;
	}

}
