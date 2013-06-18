package de.htwg.seapal.aview.gui.adapter;

import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.htwg.seapal.R;
import de.htwg.seapal.controller.impl.WaypointController;

public class WaypointListAdapter extends ArrayAdapter<UUID> {

	private List<UUID> waypoints;
	private WaypointController controller;
	private Activity activity;

	public WaypointListAdapter(Context context, int resource, List<UUID> items,
			WaypointController controller) {
		super(context, resource, items);
		this.activity = (Activity) context;
		this.controller = controller;
		this.waypoints = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.tripwaypointlistadapter, null);
		}

		UUID waypoint = waypoints.get(position);

		if (waypoint != null) {
			TextView tt0 = (TextView) v
					.findViewById(R.id.tripwaypointlistadapter_name);
			TextView tt2 = (TextView) v
					.findViewById(R.id.tripwaypointlistadapter_cog);
			TextView tt3 = (TextView) v
					.findViewById(R.id.tripwaypointlistadapter_sog);
			TextView tt4 = (TextView) v
					.findViewById(R.id.tripwaypointlistadapter_lat);
			TextView tt5 = (TextView) v
					.findViewById(R.id.tripwaypointlistadapter_long);

			if (tt0 != null)
				tt0.setText(controller.getName(waypoint));

			if (tt2 != null)
				tt2.setText(controller.getCOG(waypoint) + "");

			if (tt3 != null)
				tt3.setText(controller.getSOG(waypoint) + "");

			if (tt4 != null) {
				Location location = new Location("");
				location.setLatitude(controller.getLatitude(waypoint));
				tt4.setText(Double.toString(location.getLatitude()));
			}

			if (tt5 != null) {
				Location location = new Location("");
				location.setLongitude(controller.getLongitude((waypoint)));
				tt5.setText(Double.toString(location.getLongitude()));
			}

		}

		return v;
	}

}
