package de.htwg.seapal.aview.gui.activity;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;
import de.htwg.seapal.R;

public class BaseDrawerActivity extends RoboActivity {

	@InjectView(R.id.drawer_menu_drawer_list)
	private ListView drawerListView;

	@InjectResource(R.array.drawer_list_array)
	private String[] drawerStrings;

	private DrawerLayout drawerLayout;

	@InjectView(R.id.drawer_menu_content_frame)
	private FrameLayout frameLayout;

	private ActionBarDrawerToggle drawerToggle;

	private int changeActivity;

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
		frameLayout = (FrameLayout) drawerLayout
				.findViewById(R.id.drawer_menu_content_frame);

		// Setting the content of layout your provided to the act_content frame
		getLayoutInflater().inflate(layoutResID, frameLayout, true);
		super.setContentView(drawerLayout);

		initializeDrawer();
	}

	protected void initializeDrawer() {
		drawerListView.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, drawerStrings));

		drawerListView.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu();
				switchActivity();
			}

			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);
	}

	// -------------------------------------------- ACTION - BAR ------------

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.actionbar, menu);
		return true;
	}

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the view
		boolean drawerOpen = drawerLayout.isDrawerOpen(drawerListView);
		for (int i = 0; i < menu.size(); i++)
			menu.getItem(i).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// The action bar home/up action should open or close the drawer.
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action buttons
		switch (item.getItemId()) {
		case R.id.menu_save:
			// create intent to perform web search for this planet
			Toast.makeText(this, "SAVE !!!!!", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menu_new:
			startActivity(new Intent(this,
					de.htwg.seapal.aview.gui.activity.BoatActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// -------------------------------------------- DRAWER ------------------

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
		changeActivity = position;
		Toast.makeText(this, drawerStrings[position] + " !!!!!",
				Toast.LENGTH_SHORT).show();
		// Highlight the selected item, update the title, and close the drawer
		// drawerListView.setItemChecked(position, true);
		// setTitle(drawerStrings[position]);
		drawerLayout.closeDrawer(drawerListView);
	}

	public void switchActivity() {
		Class<? extends Activity> clazz;
		switch (changeActivity) {
		case 1:
			clazz = de.htwg.seapal.aview.gui.activity.BoatActivity.class;
			break;
		case 2:
			clazz = de.htwg.seapal.aview.gui.activity.MapActivity.class;
			break;
		default:
			changeActivity = -1;
			return;
		}
		if (!this.getClass().equals(clazz))
			startActivity(new Intent(this, clazz));
		changeActivity = -1;
	}

	@Override
	public void setTitle(CharSequence title) {
		// mTitle = title;
		getActionBar().setTitle(title);
	}

}
