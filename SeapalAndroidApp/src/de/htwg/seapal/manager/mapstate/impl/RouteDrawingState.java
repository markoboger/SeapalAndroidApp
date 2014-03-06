package de.htwg.seapal.manager.mapstate.impl;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.inject.Inject;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import de.htwg.seapal.R;
import de.htwg.seapal.events.map.OnMapRestoreInstanceEvent;
import de.htwg.seapal.events.map.OnMapSaveInstanceEvent;
import de.htwg.seapal.events.map.waypointmanager.AddWayointEvent;
import de.htwg.seapal.events.map.waypointmanager.AddWaypointPolylineEvent;
import de.htwg.seapal.manager.map.PolylineManager;
import de.htwg.seapal.manager.mapstate.Statelike;
import roboguice.event.EventManager;
import roboguice.event.Observes;

/**
 * Created by jakub on 2/28/14.
 */
public class RouteDrawingState implements Statelike {


    public static final MarkerOptions MARKER_OPTIONS = new MarkerOptions()
            .anchor(0.25f, 1.0f - 0.08333f)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ann_route));

    public static final PolylineOptions POLYLINE_OPTIONS = new PolylineOptions()
            .width(5)
            .color(Color.RED);

    @Inject
    private EventManager eventManager;

    @Inject
    private Context context;


    @Inject
    private PolylineManager polylineManager;


    private boolean initialized = false;


    public RouteDrawingState() {
    }

    @Override
    public void onSortPress(Context context, GoogleMap map, LatLng latlng) {
        if (!initialized) {
            eventManager.fire(new AddWaypointPolylineEvent(context, map, MARKER_OPTIONS, POLYLINE_OPTIONS));
            initialized = true;

        }

        eventManager.fire(new AddWayointEvent(context, map, MARKER_OPTIONS, POLYLINE_OPTIONS, latlng));


    }

    @Override
    public void onLongPress(Context context, GoogleMap map, LatLng latlng) {

    }

    public void saveInstance(@Observes OnMapSaveInstanceEvent event) {
        Bundle outState = event.getOutBundle();
        List l = new LinkedList<Object>();
        l.add(polylineManager);
        outState.putSerializable("route_manager_waypoint", (Serializable) l);
        outState.putBoolean("route_manager_initialized", initialized);

    }

    public void restoreInstance(@Observes OnMapRestoreInstanceEvent event) {
        Bundle savedInstance = event.getSavedInstance();
        GoogleMap map = event.getMap();

        LinkedList list = (LinkedList) savedInstance.get("route_manager_waypoint");
        initialized = savedInstance.getBoolean("route_manager_initialized");
        polylineManager = (PolylineManager) list.getFirst();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle b = new Bundle();
        b.putBoolean("initialized", initialized);
        dest.writeBundle(b);

    }
}
