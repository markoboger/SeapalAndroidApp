package de.htwg.seapal.aview.gui.activity;

import java.util.UUID;

import roboguice.activity.RoboActivity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.inject.Inject;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.fragment.BoatDetailFragment;
import de.htwg.seapal.aview.gui.fragment.BoatListFragment;
import de.htwg.seapal.controller.impl.BoatController;
import de.htwg.seapal.utils.observer.Event;
import de.htwg.seapal.utils.observer.IObserver;

public class BoatActivity extends RoboActivity implements IObserver,
		BoatListFragment.ListSelectedCallback {

	@Inject
	private BoatController controller;
	private BoatListFragment fragmentListe;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.boat);

		// controller.addObserver(this);

		if (savedInstanceState == null) {
			fragmentListe = new BoatListFragment();
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();
			transaction.add(R.id.frame_list, fragmentListe,
					BoatListFragment.TAG);

			View v = this.findViewById(R.id.linearLayout_xlarge);

			if (v != null) { // tablet -> FragmentDetail
				BoatDetailFragment fragmentDetail = new BoatDetailFragment();
				transaction.add(R.id.frame_detail, fragmentDetail,
						BoatDetailFragment.TAG);
			}
			transaction.commit();

		}

	}

	@Override
	public void selected(UUID boat) {
		// Clickes an ListItem

		View v = this.findViewById(R.id.linearLayout_xlarge);

		if (v != null) { // Tablet scenario
			BoatDetailFragment fragment = (BoatDetailFragment) getFragmentManager()
					.findFragmentByTag(BoatDetailFragment.TAG);
			fragment.refresh(boat);
		} else {
			// Smartphone
			BoatDetailFragment fragment = new BoatDetailFragment();
			fragment.setBoat(boat);
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();
			transaction.replace(R.id.frame_list, fragment);
			transaction.addToBackStack(null);
			transaction.commit();
		}
	}

	@Override
	public void update(Event event) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actionbar, menu);
		
		return true;
	}
	
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		
		
		
		return super.onMenuItemSelected(featureId, item);
	}

}
