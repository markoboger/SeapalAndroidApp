package de.htwg.seapal.aview.gui.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.inject.Inject;

import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.fragment.AccountFragment;
import de.htwg.seapal.aview.gui.fragment.BoatListFragment;
import de.htwg.seapal.aview.gui.fragment.CrewFragment;
import de.htwg.seapal.aview.gui.fragment.LogbookFragment;
import de.htwg.seapal.aview.gui.fragment.LogbookSlideFragment;
import de.htwg.seapal.aview.listener.OnCreateOptionsMenuListener;
import de.htwg.seapal.aview.listener.TabListener;

/**
 * Created by jakub on 12/10/13.
 */
public class LogbookTabsActivity extends BaseDrawerActivity implements BoatListFragment.OnBoatNameSelectedListener {


    public static final int LOGBOOK_FRAGMENT = 1;
    public static final int CREW_FRAGMENT = 0;
    private int mPosition;
    @Inject
    private LogbookFragment mLogbookFragment;
    private boolean mLogbookFragmentCreated = false;
    private boolean mCrewFragmentCreated = false;
    @Inject
    private CrewFragment mCrewFragment;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logbook_fragment_tabs);


        ActionBar ab = getActionBar();

        ab.addTab(ab.newTab().setText("Account").setTabListener(new TabListener<AccountFragment>(R.id.logbook_fragment_tabs, "Account", AccountFragment.class, this, new OnCreateOptionsMenuListener() {
            @Override
            public void onCreateMenu() {
                if (mMenu != null)
                    mMenu.clear();

            }
        })));
        ab.addTab(ab.newTab().setText("Crew").setTabListener(new TabListener<CrewFragment>(R.id.logbook_fragment_tabs, "crew", CrewFragment.class, this, new OnCreateOptionsMenuListener() {
            @Override
            public void onCreateMenu() {
                if (mMenu != null) {
                    mMenu.clear();
                    getMenuInflater().inflate(R.menu.logbook_crew_menu, mMenu);

                }


            }
        })));
        ab.addTab(ab.newTab().setText("Logbook").setTabListener(new TabListener<LogbookFragment>(R.id.logbook_fragment_tabs, "logbook", LogbookFragment.class, this, new OnCreateOptionsMenuListener() {
            @Override
            public void onCreateMenu() {
                if (mMenu != null) {
                    mMenu.clear();
                    getMenuInflater().inflate(R.menu.logbook_boat_menu, mMenu);

                }
            }
        })));
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


    }

    @Override
    public void onBoatSelected(int position, UUID uuid) {
        LogbookSlideFragment logbookSlideFragment = (LogbookSlideFragment) getSupportFragmentManager().findFragmentById(R.id.logbook_slide_fragment);
        if (logbookSlideFragment != null) {
            logbookSlideFragment.updateBoatView(position, uuid);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logbookmenu_new:
                if (mPosition == LOGBOOK_FRAGMENT) {
                    LogbookSlideFragment logbookSlideFragment = (LogbookSlideFragment) getSupportFragmentManager().findFragmentById(R.id.logbook_slide_fragment);
                    logbookSlideFragment.onNewBoat();
                }
                break;
            case R.id.logbookmenu_delete:
                if (mPosition == LOGBOOK_FRAGMENT) {
                    LogbookSlideFragment logbookSlideFragment = (LogbookSlideFragment) getSupportFragmentManager().findFragmentById(R.id.logbook_slide_fragment);
                    logbookSlideFragment.onDeleteBoat();
                }
                break;
            case R.id.logbookmenu_save:
                if (mPosition == LOGBOOK_FRAGMENT) {
                    LogbookSlideFragment logbookSlideFragment = (LogbookSlideFragment) getSupportFragmentManager().findFragmentById(R.id.logbook_slide_fragment);
                    logbookSlideFragment.onSaveBoat();
                }
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }
}
