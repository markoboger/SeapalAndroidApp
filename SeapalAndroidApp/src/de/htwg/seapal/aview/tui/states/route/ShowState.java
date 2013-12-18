package de.htwg.seapal.aview.tui.states.route;

import android.widget.Toast;

import java.util.UUID;

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
        return "q - quit\n" + "d - delete route\n" + "e - edit routename\n" + "s - show marks\n" + "--------------------------------------------------\n" + ((RouteActivity) context).getController().getString(route);
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
