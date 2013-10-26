package de.htwg.seapal.aview.tui.states.route;

import java.util.UUID;

import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.RouteActivity;
import de.htwg.seapal.controller.IRouteController;

public class NewState implements TuiState {

	@Override
	public String buildString(StateContext context) {
		return "q - quit \n\n Enter RouteName";
	}

	@Override
	public void process(StateContext context, String input) {
		IRouteController controller = ((RouteActivity) context).getController();
		if (input.equals("q")) {
			context.setState(new StartState());
            return;
		}
		UUID route = controller.newRoute();
		controller.setName(route, input);
		context.setState(new ShowState(route));
    }

}
