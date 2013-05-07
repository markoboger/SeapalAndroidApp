package de.htwg.seapal.aview.tui.states.mark;

import java.util.UUID;

import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.MarkActivity;

public class ShowState implements TuiState {

	private UUID mark;

	public ShowState(UUID mark) {
		this.mark = mark;
	}

	@Override
	public String buildString(StateContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append("q - quit\n");
		sb.append("d - delete mark\n");
		sb.append("e - edit\n");
		sb.append("--------------------------------------------------\n");
		sb.append(((MarkActivity) context).getController().getString(mark));
		return sb.toString();
	}

	@Override
	public boolean process(StateContext context, String input) {
		MarkActivity activity = (MarkActivity) context;
		switch (input.charAt(0)) {
		case 'q':
			context.setState(new StartState());
			break;
		case 'd':
			context.setState(new StartState());
			activity.getController().deleteMark(mark);
			break;
		case 'e':
			context.setState(new EditSelectState(mark));
			break;
		default:
			Toast.makeText(activity, "Unkown Option", Toast.LENGTH_SHORT)
					.show();
			return false;
		}

		return true;
	}

}
