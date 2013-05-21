package de.htwg.seapal.aview.gui.fragment;

import java.util.List;
import java.util.UUID;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.adapter.BoatListAdapter;
import de.htwg.seapal.controller.impl.BoatController;

public class FragmentList extends ListFragment {

	public static String TAG = "FragmentList";
	private List<UUID> boatList;
	private BoatController controller;
	private BoatListAdapter adapter = null;

	public FragmentList() {

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState == null)
			boatList = controller.getBoats();

	}

	@Override
	public void onResume() {
		super.onResume();
		if(boatList == null)
			boatList = controller.getBoats();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		setListAdapter(null);
		if (adapter == null)
			adapter = new BoatListAdapter(getActivity(), R.layout.boatlist,
					boatList, controller);
		
		LayoutInflater inflater = LayoutInflater.from(getActivity());
        View header = inflater.inflate(R.layout.boatlistheadershort, null, false);

		this.getListView().addHeaderView(header, null, false);
		this.setListAdapter(adapter);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		getListView().setFocusable(true);
		getListView().setSelection(4);
	}
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// inform Activity

		UUID boat = (UUID) l.getAdapter().getItem(position);
		ListSelectedCallback callback = (ListSelectedCallback) getActivity();
		callback.selected(boat);

	}

	// Callback for Container Activity
	public interface ListSelectedCallback {
		public void selected(UUID boat);
	}

	public void setController(BoatController controller) {
		this.controller = controller;
	}
	
}
