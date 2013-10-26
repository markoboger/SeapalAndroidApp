package de.htwg.seapal.aview.tui.states.waypoint;

import java.util.UUID;

import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.WaypointActivity;
import de.htwg.seapal.controller.IWaypointController;
import de.htwg.seapal.model.IWaypoint.ForeSail;
import de.htwg.seapal.model.IWaypoint.MainSail;
import de.htwg.seapal.model.IWaypoint.Maneuver;

public class EditState implements TuiState {
	private final int position;
	private final UUID waypoint;

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
	public void process(StateContext context, String input) {
		if (input.equals("q")) {
			context.setState(new EditSelectState(waypoint));
            return;
		}
		IWaypointController controller = ((WaypointActivity) context)
				.getController();
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
				Toast.makeText((WaypointActivity) context, "Unkown Option",
						Toast.LENGTH_SHORT).show();
            }
		} catch (NumberFormatException e) {
			Toast.makeText((WaypointActivity) context, "Unkown Option",
					Toast.LENGTH_SHORT).show();
        }
    }
}