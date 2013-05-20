package de.htwg.seapal.aview.gui.activity;

import java.util.UUID;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.fragment.FragmentDetail;
import de.htwg.seapal.aview.gui.fragment.FragmentList;
import de.htwg.seapal.controller.impl.BoatController;
import de.htwg.seapal.database.impl.TouchDBBoatDatabase;
import de.htwg.seapal.utils.logging.Logger;
import de.htwg.seapal.utils.observer.Event;
import de.htwg.seapal.utils.observer.IObserver;

public class BoatActivity extends Activity implements IObserver,
		FragmentList.ListSelectedCallback {

	private BoatController controller;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout);

		controller = new BoatController(
				TouchDBBoatDatabase.getInstance(getApplicationContext()),
				new Logger());
//		controller.addObserver(this);
		

		if (savedInstanceState == null) {
			FragmentList fragmentListe = new FragmentList();
			fragmentListe.setController(controller);
			FragmentManager fm = getFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();
			transaction.add(R.id.frame_links, fragmentListe, FragmentList.TAG);

			View v = this.findViewById(R.id.linearLayout_large_land);

			if (v != null) { // tablet and landscape -> FragmentDetail
				FragmentDetail fragmentDetail = new FragmentDetail();
				fragmentDetail.setController(controller);
				transaction.add(R.id.frame_rechts, fragmentDetail,
						FragmentDetail.TAG);
			}
			transaction.commit();

		}

	}

	@Override
	public void selected(UUID boat) {
		// Clickes an ListItem

		View v = this.findViewById(R.id.linearLayout_large_land);

		if (v != null) { // Tablet-Landscape scenario
			FragmentDetail fragment = (FragmentDetail) getFragmentManager()
					.findFragmentByTag(FragmentDetail.TAG);
			fragment.refresh(boat);
		} else {
			// Smartphone
			FragmentDetail fragment = new FragmentDetail();
			fragment.setController(controller);
			fragment.setBoat(boat);
			
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();
			transaction.replace(R.id.frame_links, fragment);
			transaction.addToBackStack(null);
			transaction.commit();
		}
	}

	@Override
	public void update(Event event) {
		// TODO Auto-generated method stub

	}
	

}
