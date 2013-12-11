package de.htwg.seapal.aview.gui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.fragment.BoatListFragment;
import de.htwg.seapal.aview.gui.fragment.BoatViewFragment;
import de.htwg.seapal.aview.gui.fragment.LogbookSlideFragment;

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
        LogbookSlideFragment logbookSlideFragment = (LogbookSlideFragment) getSupportFragmentManager().findFragmentById(R.id.logbook_slide_fragment);
        if(logbookSlideFragment != null) {
            logbookSlideFragment.updateBoatView(position, uuid);
        }
    }

}
