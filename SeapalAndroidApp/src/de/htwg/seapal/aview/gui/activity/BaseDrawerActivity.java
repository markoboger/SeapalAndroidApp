package de.htwg.seapal.aview.gui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.couchbase.lite.router.URLStreamHandlerFactory;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import de.htwg.seapal.R;
import de.htwg.seapal.controller.IMainController;
import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

public class BaseDrawerActivity extends RoboFragmentActivity {



	@InjectView(R.id.drawer_menu_drawer_list_left)
	private ListView drawerListViewLeft;
	@InjectResource(R.array.drawer_list_array_left)
	private String[] drawerActivityListLeft;

	private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
	private int changeToActivity;
	private static final List<Class<? extends Activity>> classes;

    @Inject
    private IMainController mainController;


	// Add here all Activities in the drawerList (same order)
	static {
		classes = new ArrayList<Class<? extends Activity>>();
		classes.add(de.htwg.seapal.aview.gui.activity.MapActivity.class);
		classes.add(LogbookTabsActivity.class);

	}



	// -------------------------------------------- CREATION ------------

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void setContentView(final int layoutResID) {
		// base layout
		drawerLayout = (DrawerLayout) getLayoutInflater().inflate(
				R.layout.drawer_menu, null);
        FrameLayout frameLayout = (FrameLayout) drawerLayout
                .findViewById(R.id.drawer_menu_content_frame);

		// Setting the content of layout your provided to the act_content frame
		getLayoutInflater().inflate(layoutResID, frameLayout, true);
		super.setContentView(drawerLayout);

		initializeDrawer();
	}

	@Override
	protected void onResume() {
		super.onResume();
		int index = classes.indexOf(this.getClass());
		// if the activity is in the drawer set the title
		if (index != -1)
			drawerListViewLeft.setItemChecked(index, true);
	}

	// -------------------------------------------- ACTION - BAR ------------
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

	// -------------------------------------------- DRAWER ------------------
	private void initializeDrawer() {

        int[] drawerIconsRight = { android.R.drawable.ic_menu_mylocation,
                android.R.drawable.ic_dialog_map,
                android.R.drawable.ic_dialog_map,
                android.R.drawable.ic_menu_mapmode,
                android.R.drawable.ic_menu_mylocation,
                android.R.drawable.ic_menu_camera,
                android.R.drawable.ic_dialog_alert,
                android.R.drawable.ic_menu_delete };

		drawerListViewLeft.setAdapter( new ArrayAdapter(this,
                R.layout.drawer_list_item, drawerActivityListLeft));
		drawerListViewLeft.setOnItemClickListener(new DrawerItemClickListener());

        //getActionBar().hide();
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
				changeToActivity = -1; // no selection made
			}

			public void onDrawerClosed(View view) {
				invalidateOptionsMenu();
				switchActivity(); // switch the activity after closing the
									// Drawer, to avoid lacking
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);

		int index = classes.indexOf(this.getClass());
		// if the activity is in the drawer set the title
		if (index != -1) {
			drawerListViewLeft.setItemChecked(index, true);
			setTitle(drawerActivityListLeft[index]);
		} else
			setTitle("Seapal");
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
            if(parent.getId() == R.id.drawer_menu_drawer_list_left){
                changeToActivity = position;
                drawerListViewLeft.setItemChecked(classes.indexOf(this.getClass()), true);
                drawerLayout.closeDrawer(drawerListViewLeft);
            }
		}
	}

	void switchActivity() {
		if (changeToActivity != -1) // no item selected
			startActivity(new Intent(this, classes.get(changeToActivity))
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).addFlags(
							Intent.FLAG_ACTIVITY_SINGLE_TOP));
	}
}
