package de.htwg.seapal.aview.tui.states.recordTrip;

import java.util.List;
import java.util.UUID;

import android.location.LocationManager;
import android.widget.Toast;
import de.htwg.seapal.aview.tui.StateContext;
import de.htwg.seapal.aview.tui.TuiState;
import de.htwg.seapal.aview.tui.activity.TripRecordActivity;
import de.htwg.seapal.controller.IWaypointController;

public class StartState implements TuiState {

    @Override
	public String buildString(StateContext context) {
		TripRecordActivity activity = (TripRecordActivity) context;
        IWaypointController controller = activity.getController();
		UUID trip = activity.getTrip();
		StringBuilder sb = new StringBuilder();
		sb.append("q \t- Quit\n");
		sb.append("s \t- start Tracking\n");
		sb.append("e \t- end Tracking\n");
		sb.append("---------------------------------------\n");
		List<UUID> waypoints = controller.getWaypoints(trip);
		int i = 1;
		for (UUID waypoint : waypoints) {
			sb.append(i++).append(")\t").append(controller.getName(waypoint))
					.append(" \t ").append(controller.getLatitude(waypoint))
					.append(" - ").append(controller.getLongitude(waypoint))
					.append("\n");
		}
		return sb.toString();
	}

	@Override
	public void process(StateContext context, String input) {
		TripRecordActivity activity = (TripRecordActivity) context;
		switch (input.charAt(0)) {
		case 'q':
			activity.finish();
			break;
		case 's':
			activity.getLocationMgr().requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 5000, 0,
					activity.getTrackLocationListener());
			break;
		case 'e':
			activity.getLocationMgr().removeUpdates(
					activity.getTrackLocationListener());
			break;
		default:
			Toast.makeText(activity, "Unkown Option", Toast.LENGTH_SHORT)
					.show();
		}
    }
}
