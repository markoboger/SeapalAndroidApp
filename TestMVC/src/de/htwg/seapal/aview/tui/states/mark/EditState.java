package de.htwg.seapal.aview.tui.states.mark;

import java.util.UUID;

import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.MarkActivity;
import de.htwg.seapal.controller.IMarkController;

public class EditState implements TuiState {

	private UUID mark;
	private int position;

	public EditState(int position, UUID mark) {
		this.mark = mark;
		this.position = position;
	}

	@Override
	public String buildString(StateContext context) {
		return "q - quit \n\n Enter "
				+ EditSelectState.getEditMarkElement(position);
	}

	@Override
	public boolean process(StateContext context, String input) {
		if (input.equals("q")) {
			context.setState(new EditSelectState(mark));
			return false;
		}
		IMarkController controller = ((MarkActivity) context).getController();
		context.setState(new ShowState(mark));
		try {
			switch (position + 1) {
			case 1:
				controller.setName(mark, input);
				break;
			case 2:
				controller.setLatitude(mark, Double.parseDouble(input));
				break;
			case 3:
				controller.setLongitude(mark, Double.parseDouble(input));
				break;
			case 4:
				controller.setNote(mark, input);
				break;
			default:
				Toast.makeText((MarkActivity) context, "Unkown Option",
						Toast.LENGTH_SHORT).show();
				return false;
			}

		} catch (NumberFormatException e) {
			Toast.makeText((MarkActivity) context, "Unkown Option",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

}
