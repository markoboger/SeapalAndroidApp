package de.htwg.seapal.aview.tui.states.boat;

import java.util.UUID;

import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.BoatActivity;
import de.htwg.seapal.controller.IBoatController;

public class EditState implements TuiState {

	private int position;
	private UUID boat;

	public EditState(int position, UUID boat) {
		this.position = position;
		this.boat = boat;
	}

	@Override
	public String buildString(StateContext context) {
		return "q - quit \n\n Enter "
				+ EditSelectState.getEditBoatElement(position);
	}

	@Override
	public boolean process(StateContext context, String input) {
		if (input.equals("q")) {
			context.setState(new EditSelectState(boat));
			return false;
		}
		IBoatController controller = ((BoatActivity) context).getController();
		context.setState(new ShowState(boat));
		try {
			switch (position + 1) {
			case 1:
				controller.setBoatName(boat, input);
				break;
			case 2:
				controller.setRegisterNr(boat, input);
				break;
			case 3:
				controller.setSailSign(boat, input);
				break;
			case 4:
				controller.setHomePort(boat, input);
				break;
			case 5:
				controller.setYachtclub(boat, input);
				break;
			case 6:
				// controller.setOwner(boat, UUID.fromString(input));
				break;
			case 7:
				controller.setInsurance(boat, input);
				break;
			case 8:
				controller.setCallSign(boat, input);
				break;
			case 9:
				controller.setType(boat, input);
				break;
			case 10:
				controller.setConstructor(boat, input);
				break;
			case 11:
				controller.setLength(boat, Integer.valueOf(input));
				break;
			case 12:
				controller.setWidth(boat, Integer.valueOf(input));
				break;
			case 13:
				controller.setDraft(boat, Integer.valueOf(input));
				break;
			case 14:
				controller.setMastHeight(boat, Integer.valueOf(input));
				break;
			case 15:
				controller.setRigging(boat, input);
				break;
			case 16:
				controller.setYearOfConstruction(boat, Integer.valueOf(input));
				break;
			case 17:
				controller.setMotor(boat, input);
				break;
			case 18:
				controller.setTankSize(boat, Integer.valueOf(input));
				break;
			case 19:
				controller.setWasteWaterTankSize(boat, Integer.valueOf(input));
				break;
			case 20:
				controller.setFreshWaterTankSize(boat, Integer.valueOf(input));
				break;
			case 21:
				controller.setMainSailSize(boat, Integer.valueOf(input));
				break;
			case 22:
				controller.setGenuaSize(boat, Integer.valueOf(input));
				break;
			case 23:
				controller.setSpiSize(boat, Integer.valueOf(input));
				break;
			default:
				Toast.makeText((BoatActivity) context, "Unkown Option",
						Toast.LENGTH_SHORT).show();
				return false;
			}

		} catch (NumberFormatException e) {
			Toast.makeText((BoatActivity) context, "Unkown Option",
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}

}
