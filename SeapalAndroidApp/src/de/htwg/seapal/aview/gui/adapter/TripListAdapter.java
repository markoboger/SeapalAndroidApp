package de.htwg.seapal.aview.gui.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import de.htwg.seapal.R;
import de.htwg.seapal.model.IModel;
import de.htwg.seapal.model.ITrip;

public class TripListAdapter extends ArrayAdapter<IModel> {

	private final List<IModel> trips;


	public TripListAdapter(Context context, List<IModel> items) {
		super(context, R.layout.triplistadapter, items);
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

		ITrip trip = (ITrip) trips.get(position);

		if (trip != null) {
			TextView tt0 = (TextView) v
					.findViewById(R.id.triplistadapter_tripname);
			TextView tt1 = (TextView) v
					.findViewById(R.id.triplistadapter_destination);
			TextView tt2 = (TextView) v
					.findViewById(R.id.triplistadapter_startdate);
			TextView tt3 = (TextView) v.findViewById(R.id.triplistadapter_from);
			TextView tt4 = (TextView) v
					.findViewById(R.id.triplistadapter_enddate);
			TextView tt5 = (TextView) v
					.findViewById(R.id.triplistadapter_skipper);

			if (tt0 != null)
				tt0.setText(trip.getName());

			if (tt1 != null)
				tt1.setText(trip.getTo());

			if (tt2 != null)
				tt2.setText(DateFormat.format("yyyy/MM/dd hh:mm",
						trip.getStartDate()));

			if (tt3 != null)
				tt3.setText(trip.getTo());

			if (tt4 != null)
				tt4.setText(DateFormat.format("yyyy/MM/dd hh:mm",
						trip.getEndDate()));

			if (tt5 != null) {
				if (trip.getSkipper() == null)
					tt5.setText("-");
				else
					tt5.setText(trip.getSkipper().toString());
			}

		}

		return v;
	}

}
