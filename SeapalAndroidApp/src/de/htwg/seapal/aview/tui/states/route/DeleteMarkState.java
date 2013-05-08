package de.htwg.seapal.aview.tui.states.route;

import java.util.List;
import java.util.UUID;

import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.RouteActivity;
import de.htwg.seapal.controller.IMarkController;

public class DeleteMarkState implements TuiState {

	private List<UUID> marks;
	private UUID route;
	private IMarkController markController;

	public DeleteMarkState(UUID route, List<UUID> marks, IMarkController markController) {
		this.route = route;
		this.marks = marks;
		this.markController = markController;
	}

	@Override
	public String buildString(StateContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append("q - quit\n");
		sb.append("<x> - delete Mark from Route\n");
		sb.append("-------------------------------------\n");
		int i = 1;
		for (UUID uuid : marks) {
			sb.append(i++).append(")\t").append(markController.getName(uuid))
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
			if (input.equals("q"))
				context.setState(new ShowState(route));
			else
				Toast.makeText((RouteActivity) context, "Unkown Option",
						Toast.LENGTH_SHORT).show();
			return false;
		}
		if (number >= 0 && number < marks.size()) {
			markController.deleteMark(marks.get(number));
			context.setState(new ShowState(route));			
		}
		else
			Toast.makeText((RouteActivity) context, "Unkown Option",
					Toast.LENGTH_SHORT).show();
		
		return true;
	}

}
