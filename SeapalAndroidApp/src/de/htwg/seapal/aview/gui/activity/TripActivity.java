package de.htwg.seapal.aview.gui.activity;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.adapter.WaypointListAdapter;
import de.htwg.seapal.controller.IMainController;
import de.htwg.seapal.manager.SessionManager;
import de.htwg.seapal.model.IWaypoint;
import de.htwg.seapal.model.impl.Trip;

public class TripActivity extends BaseDrawerActivity  {

    public static final String TRIP_PREFS = "trip_prefs";

    @Inject
    private IMainController mainController;

    @Inject
    private SessionManager sessionManager;

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
    private ImageView map;

	private List<UUID> waypointList;
    private List<IWaypoint> waypoints;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.trip);
		Bundle extras = getIntent().getExtras();
		trip = UUID.fromString(extras.getString("trip"));
        waypointList = new ArrayList<UUID>();
        waypoints = (List<IWaypoint>) mainController.getByParent("waypoint", "trip",sessionManager.getSession(), trip);
        for (IWaypoint waypoint : waypoints) {
            waypointList.add(waypoint.getUUID());
        }
		initUI();
		fillText();

        removeRightDrawer();
	}

	@Override
	protected void onResume() {
		addListView();
		super.onResume();
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

        Collection<Trip> trips = (Collection<Trip>) mainController.getSingleDocument("trip", sessionManager.getSession(), trip);
        if (trips.iterator().hasNext()) {
            Trip iTrip = trips.iterator().next();

            if (item.getItemId() == R.id.tripmenu_save) {

                if (!iTrip.getName() .equals(triptitle.getText().toString()))
                    iTrip.setName(triptitle.getText().toString());
                if (!iTrip.getFrom().equals(
                        from.getText().toString()))
                    iTrip.setFrom(from.getText().toString());
                if (!iTrip.getTo()
                        .equals(to.getText().toString()))
                    iTrip.setTo(to.getText().toString());
                if (!iTrip.getCrew().equals(crew.getText().toString()))
                    iTrip.setCrew(crew.getText().toString());

                if (!iTrip.getNotes().equals(notes.getText().toString()))
                    iTrip.setNotes(notes.getText().toString());

                // skipper

                Toast.makeText(this, "Saved Changes", Toast.LENGTH_SHORT).show();
            }
        }

		return true;
	}

	private void fillText() {

        Collection<Trip> trips = (Collection<Trip>) mainController.getSingleDocument("trip", sessionManager.getSession(), trip);
        if (trips.iterator().hasNext()) {
            Trip iTrip = trips.iterator().next();

            triptitle.setText(iTrip.getName());
            from.setText(iTrip.getFrom());
            to.setText(iTrip.getTo());
            start.setText(DateFormat.format("yyyy/MM/dd hh:mm",
                    iTrip.getStartDate()));
            end.setText(DateFormat.format("yyyy/MM/dd hh:mm",
                    iTrip.getEndDate()));

            if (iTrip.getSkipper() == null)
                skipper.setText("-");
            else
                skipper.setText(iTrip.getSkipper().toString());

            duration.setText(calcDuration());
            notes.setText(iTrip.getNotes());
            crew.setText(iTrip.getCrew());
        }

	}

	private String calcDuration() {

        Collection<Trip> trips = (Collection<Trip>) mainController.getSingleDocument("trip", sessionManager.getSession(), trip);
        if (trips.iterator().hasNext()) {
            Trip iTrip = trips.iterator().next();

            long l1 = iTrip.getStartDate();
            long l2 = iTrip.getEndDate();
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
        return "";
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
        map = (ImageView) findViewById(R.id.trip_editMap);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TripActivity.this,MapActivity.class);
                i.putExtra("waypoints", (Serializable) waypoints);
                TripActivity.this.startActivity(i);




            }
        });

	}


	private void addListView() {
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View header = inflater.inflate(R.layout.tripwaypointlistheader, null);
        ViewGroup mainView = (ViewGroup) findViewById(R.id.trip_WaypointListLayout);
		mainView.addView(header, 0);
		ListView waypointListView = (ListView) findViewById(R.id.trip_WaypointList);
		WaypointListAdapter adapter = new WaypointListAdapter(this,
                waypointList);


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
