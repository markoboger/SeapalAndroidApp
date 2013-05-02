package de.htwg.seapal.aview.tui.states.boat;

import java.util.UUID;

import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.BoatActivity;

public class EditSelectState implements TuiState {

	private UUID boat;
	private static String[] editBoat = new String[] { "BoatName", "RegisterNr",
			"SailSign", "HomePort", "Yachtclub", "Owner", "Insurance",
			"CallSign", "Type", "Constructor", "Length", "Width", "Draft",
			"MastHeight", "Rigging", "YearOfConstruction", "Motor", "TankSize",
			"WasteWaterTankSize", "FreshWaterTankSize", "MainSailSize",
			"GenuaSize", "SpiSize" };

	public EditSelectState(UUID boat) {
		this.boat = boat;
	}

	@Override
	public String buildString(StateContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append("q - quit\n");
		sb.append("<x> - edit Attribute\n");
		sb.append("------------------------------------------------\n");
		int i = 1;
		for (String element : editBoat) {
			sb.append(i + ") " + element + "\n");
			++i;
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
				context.setState(new ShowState(boat));
			else
				Toast.makeText((BoatActivity) context, "Unkown Option",
						Toast.LENGTH_SHORT).show();
			return false;
		}
		if (number >= 0 && number < editBoat.length)
			context.setState(new EditState(number, boat));
		else
			Toast.makeText((BoatActivity) context, "Unkown Option",
					Toast.LENGTH_SHORT).show();
		return true;
	}

	public static String getEditBoatElement(int position) {
		return editBoat[position];
	}

}
