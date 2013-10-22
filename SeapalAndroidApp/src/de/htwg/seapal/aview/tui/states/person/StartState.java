package de.htwg.seapal.aview.tui.states.person;

import java.util.List;
import java.util.UUID;

import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.PersonActivity;
import de.htwg.seapal.controller.IPersonController;

public class StartState implements TuiState {

	private List<UUID> persons;

	@Override
	public String buildString(StateContext context) {
		IPersonController controller = ((PersonActivity) context)
				.getController();
		persons = controller.getPersons();
		StringBuilder sb = new StringBuilder();
		sb.append("q \t- Quit\n");
		sb.append("r \t- Refresh\n");
		sb.append("n \t- New Person\n");
		sb.append("<X> \t- Show Person\n");
		sb.append("---------------------------------------\n");
		int i = 1;
		for (UUID uuid : persons) {
			sb.append(i++).append(")\t")
					.append(controller.getPersonLastname(uuid)).append(", ")
					.append(controller.getPersonFirstname(uuid)).append("\n");
		}
		return sb.toString();
	}

	@Override
	public void process(StateContext context, String input) {
		PersonActivity activity = (PersonActivity) context;
		switch (input.charAt(0)) {
		case 'q':
			activity.finish();
			break;
		case 'n':
			context.setState(new NewState());
			break;
		case 'r':
			break;
		default:
			Integer number;
			try {
				number = Integer.valueOf(input) - 1;
			} catch (NumberFormatException e) {
				Toast.makeText(activity, "Unkown Option", Toast.LENGTH_SHORT)
						.show();
                return;
			}
			if (number < persons.size() && number >= 0)
				context.setState(new ShowState(persons.get(number)));
			else
				Toast.makeText(activity, "Unkown Option", Toast.LENGTH_SHORT)
						.show();
		}
    }
}
