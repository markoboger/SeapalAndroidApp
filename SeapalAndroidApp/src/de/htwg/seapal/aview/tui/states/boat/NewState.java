package de.htwg.seapal.aview.tui.states.boat;

import java.util.UUID;

import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.BoatActivity;
import de.htwg.seapal.controller.IBoatController;

public class NewState implements TuiState {

	@Override
	public String buildString(StateContext context) {
		return "q - quit \n\n Enter BoatName";
	}

	@Override
	public void process(StateContext context, String input) {
		IBoatController controller = ((BoatActivity) context).getController();
		if (input.equals("q")) {
			context.setState(new StartState());
            return;
		}
		UUID boat = controller.newBoat();
		controller.setBoatName(boat, input);
		context.setState(new ShowState(boat));
    }

}
