package de.htwg.seapal.aview.tui.states.mark;

import java.util.UUID;

import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.MarkActivity;

public class EditSelectState implements TuiState {

	private final UUID mark;
	private static final String[] editMark = new String[] { "Name", "Latitude",
			"Longitude", "Notes" };

	public EditSelectState(UUID mark) {
		this.mark = mark;
	}

	@Override
	public String buildString(StateContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append("q - quit\n");
		sb.append("<x> - edit Attribute\n");
		sb.append("------------------------------------------------\n");
		int i = 1;
		for (String element : editMark) {
			sb.append(i).append(") ").append(element).append("\n");
			++i;
		}
		return sb.toString();
	}

	@Override
	public void process(StateContext context, String input) {
		Integer number;
		try {
			number = Integer.valueOf(input) - 1;
		} catch (NumberFormatException e) {
			if (input.equals("q"))
				context.setState(new ShowState(mark));
			else
				Toast.makeText((MarkActivity) context, "Unkown Option",
						Toast.LENGTH_SHORT).show();
            return;
		}
		if (number >= 0 && number < editMark.length)
			context.setState(new EditState(number, mark));
		else
			Toast.makeText((MarkActivity) context, "Unkown Option",
					Toast.LENGTH_SHORT).show();
    }

	public static String getEditMarkElement(int position) {
		return editMark[position];
	}

}
