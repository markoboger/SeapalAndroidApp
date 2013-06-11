package de.htwg.seapal.aview.gui.adapter;

import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import de.htwg.seapal.R;
import de.htwg.seapal.controller.impl.TripController;

public class TripListAdapter extends ArrayAdapter<UUID> {

	private List<UUID> trips;
	private TripController controller;
	private Activity activity;

	public TripListAdapter(Context context, int resource, List<UUID> items,
			TripController controller) {
		super(context, resource, items);
		this.activity = (Activity) context;
		this.controller = controller;
		this.trips = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.triplistadapter, null);
		}

		UUID trip = trips.get(position);

		if (trip != null) {
			TextView tt0 = (TextView) v
					.findViewById(R.id.triplistadapter_tripname);
			TextView tt1 = (TextView) v
					.findViewById(R.id.triplistadapter_destination);
			TextView tt2 = (TextView) v
					.findViewById(R.id.triplistadapter_startdate);
			TextView tt3 = (TextView) v.findViewById(R.id.triplistadapter_from);
			TextView tt4 = (TextView) v.findViewById(R.id.triplistadapter_enddate);
			TextView tt5 = (TextView) v.findViewById(R.id.triplistadapter_skipper);

			if (tt0 != null)
				tt0.setText(controller.getName(trip));

			if (tt1 != null)
				tt1.setText(controller.getEndLocation(trip));

			if (tt2 != null)
				tt2.setText(DateFormat.format("yyyy/MM/dd hh:mm",
						controller.getStartTime(trip)));

			if (tt3 != null)
				tt3.setText(controller.getStartLocation(trip));
			
			if(tt4 != null)
				tt4.setText(DateFormat.format("yyyy/MM/dd hh:mm",
						controller.getEndTime(trip)));
			
			if(tt5 != null) {
				if(controller.getSkipper(trip) == null)
					tt5.setText("-");
				else
					tt5.setText(controller.getSkipper(trip).toString());
			}

		}

		return v;
	}

}
