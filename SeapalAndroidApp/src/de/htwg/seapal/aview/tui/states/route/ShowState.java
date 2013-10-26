package de.htwg.seapal.aview.tui.states.route;

import java.util.UUID;

import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.RouteActivity;

public class ShowState implements TuiState {

	private final UUID route;

	public ShowState(UUID route) {
		this.route = route;
	}

	@Override
	public String buildString(StateContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append("q - quit\n");
		sb.append("d - delete route\n");
		sb.append("e - edit routename\n");
		sb.append("s - show marks\n");
		sb.append("--------------------------------------------------\n");
		sb.append(((RouteActivity) context).getController().getString(route));
		return sb.toString();
	}

	@Override
	public void process(StateContext context, String input) {
		RouteActivity activity = (RouteActivity) context;
		switch (input.charAt(0)) {
		case 'q':
			context.setState(new StartState());
			break;
		case 'd':
			context.setState(new StartState());
			activity.getController().deleteRoute(route);
			break;
		case 'e':
			context.setState(new EditState(route));
			break;
		case 's':
			context.setState(new ShowMarksState(route));
			break;
		default:
			Toast.makeText(activity, "Unkown Option", Toast.LENGTH_SHORT)
					.show();
        }

    }

}
