package de.htwg.seapal.aview.tui.states.route;

import java.util.UUID;

import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.RouteActivity;

public class EditState implements TuiState {

	private final UUID route;

	public EditState(UUID route) {
		this.route = route;
	}

	@Override
	public String buildString(StateContext context) {
		return "q - quit \n\n Enter new RouteName";
	}

	@Override
	public void process(StateContext context, String input) {
		if (input.equals("q")) {
			context.setState(new ShowState(route));
            return;
		}
		((RouteActivity) context).getController().setName(route, input);
		context.setState(new ShowState(route));
    }

}
