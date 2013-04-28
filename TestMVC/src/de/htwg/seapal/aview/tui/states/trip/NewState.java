package de.htwg.seapal.aview.tui.states.trip;

import java.util.UUID;

import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.TripActivity;
import de.htwg.seapal.controller.ITripController;

public class NewState implements TuiState {

	@Override
	public String buildString(StateContext context) {
		return "q - quit \n\n Enter BoatName";
	}

	@Override
	public boolean process(StateContext context, String input) {
		if (input.equals("q")) {
			context.setState(new StartState());
			return false;
		}
		ITripController controller = ((TripActivity) context).getController();
		UUID newTrip = controller.newTrip(((TripActivity) context).getBoat());
		controller.setName(newTrip, input);
		context.setState(new ShowState(newTrip));
		return true;
	}
}
