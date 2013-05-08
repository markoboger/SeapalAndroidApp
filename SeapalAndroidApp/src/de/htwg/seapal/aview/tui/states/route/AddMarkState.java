package de.htwg.seapal.aview.tui.states.route;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.controller.IMarkController;

public class AddMarkState implements TuiState {

	private UUID route;
	private IMarkController markController;
	private List<UUID> marks;

	public AddMarkState(UUID route, IMarkController markController) {
		this.route = route;
		this.markController = markController;
	}

	@Override
	public String buildString(StateContext context) {
		
		
		//TODO: 
		
		
		marks = markController.getMarks();
		
		StringBuilder sb = new StringBuilder();
		sb.append("q - quit\n");
		sb.append("<x> - add Mark to Route\n");
		sb.append("----------------------------\n");
		return sb.toString();
	}

	@Override
	public boolean process(StateContext context, String input) {
		// TODO Auto-generated method stub
		return false;
	}

}
