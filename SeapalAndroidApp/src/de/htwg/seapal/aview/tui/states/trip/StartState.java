package de.htwg.seapal.aview.tui.states.trip;

import java.util.List;
import java.util.UUID;

import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.TripActivity;
import de.htwg.seapal.controller.ITripController;

public class StartState implements TuiState {

    private List<UUID> trips;

	@Override
	public String buildString(StateContext context) {
		TripActivity activity = (TripActivity) context;
		ITripController controller = activity.getController();
        UUID boat = activity.getBoat();
		StringBuilder sb = new StringBuilder();
		sb.append("q \t- Quit\n");
		sb.append("r \t- Refresh\n");
		sb.append("n \t- New Trip\n");
		sb.append("<X> \t- Show Trip\n");
		sb.append("---------------------------------------\n");
		trips = controller.getTrips(boat);
		int i = 1;
		for (UUID uuid : trips) {
			sb.append(i++).append(")\t").append(controller.getName(uuid))
					.append("\n");
		}
		return sb.toString();
	}

	@Override
	public void process(StateContext context, String input) {
		TripActivity activity = (TripActivity) context;
		switch (input.charAt(0)) {
		case 'q':
			activity.finish();
			break;
		case 'n':
			context.setState(new NewState());
			break;
		case 'r':
			break;
		default:
			Integer number;
			try {
				number = Integer.valueOf(input) - 1;
			} catch (NumberFormatException e) {
				Toast.makeText(activity, "Unkown Option", Toast.LENGTH_SHORT)
						.show();
                return;
			}
			if (number < trips.size() && number >= 0)
				context.setState(new ShowState(trips.get(number)));
			else
				Toast.makeText((TripActivity) context, "Unkown Option",
						Toast.LENGTH_SHORT).show();
		}
    }

}
