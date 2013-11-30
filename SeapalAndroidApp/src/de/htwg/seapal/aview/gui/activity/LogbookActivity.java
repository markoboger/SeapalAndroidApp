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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.boatmenu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        LogbookSlideFragment logbookSlideFragment =  (LogbookSlideFragment) getSupportFragmentManager().findFragmentById(R.id.logbook_slide_fragment);
        switch (item.getItemId()) {
            case R.id.boatmenu_new:
                logbookSlideFragment.onNewBoat();
                break;
            case R.id.boatmenu_delete:
                logbookSlideFragment.onDeleteBoat();
                break;
            case R.id.boatmenu_save:
                logbookSlideFragment.onSaveBoat();
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }
}
