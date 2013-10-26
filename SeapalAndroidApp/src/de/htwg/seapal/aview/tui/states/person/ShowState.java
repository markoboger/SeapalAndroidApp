package de.htwg.seapal.aview.tui.states.person;

import java.util.UUID;

import android.content.Intent;
import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.PersonActivity;
import de.htwg.seapal.aview.tui.activity.TripActivity;

public class ShowState implements TuiState {

	private final UUID person;

	public ShowState(UUID person) {
		this.person = person;
	}

	@Override
	public String buildString(StateContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append("q - quit\n");
		sb.append("d - delete person\n");
		sb.append("e - edit\n");
		sb.append("t - show trips\n");
		sb.append("--------------------------------------------------\n");
		sb.append(((PersonActivity) context).getController().getPersonString(
				person));
		return sb.toString();
	}

	@Override
	public void process(StateContext context, String input) {
		PersonActivity activity = (PersonActivity) context;
		switch (input.charAt(0)) {
		case 'q':
			context.setState(new StartState());
			break;
		case 'd':
			context.setState(new StartState());
			activity.getController().deletePerson(person);
			break;
		case 'e':
			context.setState(new EditSelectState(person));
			break;
		case 't':
			Intent intent = new Intent(activity, TripActivity.class);
			intent.putExtra("person", person.toString());
			activity.startActivity(intent);
			break;
		default:
			Toast.makeText(activity, "Unkown Option", Toast.LENGTH_SHORT)
					.show();
        }

    }

}
