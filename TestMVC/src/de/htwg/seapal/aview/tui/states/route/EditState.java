package de.htwg.seapal.aview.tui.states.route;

import java.util.UUID;

import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;

public class EditState implements TuiState {
	
	private UUID route;
	
	public EditState(UUID route) {
		this.route = route;
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
