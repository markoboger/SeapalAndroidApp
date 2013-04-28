package de.htwg.seapal.aview.tui.states.waypoint;

import java.util.UUID;

import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.WaypointActivity;
import de.htwg.seapal.controller.IWaypointController;
import de.htwg.seapal.model.IWaypoint.ForeSail;
import de.htwg.seapal.model.IWaypoint.MainSail;
import de.htwg.seapal.model.IWaypoint.Maneuver;

public class EditState implements TuiState {
	private int position;
	private UUID waypoint;

	public EditState(int position, UUID waypoint) {
		this.position = position;
		this.waypoint = waypoint;
	}

	@Override
	public String buildString(StateContext context) {
		return "q - quit \n\n Enter "
				+ EditSelectState.getEditWaypointElement(position);
	}

	@Override
	public boolean process(StateContext context, String input) {
		if (input.equals("q")) {
			context.setState(new EditSelectState(waypoint));
			return false;
		}
		IWaypointController controller = ((WaypointActivity) context).getController();
		context.setState(new ShowState(waypoint));
		try {
			switch (position + 1) {
			case 1:
				controller.setName(waypoint, input);
				break;
			case 2:
				controller.setHeadedFor(waypoint, UUID.fromString(input));
				break;
			case 3:
				controller.setManeuver(waypoint, Maneuver.NONE);
				break;
			case 4:
				controller.setForesail(waypoint, ForeSail.NONE);
				break;
			case 5:
				controller.setMainsail(waypoint, MainSail.NONE);
				break;
			default:
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}