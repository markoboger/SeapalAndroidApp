package de.htwg.seapal.aview.tui.states.route;

import java.util.List;
import java.util.UUID;

import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.RouteActivity;
import de.htwg.seapal.controller.IRouteController;

public class StartState implements TuiState {

	private List<UUID> routes;

	@Override
	public String buildString(StateContext context) {
		IRouteController controller = ((RouteActivity) context).getController();
		routes = controller.getRoutes();
		StringBuilder sb = new StringBuilder();
		sb.append("q \t- Quit\n");
		sb.append("r \t- Refresh\n");
		sb.append("n \t- New Route\n");
		sb.append("<X> \t- Show Route\n");
		sb.append("---------------------------------------\n");
		int i = 1;
		for (UUID uuid : routes) {
			sb.append(i++).append(")\t").append(controller.getName(uuid))
					.append("\n");
		}
		return sb.toString();
	}

	@Override
	public boolean process(StateContext context, String input) {
		RouteActivity activity = (RouteActivity) context;
		switch (input.charAt(0)) {
		case 'q':
			activity.finish();
			break;
		case 'r':
			break;
		case 'n':
			context.setState(new NewState());
			break;
		default:
			Integer number;
			try {
				number = Integer.valueOf(input) - 1;
			} catch (NumberFormatException e) {
				Toast.makeText(activity, "Unkown Option", Toast.LENGTH_SHORT)
						.show();
				return false;
			}

			if (number < routes.size() && number >= 0)
				context.setState(new ShowState(routes.get(number)));
			else
				Toast.makeText(activity, "Unkown Option", Toast.LENGTH_SHORT)
						.show();

		}

		return false;
	}

}
