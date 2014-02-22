package de.htwg.seapal.aview.gui.activity;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.inject.Inject;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.fragment.AccountFragment;
import de.htwg.seapal.aview.gui.fragment.CrewFragment;
import de.htwg.seapal.aview.gui.fragment.LogbookFragment;
import de.htwg.seapal.aview.listener.OnCreateOptionsMenuListener;
import de.htwg.seapal.aview.listener.TabListener;
import de.htwg.seapal.events.boat.BoatFavoredEvent;
import de.htwg.seapal.events.boat.CreateBoatEvent;
import de.htwg.seapal.events.boat.DeleteBoatEvent;
import de.htwg.seapal.events.boat.FavourBoatEvent;
import de.htwg.seapal.events.boat.SaveBoatEvent;
import de.htwg.seapal.events.crew.OnCrewAddEvent;
import de.htwg.seapal.events.session.LogOutEvent;
import de.htwg.seapal.events.session.LoginEvent;
import de.htwg.seapal.manager.SessionManager;
import roboguice.event.EventManager;
import roboguice.event.Observes;

/**
 * Created by jakub on 12/10/13.
 */
public class LogbookTabsActivity extends BaseDrawerActivity {

    public static final String LOGBOOK_PREFS = "logbook_prefs";
    public static final String LOGBOOK_BOAT_FAVOURED = "logbook_boat_favoured";

    private Menu mMenu;

    @Inject
    private SessionManager sessionManager;

    @Inject
    private EventManager eventManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logbook_fragment_tabs);


        final ActionBar ab = getActionBar();
        addTabs(ab);
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        removeRightDrawer();
    }

    public void onLogin(@Observes LoginEvent e) {
        final ActionBar ab = getActionBar();
        ab.removeAllTabs();
        addTabs(ab);

    }

    public void onLogOut(@Observes LogOutEvent e) {
        SharedPreferences s = getSharedPreferences(LOGBOOK_PREFS, 0);
        s.edit().clear().commit();

        recreate();
    }

    private void addTabs(ActionBar ab) {
        ab.addTab(ab.newTab().setText("Account").setTabListener(new TabListener<AccountFragment>(R.id.logbook_fragment_tabs, AccountFragment.class, this, new OnCreateOptionsMenuListener() {
            @Override
            public void onCreateMenu() {
                if (mMenu != null)
                    mMenu.clear();

            }
        })));

        if (sessionManager.isLoggedIn()) {
            ab.addTab(ab.newTab().setText("Crew").setTabListener(new TabListener<CrewFragment>(R.id.logbook_fragment_tabs, CrewFragment.class, this, new OnCreateOptionsMenuListener() {
                @Override
                public void onCreateMenu() {
                    if (mMenu != null) {
                        mMenu.clear();
                        getMenuInflater().inflate(R.menu.logbook_crew_menu, mMenu);

                    }


                }
            })));
            ab.addTab(ab.newTab().setText("Logbook").setTabListener(new TabListener<LogbookFragment>(R.id.logbook_fragment_tabs, LogbookFragment.class, this, new OnCreateOptionsMenuListener() {
                @Override
                public void onCreateMenu() {
                    if (mMenu != null) {
                        mMenu.clear();
                        getMenuInflater().inflate(R.menu.logbook_boat_menu, mMenu);

                    }
                }

            })));
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
                eventManager.fire(new CreateBoatEvent());
                break;
            case R.id.logbookmenu_delete:
                eventManager.fire(new DeleteBoatEvent());
                break;
            case R.id.logbookmenu_save:
                eventManager.fire(new SaveBoatEvent());
                break;
            case R.id.logbookmenu_favour:
                eventManager.fire(new FavourBoatEvent());
                break;
            case R.id.crew_add:
                eventManager.fire(new OnCrewAddEvent(this));
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    public void onBoatFavoured(@Observes BoatFavoredEvent event) {
        SharedPreferences s = getSharedPreferences(LOGBOOK_PREFS, 0);
        s.edit().putString(LOGBOOK_BOAT_FAVOURED, event.getUuid().toString()).commit();
    }
}
