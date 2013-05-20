package de.htwg.seapal.aview.gui.fragment;

import java.util.UUID;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import de.htwg.seapal.R;
import de.htwg.seapal.controller.impl.BoatController;

public class FragmentDetail extends Fragment {

	public static String TAG = "FragmentDetail";
	private UUID boat;
	private BoatController controller;

	public FragmentDetail() {
	}

	public void setBoat(UUID boat) {
		this.boat = boat;

	}

	@Override
	public void onResume() {
		if (boat != null) {
			refresh(boat);
		}
		super.onResume();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return inflater.inflate(R.layout.boatdetails, container,
				false);
	}

	public void refresh(UUID boat) {
		this.boat = boat;

		Activity activity = getActivity();
		EditText containerView = (EditText) (activity
				.findViewById(R.id.editBoatName));
		containerView.setText(controller.getBoatName(boat));

	}

	public void setController(BoatController controller) {
		this.controller = controller;
	}
}
