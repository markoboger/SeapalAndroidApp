package en.htwg.seapal.aview.tui.states.trip;

import java.util.UUID;

import en.htwg.seapal.aview.tui.StateContext;
import en.htwg.seapal.aview.tui.TuiState;
import en.htwg.seapal.aview.tui.activity.TripActivity;

public class StartState implements TuiState {

	private UUID boat;
	
	@Override
	public String buildString(StateContext context) {
		boat = ((TripActivity) context).getBoat();
		StringBuilder sb = new StringBuilder();
		sb.append("");
		return "";
	}

	@Override
	public boolean process(StateContext context, String input) {
		// TODO Auto-generated method stub
		return false;
	}

}
