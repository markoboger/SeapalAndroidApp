package de.htwg.seapal.aview.tui.states.person;

import java.util.UUID;

import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.PersonActivity;
import de.htwg.seapal.controller.IPersonController;

public class NewState implements TuiState {

	@Override
	public String buildString(StateContext context) {
		return "q - quit \n\n Enter PersonName";
	}

	@Override
	public boolean process(StateContext context, String input) {
		IPersonController controller = ((PersonActivity) context)
				.getController();
		if (input.equals("q")) {
			context.setState(new StartState());
			return false;
		}
		UUID person = controller.newPerson();
		controller.setPersonLastname(person, input);
		context.setState(new ShowState(person));
		return true;
	}

}
