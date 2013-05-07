package de.htwg.seapal.aview.tui.states.trip;

import java.util.UUID;

import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.TripActivity;
import de.htwg.seapal.controller.ITripController;

public class EditState implements TuiState {
	private int position;
	private UUID trip;

	public EditState(int position, UUID trip) {
		this.position = position;
		this.trip = trip;
	}

	@Override
	public String buildString(StateContext context) {
		return "q - quit \n\n Enter "
				+ EditSelectState.getEditTripElement(position);
	}

	@Override
	public boolean process(StateContext context, String input) {
		if (input.equals("q")) {
			context.setState(new EditSelectState(trip));
			return false;
		}
		ITripController controller = ((TripActivity) context).getController();
		context.setState(new ShowState(trip));
		try {
			switch (position + 1) {
			case 1:
				controller.setName(trip, input);
				break;
			case 2:
				controller.setStartLocation(trip, input);
				break;
			case 3:
				controller.setEndLocation(trip, input);
				break;
			case 4:
				controller.setSkipper(trip, UUID.fromString(input));
				break;
			case 5:
				controller.addCrewMember(trip, input);
				break;
			case 6:
				controller.setMotor(trip, Integer.valueOf(input));
				break;
			case 7:
				controller.setFuel(trip, Integer.valueOf(input));
				break;
			case 8:
				controller.setNotes(trip, input);
				break;
			default:
				Toast.makeText((TripActivity) context, "Unkown Option",
						Toast.LENGTH_SHORT).show();
				return false;
			}
		} catch (NumberFormatException e) {
			Toast.makeText((TripActivity) context, "Unkown Option",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
}