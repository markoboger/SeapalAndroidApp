package de.htwg.seapal.aview.gui.activity;

import android.os.Bundle;

import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.fragment.BoatNamesFragment;
import de.htwg.seapal.aview.gui.fragment.BoatViewFragment;

/**
 * Created by jakub on 11/16/13.
 */
public class LogbookActivity extends BaseDrawerActivity implements BoatNamesFragment.OnBoatNameSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logbook_main_view);


    }

    @Override
    public void onBoatSelected(int position, UUID uuid) {
        BoatViewFragment boatViewFragment =  (BoatViewFragment) getFragmentManager().findFragmentById(R.id.boat_view_fragment);
        

        if(boatViewFragment != null) {
            boatViewFragment.updateBoatView(position, uuid);
        }

    }
}
