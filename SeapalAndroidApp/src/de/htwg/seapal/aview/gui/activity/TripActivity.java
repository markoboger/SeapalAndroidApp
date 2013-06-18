package de.htwg.seapal.aview.gui.activity;

import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.adapter.WaypointListAdapter;
import de.htwg.seapal.controller.impl.TripController;
import de.htwg.seapal.controller.impl.WaypointController;
import de.htwg.seapal.utils.observer.Event;
import de.htwg.seapal.utils.observer.IObserver;

public class TripActivity extends BaseDrawerActivity implements IObserver {

	@Inject
	private TripController controller;

	@Inject
	private WaypointController waypointController;

	private UUID trip;

	private EditText triptitle;
	private EditText from;
	private EditText to;
	private EditText start;
	private EditText end;
	private EditText skipper;
	private EditText crew;
	private EditText duration;
	private EditText notes;
	private EditText engine;
	private EditText tank;

	private List<UUID> waypointList;

	private View header;
	private ViewGroup mainView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.trip);
		Bundle extras = getIntent().getExtras();
		trip = UUID.fromString(extras.getString("trip"));
		waypointList = waypointController.getWaypoints(trip);

		initUI();
		fillText();
	}

	@Override
	protected void onResume() {
		addListView();
		super.onResume();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.tripmenu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// if (item.getItemId() == android.R.id.home)
		// NavUtils.navigateUpFromSameTask(this);

		if (item.getItemId() == R.id.tripmenu_save) {

			if (!controller.getName(trip)
					.equals(triptitle.getText().toString()))
				controller.setName(trip, triptitle.getText().toString());
			if (!controller.getStartLocation(trip).equals(
					from.getText().toString()))
				controller.setStartLocation(trip, from.getText().toString());
			if (!controller.getEndLocation(trip)
					.equals(to.getText().toString()))
				controller.setEndLocation(trip, to.getText().toString());
			if (controller.getMotor(trip) != Integer.valueOf(engine.getText()
					.toString()))
				controller.setMotor(trip,
						Integer.valueOf(engine.getText().toString()));
			if (controller.getFuel(trip) != Double.valueOf(tank.getText()
					.toString()))
				controller.setFuel(trip,
						Double.valueOf(tank.getText().toString()));

			if (!controller.getCrewMembers(trip).equals(
					crew.getText().toString()))
				controller.setCrewMember(trip, crew.getText().toString());

			if (!controller.getNotes(trip).equals(notes.getText().toString()))
				controller.setNotes(trip, notes.getText().toString());

			// skipper

			Toast.makeText(this, "Saved Changes", Toast.LENGTH_SHORT).show();
		}

		return true;
	}

	@Override
	public void update(Event event) {
		fillText();
	}

	private void fillText() {

		triptitle.setText(controller.getName(trip));
		from.setText(controller.getStartLocation(trip));
		to.setText(controller.getEndLocation(trip));
		start.setText(DateFormat.format("yyyy/MM/dd hh:mm",
				controller.getStartTime(trip)));
		end.setText(DateFormat.format("yyyy/MM/dd hh:mm",
				controller.getEndTime(trip)));

		if (controller.getSkipper(trip) == null)
			skipper.setText("-");
		else
			skipper.setText(controller.getSkipper(trip).toString());

		duration.setText(calcDuration());
		notes.setText(controller.getNotes(trip));
		crew.setText(controller.getCrewMembers(trip));
		engine.setText(Integer.toString(controller.getMotor(trip)));
		tank.setText(Double.toString(controller.getFuel(trip)));

	}

	private String calcDuration() {
		long l1 = controller.getStartTime(trip);
		long l2 = controller.getEndTime(trip);
		long diff = l2 - l1;

		long secondInMillis = 1000;
		long minuteInMillis = secondInMillis * 60;
		long hourInMillis = minuteInMillis * 60;
		long dayInMillis = hourInMillis * 24;

		long elapsedDays = diff / dayInMillis;
		diff = diff % dayInMillis;
		long elapsedHours = diff / hourInMillis;
		diff = diff % hourInMillis;
		long elapsedMinutes = diff / minuteInMillis;
		diff = diff % minuteInMillis;
		long elapsedSeconds = diff / secondInMillis;

		return elapsedDays + "d   " + elapsedHours + "h   " + elapsedMinutes
				+ "m   " + elapsedSeconds + "s";
	}

	private void initUI() {
		triptitle = (EditText) findViewById(R.id.trip_editTripname);
		from = (EditText) findViewById(R.id.trip_editFrom);
		to = (EditText) findViewById(R.id.trip_editTo);
		start = (EditText) findViewById(R.id.trip_editStart);
		start.setFocusable(false);
		end = (EditText) findViewById(R.id.trip_editEnd);
		end.setFocusable(false);
		skipper = (EditText) findViewById(R.id.trip_editSkipper);
		crew = (EditText) findViewById(R.id.trip_editCrew);
		duration = (EditText) findViewById(R.id.trip_editDuration);
		duration.setFocusable(false);
		notes = (EditText) findViewById(R.id.trip_editNotes);
		engine = (EditText) findViewById(R.id.trip_editEngine);
		engine.setInputType(InputType.TYPE_CLASS_NUMBER);
		tank = (EditText) findViewById(R.id.trip_editTank);
		tank.setInputType(InputType.TYPE_CLASS_NUMBER
				| InputType.TYPE_NUMBER_FLAG_DECIMAL);
	}

	private void addListView() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		header = inflater.inflate(R.layout.tripwaypointlistheader, null);
		mainView = (ViewGroup) findViewById(R.id.trip_WaypointListLayout);
		mainView.addView(header, 0);
		ListView waypointListView = (ListView) findViewById(R.id.trip_WaypointList);
		WaypointListAdapter adapter = new WaypointListAdapter(this,
				R.layout.tripwaypointlistadapter, waypointList,
				waypointController);


		waypointListView.setAdapter(adapter);
		waypointListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				//start WaypointActivity
			}

		});
	}
}
