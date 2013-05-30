package de.htwg.seapal.aview.gui.fragment;

import java.util.List;
import java.util.UUID;

import roboguice.RoboGuice;
import android.app.ListFragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.inject.Inject;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.adapter.BoatListAdapter;
import de.htwg.seapal.controller.impl.BoatController;

public class BoatListFragment extends ListFragment {

	public static String TAG = "FragmentList";
	private List<UUID> boatList;
	private BoatListAdapter adapter = null;
	private View header;
	private ViewGroup mainView; 
	
	@Inject
	private BoatController controller;

	public BoatListFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RoboGuice.getInjector(getActivity()).injectMembers(this);
		if (savedInstanceState == null)
			boatList = controller.getBoats();

	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		
		mainView.removeView(header);
		this.onActivityCreated(null);
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
		
		//add Header
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		header = inflater.inflate(R.layout.boatlistheader, null);
		mainView = (ViewGroup) getActivity().findViewById(R.id.linearLayout_default);
		mainView.addView(header, 0);
		
		this.setListAdapter(adapter);
	}

	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// inform Activity
		mainView.removeView(header);
		UUID boat = (UUID) l.getAdapter().getItem(position);
		ListSelectedCallback callback = (ListSelectedCallback) getActivity();
		callback.selected(boat);

	}

	// Callback for Container Activity
	public interface ListSelectedCallback {
		public void selected(UUID boat);
	}

}
