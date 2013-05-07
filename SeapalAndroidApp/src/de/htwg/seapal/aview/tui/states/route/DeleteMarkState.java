package de.htwg.seapal.aview.tui.states.route;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;

public class DeleteMarkState implements TuiState {

	private List<UUID> marks;

	public DeleteMarkState(List<UUID> marks) {
		this.marks = marks;
	}

	@Override
	public String buildString(StateContext context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean process(StateContext context, String input) {
		// TODO Auto-generated method stub
		return false;
	}

}
