package de.htwg.seapal.aview.tui.states.recordTrip;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.TripRecordActivity;
import de.htwg.seapal.controller.impl.WaypointController;

public class StartState implements TuiState {

	private WaypointController controller;

	@Override
	public String buildString(StateContext context) {
		controller = ((TripRecordActivity) context).getController();
		UUID trip = ((TripRecordActivity) context).getTrip();
		StringBuilder sb = new StringBuilder();
		sb.append("n \t- New Trip\n");
		sb.append("<X> \t- Show Trip\n");
		sb.append("---------------------------------------\n");
		List<UUID> waypoints = controller.getWaypoints(trip);
		int i = 1;
		for (UUID waypoint : waypoints) {
			sb.append(i++).append(")\t").append(controller.getName(waypoint))
					.append(" \t ").append(controller.getLatitude(waypoint))
					.append(" - ").append(controller.getLongitude(waypoint))
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
