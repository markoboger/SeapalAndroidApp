package de.htwg.seapal.aview.tui.states.waypoint;

import java.util.UUID;

import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.WaypointActivity;

public class ShowState implements TuiState {
	private UUID waypoint;

	public ShowState(UUID waypoint) {
		this.waypoint = waypoint;
	}

	@Override
	public String buildString(StateContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append("q - quit\n");
		sb.append("e - edit waypoint\n");
		sb.append("--------------------------------------------------\n");
		sb.append(((WaypointActivity) context).getController().getString(
				waypoint));
		return sb.toString();
	}

	@Override
	public boolean process(StateContext context, String input) {
		switch (input.charAt(0)) {
		case 'q':
			context.setState(new StartState());
			break;
		case 'e':
			context.setState(new EditSelectState(waypoint));
			break;
		default:
			Toast.makeText((WaypointActivity) context, "Unkown Option",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
}
