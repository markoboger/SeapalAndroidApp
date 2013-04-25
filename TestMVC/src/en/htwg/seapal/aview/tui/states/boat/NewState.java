package en.htwg.seapal.aview.tui.states.boat;

import java.util.UUID;

import en.htwg.seapal.aview.tui.StateContext;
import en.htwg.seapal.aview.tui.TuiState;
import en.htwg.seapal.aview.tui.activity.BoatActivity;

public class NewState implements TuiState {

	@Override
	public String buildString(StateContext context) {
		String boatname = ((BoatActivity) context).userInput("Enter BoatName:");
		if (boatname == null)
			((StateContext) context).setState(new StartState());
		else
			((StateContext) context).setState(new ShowState());
		UUID boat = ((BoatActivity) context).getController().newBoat();
		((BoatActivity) context).getController().setBoatName(boat, boatname);
		return "";
	}

	@Override
	public boolean process(StateContext context, String input) {
		return false;
	}

}
