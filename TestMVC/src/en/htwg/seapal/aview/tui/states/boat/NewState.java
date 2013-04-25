package en.htwg.seapal.aview.tui.states.boat;

import java.util.UUID;

import en.htwg.seapal.aview.tui.StateContext;
import en.htwg.seapal.aview.tui.TuiState;
import en.htwg.seapal.aview.tui.activity.BoatActivity;

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
		UUID boat = ((BoatActivity) context).getController().newBoat();
		((BoatActivity) context).getController().setBoatName(boat, input);
		context.setState(new ShowState(boat));
		return true;
	}

}
