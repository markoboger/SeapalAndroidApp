package de.htwg.seapal.manager.map;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.inject.Inject;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.activity.LogbookTabsActivity;
import de.htwg.seapal.controller.IMainController;
import de.htwg.seapal.events.map.OnMapRestoreInstanceEvent;
import de.htwg.seapal.events.map.OnMapSaveInstanceEvent;
import de.htwg.seapal.events.map.trackingmanager.StartTrackingEvent;
import de.htwg.seapal.events.map.trackingmanager.StopTrackingEvent;
import de.htwg.seapal.events.map.waypointmanager.AddWayointEvent;
import de.htwg.seapal.events.map.waypointmanager.AddWaypointPolylineEvent;
import de.htwg.seapal.events.map.waypointmanager.RedrawWaypointsEvent;
import de.htwg.seapal.events.map.waypointmanager.RequestWaypointsZoom;
import de.htwg.seapal.manager.SessionManager;
import de.htwg.seapal.model.IModel;
import de.htwg.seapal.model.impl.Trip;
import de.htwg.seapal.services.TrackingService;
import roboguice.event.EventManager;
import roboguice.event.Observes;

/**
 * Created by jakub on 2/28/14.
 */
public class TrackingManager {


    @Inject
    private IMainController mainController;

    @Inject
    private SessionManager sessionManager;

    @Inject
    private Context context;

    @Inject
    private EventManager eventManager;

    private TrackingServiceWaypointBroadcastReceiver waypointBroadcastReceiver;

    private Intent trackingService;

    private GoogleMap map;

    public static final MarkerOptions TRACKING_MARKER_OPTIONS = new MarkerOptions()
            .anchor(0.25f, 1.0f - 0.08333f)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ann_mark));

    public static final PolylineOptions TRACKING_POLYLINE_OPTIONS = new PolylineOptions()
            .width(5)
            .color(Color.parseColor("#333333"));


    @Inject
    private WaypointManager waypointManager;


    public class TrackingServiceWaypointBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            LatLng latLng = intent.getParcelableExtra(TrackingService.LAT_LNG);
            eventManager.fire(new AddWayointEvent(context, map, TRACKING_MARKER_OPTIONS, TRACKING_POLYLINE_OPTIONS, latLng));

        }
    }

    public void stopTracking(@Observes StopTrackingEvent event) {
        Context context = event.getContext();
        if (trackingService != null) {

            UUID tripUuid = UUID.fromString(trackingService.getStringExtra(TrackingService.TRIP_UUID));
            Collection<Trip> trips = (Collection<Trip>) mainController.getSingleDocument("trip", sessionManager.getSession(), tripUuid);

            if (trips.iterator().hasNext()) {

                Trip t = trips.iterator().next();


                t.setEndDate(System.currentTimeMillis());
                mainController.creatDocument("trip", t, sessionManager.getSession());

                if (waypointBroadcastReceiver != null) {
                    context.unregisterReceiver(waypointBroadcastReceiver);
                }

                context.stopService(trackingService);
                eventManager.fire(new RequestWaypointsZoom(context, map, TRACKING_MARKER_OPTIONS, TRACKING_POLYLINE_OPTIONS));

                trackingService = null;
                Toast.makeText(context, "Tracking Stopped", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Somehow the trip tracking did not create a trip, maybe you started tracking for a crew ship.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Tracking not Started", Toast.LENGTH_SHORT).show();
        }

    }

    public void startTracking(@Observes StartTrackingEvent event) {
        final Context context = event.getContext();
        map = event.getMap();


        SharedPreferences s = context.getSharedPreferences(LogbookTabsActivity.LOGBOOK_PREFS, 0);
        final String boatString = s.getString(LogbookTabsActivity.LOGBOOK_BOAT_FAVOURED, "");

        if (!StringUtils.isEmpty(boatString) && trackingService == null) {

            Collection<? extends IModel> boat = mainController.getSingleDocument("boat", sessionManager.getSession(), UUID.fromString(boatString));
            if (!boat.isEmpty()) {

                eventManager.fire(new AddWaypointPolylineEvent(context, map, TRACKING_MARKER_OPTIONS, TRACKING_POLYLINE_OPTIONS));

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.start_tracking_dialog, null);


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.enter_some_trip_info)
                        .setView(view)
                        .setPositiveButton(R.string.tracking_start, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                EditText name = (EditText) view.findViewById(R.id.tracking_trip_name);
                                EditText from = (EditText) view.findViewById(R.id.tracking_trip_from);
                                EditText to = (EditText) view.findViewById(R.id.tracking_trip_to);


                                Trip t = new Trip();
                                t.setStartDate(System.currentTimeMillis());
                                t.setName(name.getText().toString());
                                t.setTo(to.getText().toString());
                                t.setFrom(from.getText().toString());
                                t.setBoat(boatString);
                                t = (Trip) mainController.creatDocument("trip", t, sessionManager.getSession());

                                trackingService = new Intent(context, TrackingService.class);
                                UUID tripUuid = t.getUUID();
                                trackingService.putExtra(TrackingService.TRIP, t);
                                trackingService.putExtra(TrackingService.TRIP_UUID, tripUuid.toString());
                                trackingService.putExtra(TrackingService.BOAT_UUID, boatString);


                                waypointBroadcastReceiver = new TrackingServiceWaypointBroadcastReceiver();
                                context.registerReceiver(waypointBroadcastReceiver, new IntentFilter(TrackingService.WAYPOINT_BROADCAST_RECEIVER));


                                Toast.makeText(context, "Tracking Started", Toast.LENGTH_SHORT).show();

                                context.startService(trackingService);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });
                // Create the AlertDialog object and show it.
                builder.create().show();


            } else {
                Toast.makeText(context, "You probably favoured a boat of your crew", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(context, "No favoured boat or tracking already started", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveInstance(@Observes OnMapSaveInstanceEvent event) {
        Bundle outState = event.getOutBundle();
        outState.putParcelable("tracking_manager_service", trackingService);
        List l = new LinkedList<Object>();
        l.add(waypointManager);
        outState.putSerializable("tracking_manager_waypoint", (Serializable) l);

    }

    public void restoreInstance(@Observes OnMapRestoreInstanceEvent event) {
        Bundle savedInstance = event.getSavedInstance();
        map = event.getMap();
        trackingService = (Intent) savedInstance.get("tracking_manager_service");
        LinkedList list = (LinkedList) savedInstance.get("tracking_manager_waypoint");
        waypointManager = (WaypointManager) list.getFirst();
        eventManager.fire(new RedrawWaypointsEvent(context, map, TRACKING_MARKER_OPTIONS, TRACKING_POLYLINE_OPTIONS));

        if (trackingService != null) {
            waypointBroadcastReceiver = new TrackingServiceWaypointBroadcastReceiver();
            context.registerReceiver(waypointBroadcastReceiver, new IntentFilter(TrackingService.WAYPOINT_BROADCAST_RECEIVER));
        }


    }

}

