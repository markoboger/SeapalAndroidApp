package de.htwg.seapal.aview.tui;

public interface TuiState {

	public String buildString(StateContext context);

	public void process(StateContext context, String input);

}