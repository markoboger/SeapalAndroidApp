package de.htwg.seapal.aview.gui.activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.inject.Inject;

import java.util.UUID;

import de.htwg.seapal.Manager.SessionManager;
import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.fragment.AccountFragment;
import de.htwg.seapal.aview.gui.fragment.BoatListFragment;
import de.htwg.seapal.aview.gui.fragment.CrewFragment;
import de.htwg.seapal.aview.gui.fragment.LogbookFragment;
import de.htwg.seapal.aview.gui.fragment.LogbookSlideFragment;
import de.htwg.seapal.aview.listener.OnCreateOptionsMenuListener;
import de.htwg.seapal.aview.listener.TabListener;
import de.htwg.seapal.controller.IMainController;

/**
 * Created by jakub on 12/10/13.
 */
public class LogbookTabsActivity extends BaseDrawerActivity implements BoatListFragment.OnBoatNameSelectedListener, BoatListFragment.OnBoatFavouredListener {


    public static final String LOGBOOK_PREFS = "logbook_prefs";
    public static final String LOGBOOK_BOAT_FAVOURED = "logbook_boat_favoured";
    private int mPosition;
    @Inject
    private LogbookFragment mLogbookFragment;
    private boolean mLogbookFragmentCreated = false;
    private boolean mCrewFragmentCreated = false;
    @Inject
    private CrewFragment mCrewFragment;
    private Menu mMenu;

    @Inject
    private SessionManager sessionManager;
    @Inject
    private IMainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logbook_fragment_tabs);

        sessionManager.addListener(new SessionManager.OnLogOutListener(){

            @Override
            public void onLogout() {
                SharedPreferences s = getSharedPreferences(LOGBOOK_PREFS, 0);
                s.edit().clear().commit();

            }
        });


        final ActionBar ab = getActionBar();
        addTabs(ab);
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        sessionManager.addListener(new SessionManager.OnLoginListener() {
            @Override
            public void onLogin() {
                ab.removeAllTabs();
                addTabs(ab);

            }
        });

        sessionManager.addListener(new SessionManager.OnLogOutListener() {
            @Override
            public void onLogout() {
                ab.removeAllTabs();
                addTabs(ab);

            }
        });


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
        LogbookSlideFragment logbookSlideFragment = (LogbookSlideFragment) getSupportFragmentManager().findFragmentById(R.id.logbook_slide_fragment);
        switch (item.getItemId()) {
            case R.id.logbookmenu_new:
                logbookSlideFragment.onNewBoat();
                break;
            case R.id.logbookmenu_delete:
                logbookSlideFragment.onDeleteBoat();
                break;
            case R.id.logbookmenu_save:
                logbookSlideFragment.onSaveBoat();
                break;
            case R.id.logbookmenu_favour:
                logbookSlideFragment.onFavourBoat();
                break;
            case R.id.crew_add:
                final View view = getLayoutInflater().inflate(R.layout.crew_add_dialog, null);

                new AlertDialog.Builder(this)
                        .setView(view)
                        .setTitle(R.string.crew_add_title_text)
                        .setPositiveButton(R.string.crew_add_text, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText e = null;
                                if (view != null) {
                                    e = (EditText) view.findViewById(R.id.text);
                                    if (e != null) {
                                        mainController.addFriend(sessionManager.getSession(), e.getText().toString());
                                    }
                                }
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create().show();

                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onBoatFavoured(UUID uuid) {
        SharedPreferences s = getSharedPreferences(LOGBOOK_PREFS, 0);
        s.edit().putString(LOGBOOK_BOAT_FAVOURED, uuid.toString()).commit();
    }
}
