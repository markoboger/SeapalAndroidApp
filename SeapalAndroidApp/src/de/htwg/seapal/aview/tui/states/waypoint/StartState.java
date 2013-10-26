package de.htwg.seapal.aview.tui.states.waypoint;

import java.util.List;
import java.util.UUID;

import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.WaypointActivity;
import de.htwg.seapal.controller.IWaypointController;

public class StartState implements TuiState {

    private List<UUID> waypoints;

	@Override
	public String buildString(StateContext context) {
		IWaypointController controller = ((WaypointActivity) context)
				.getController();
        UUID trip = ((WaypointActivity) context).getTrip();
		StringBuilder sb = new StringBuilder();
		sb.append("r \t- Refresh\n");
		sb.append("<X> \t- Show Waypoint\n");
		sb.append("---------------------------------------\n");
		waypoints = controller.getWaypoints(trip);
		int i = 1;
		for (UUID uuid : waypoints) {
			sb.append(i++).append(")\t").append(controller.getName(uuid))
					.append("\n");
		}
		return sb.toString();
	}

	@Override
	public void process(StateContext context, String input) {
		Integer number;
		try {
			number = Integer.valueOf(input) - 1;
		} catch (NumberFormatException e) {
			if (input.charAt(0) == 'r')
                return;
			Toast.makeText((WaypointActivity) context, "Unkown Option",
					Toast.LENGTH_SHORT).show();
            return;
		}
		if (number < waypoints.size() && number >= 0)
			context.setState(new ShowState(waypoints.get(number)));
		else
			Toast.makeText((WaypointActivity) context, "Unkown Option",
					Toast.LENGTH_SHORT).show();
    }
}
