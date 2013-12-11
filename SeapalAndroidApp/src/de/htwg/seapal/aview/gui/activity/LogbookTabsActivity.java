package de.htwg.seapal.aview.gui.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.inject.Inject;

import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.fragment.BoatListFragment;
import de.htwg.seapal.aview.gui.fragment.CrewFragment;
import de.htwg.seapal.aview.gui.fragment.LogbookFragment;
import de.htwg.seapal.aview.gui.fragment.LogbookSlideFragment;

/**
 * Created by jakub on 12/10/13.
 */
public class LogbookTabsActivity extends BaseDrawerActivity implements ActionBar.TabListener, BoatListFragment.OnBoatNameSelectedListener{


    public static final int LOGBOOK_FRAGMENT = 1;
    public static final int CREW_FRAGMENT = 0;

    private int mPosition;
    @Inject
    private  LogbookFragment mLogbookFragment;
    private boolean mLogbookFragmentCreated = false;
    private boolean mCrewFragmentCreated = false;

    @Inject
    private CrewFragment mCrewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logbook_fragment_tabs);


        ActionBar ab = getActionBar();

        ab.addTab(ab.newTab().setText("Crew").setTabListener(this));
        ab.addTab(ab.newTab().setText("Logbook").setTabListener(this));
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


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
        getMenuInflater().inflate(R.menu.logbook_menu, menu);
        return true;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        switch (tab.getPosition()) {
            case CREW_FRAGMENT:
                ft.remove(mCrewFragment);
                ft.replace(R.id.logbook_fragment_tabs, mCrewFragment);
                mPosition = CREW_FRAGMENT;
                break;
            case LOGBOOK_FRAGMENT:
                ft.remove(mLogbookFragment);
                ft.replace(R.id.logbook_fragment_tabs, mLogbookFragment);
                mPosition = LOGBOOK_FRAGMENT;

                break;

        }

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        switch (tab.getPosition()) {
            case CREW_FRAGMENT:
                ft.hide(mCrewFragment);
                break;
            case LOGBOOK_FRAGMENT:
                ft.hide(mLogbookFragment);
                break;

        }

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        switch (tab.getPosition()) {
            case CREW_FRAGMENT:
                ft.show(mCrewFragment);
                mPosition = CREW_FRAGMENT;
                break;
            case LOGBOOK_FRAGMENT:
                ft.show(mLogbookFragment);
                mPosition = LOGBOOK_FRAGMENT;
                break;

        }

    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logbookmenu_new:
                if (mPosition == LOGBOOK_FRAGMENT) {
                    LogbookSlideFragment logbookSlideFragment =  (LogbookSlideFragment) getSupportFragmentManager().findFragmentById(R.id.logbook_slide_fragment);
                    logbookSlideFragment.onNewBoat();
                }
                break;
            case R.id.logbookmenu_delete:
                if (mPosition == LOGBOOK_FRAGMENT) {
                    LogbookSlideFragment logbookSlideFragment =  (LogbookSlideFragment) getSupportFragmentManager().findFragmentById(R.id.logbook_slide_fragment);
                    logbookSlideFragment.onDeleteBoat();
                }
                break;
            case R.id.logbookmenu_save:
                if (mPosition == LOGBOOK_FRAGMENT) {
                    LogbookSlideFragment logbookSlideFragment =  (LogbookSlideFragment) getSupportFragmentManager().findFragmentById(R.id.logbook_slide_fragment);
                    logbookSlideFragment.onSaveBoat();
                }
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }
}
