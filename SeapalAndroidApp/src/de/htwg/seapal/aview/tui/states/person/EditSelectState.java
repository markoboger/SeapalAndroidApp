package de.htwg.seapal.aview.tui.states.person;

import java.util.UUID;

import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.PersonActivity;

public class EditSelectState implements TuiState {

	private final UUID person;
	private static final String[] editPerson = new String[] { "First Name",
			"Last Name", "Date of Birth", "Date of Registration", "Age",
			"Owner", "Insurance", "CallSign", "Type", "Constructor", "Length",
			"Width", "Draft", "MastHeight", "Rigging", "YearOfConstruction",
			"Motor", "TankSize", "WasteWaterTankSize", "FreshWaterTankSize",
			"MainSailSize", "GenuaSize", "SpiSize" };

	public EditSelectState(UUID person) {
		this.person = person;
	}

	@Override
	public String buildString(StateContext context) {
		StringBuilder sb = new StringBuilder();
		sb.append("q - quit\n");
		sb.append("<x> - edit Attribute\n");
		sb.append("------------------------------------------------\n");
		int i = 1;
		for (String element : editPerson) {
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
				context.setState(new ShowState(person));
			else
				Toast.makeText((PersonActivity) context, "Unkown Option",
						Toast.LENGTH_SHORT).show();
            return;
		}
		if (number >= 0 && number < editPerson.length)
			context.setState(new EditState());
		else
			Toast.makeText((PersonActivity) context, "Unkown Option",
					Toast.LENGTH_SHORT).show();
    }

	public static String getEditPersonElement(int position) {
		return editPerson[position];
	}

}
