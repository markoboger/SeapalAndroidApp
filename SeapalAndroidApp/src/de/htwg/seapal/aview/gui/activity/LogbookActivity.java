package de.htwg.seapal.aview.gui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.fragment.BoatListFragment;
import de.htwg.seapal.aview.gui.fragment.BoatViewFragment;

/**
 * Created by jakub on 11/16/13.
 */
public class LogbookActivity extends BaseDrawerActivity implements BoatListFragment.OnBoatNameSelectedListener{

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.boatmenu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        BoatListFragment boatListFragment =  (BoatListFragment) getFragmentManager().findFragmentById(R.id.boat_list_fragment);
        switch (item.getItemId()) {
            case R.id.boatmenu_new:
                boatListFragment.onNewBoat();
                break;
            case R.id.boatmenu_delete:
                boatListFragment.onDeleteBoat();
                break;
            case R.id.boatmenu_save:
                boatListFragment.onSaveBoat();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }
}
