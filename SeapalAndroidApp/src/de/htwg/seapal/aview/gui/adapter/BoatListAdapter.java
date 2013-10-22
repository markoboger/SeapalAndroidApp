package de.htwg.seapal.aview.gui.adapter;

import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.activity.TripListActivity;
import de.htwg.seapal.controller.impl.BoatController;

public class BoatListAdapter extends ArrayAdapter<UUID> {

	private final List<UUID> boats;
	private final BoatController controller;
	private final Activity activity;

	public BoatListAdapter(Context context, List<UUID> items,
                           BoatController controller) {
		super(context, R.layout.boatlist, items);
		this.activity = (Activity) context;
		this.boats = items;
		this.controller = controller;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.boatlist, null);
		}

		final UUID boat = boats.get(position);

		if (boat != null) {
			TextView tt0 = (TextView) v.findViewById(R.id.boatname);
			TextView tt1 = (TextView) v.findViewById(R.id.boattype);
			TextView tt2 = (TextView) v.findViewById(R.id.constructor);
			TextView tt3 = (TextView) v.findViewById(R.id.length);
			TextView tt4 = (TextView) v.findViewById(R.id.owner);
			Button tripButton = (Button) v.findViewById(R.id.showTripsButton);

			if (tt0 != null)
				tt0.setText(controller.getBoatName(boat));

			if (tt1 != null)
				tt1.setText(controller.getType(boat));

			if (tt2 != null)
				tt2.setText(controller.getConstructor(boat));

			if (tt3 != null)
				tt3.setText(Double.toString(controller.getLength(boat)));

			if (tt4 != null) {
				if (controller.getOwner(boat) == null)
					tt4.setText("-");
				else
					tt4.setText(controller.getOwner(boat).toString());
			}

			if (tripButton != null) {
				tripButton.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						Intent intent = new Intent(activity, TripListActivity.class);
						intent.putExtra("boat", boat.toString());
						activity.startActivity(intent);

					}
				});
			}

		}

		return v;

	}
}
