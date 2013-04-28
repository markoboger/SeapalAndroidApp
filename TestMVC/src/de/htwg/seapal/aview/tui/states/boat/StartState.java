package de.htwg.seapal.aview.tui.states.boat;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.BoatActivity;
import de.htwg.seapal.controller.IBoatController;

public class StartState implements TuiState {

	private List<UUID> boats;

	@Override
	public String buildString(StateContext context) {
		IBoatController controller = ((BoatActivity) context).getController();
		boats = controller.getBoats();
		StringBuilder sb = new StringBuilder();
		sb.append("n \t- New Boat\n");
		sb.append("<X> \t- Show Boat\n");
		sb.append("---------------------------------------\n");
		int i = 1;
		for (UUID uuid : boats) {
			sb.append(i++).append(")\t").append(controller.getBoatName(uuid))
					.append("\n");
		}
		return sb.toString();
	}

	@Override
	public boolean process(StateContext context, String input) {
		Integer number;
		try {
			number = Integer.valueOf(input) - 1;
		} catch (NumberFormatException e) {
			if (input.equals("n"))
				context.setState(new NewState());
			return false;
		}
		if (number < boats.size() && number >= 0)
			context.setState(new ShowState(boats.get(number)));
		return false;
	}

}