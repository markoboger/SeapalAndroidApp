package de.htwg.seapal.aview.gui.fragment;

import java.util.List;
import java.util.UUID;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.adapter.BoatListAdapter;
import de.htwg.seapal.controller.impl.BoatController;

public class FragmentList extends ListFragment {

	public static String TAG = "FragmentList";
	private List<UUID> boatList;
	private BoatController controller;

	
	public FragmentList() {
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(savedInstanceState == null)
			boatList = controller.getBoats();
		
		
		BoatListAdapter adapter = new BoatListAdapter(getActivity(), R.layout.boatlist, boatList, controller);
		
		this.setListAdapter(adapter);
	}

	@Override
	public void onStart() {
		super.onStart();
		getListView().setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
//		getListView().setSelection(0);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// inform Activity
//		getListView().setSelection(position);
		ListSelectedCallback callback = (ListSelectedCallback) getActivity();
		UUID boat = boatList.get(position);
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
