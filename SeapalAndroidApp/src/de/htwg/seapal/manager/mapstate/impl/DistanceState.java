package de.htwg.seapal.manager.mapstate.impl;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcel;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.inject.Inject;

import java.util.LinkedList;
import java.util.List;

import de.htwg.seapal.R;
import de.htwg.seapal.events.map.OnMapRestoreInstanceEvent;
import de.htwg.seapal.events.map.OnMapSaveInstanceEvent;
import de.htwg.seapal.events.map.waypointmanager.AddWayointEvent;
import de.htwg.seapal.events.map.waypointmanager.AddWaypointPolylineEvent;
import de.htwg.seapal.events.map.waypointmanager.RedrawWaypointsEvent;
import de.htwg.seapal.manager.map.PolylineManager;
import de.htwg.seapal.manager.mapstate.Statelike;
import roboguice.event.EventManager;
import roboguice.event.Observes;

/**
 * Created by jakub on 2/28/14.
 */
public class DistanceState implements Statelike {

    private static final String ORANGE = "#FFBB03";

    public static final MarkerOptions MARKER_OPTIONS = new MarkerOptions()
            .anchor(0.25f, 1.0f - 0.08333f)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ann_distance));

    public static final PolylineOptions POLYLINE_OPTIONS = new PolylineOptions()
            .width(5)
            .color(Color.parseColor(ORANGE));

    @Inject
    private Context context;

    @Inject
    private PolylineManager polylineManager;

    @Inject
    private EventManager eventManager;

    /**
     * initialized state
     */
    private boolean initialized = false;

    private Double distance = 0.0;

    private LatLng lastPos;

    @Override
    public void doAction(Context context, GoogleMap map, LatLng latlng) {
        if (!initialized) {
            eventManager.fire(new AddWaypointPolylineEvent(context, map, MARKER_OPTIONS, POLYLINE_OPTIONS));
            lastPos = latlng;
            initialized = true;

        }
        distance += calcDistance(lastPos, latlng);
        eventManager.fire(new AddWayointEvent(context, map, MARKER_OPTIONS, POLYLINE_OPTIONS, latlng));
        Toast.makeText(context, String.format("Calculated Distance = %.2f KM", distance), 0).show();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle b = new Bundle();
        b.putBoolean("initialized", initialized);
        b.putDouble("distance", distance);
        b.putParcelable("lastPos", lastPos);
        dest.writeBundle(b);
    }

    public void saveInstance(@Observes OnMapSaveInstanceEvent event) {
        Bundle outState = event.getOutBundle();
        List l = new LinkedList<Object>();

        outState.putBoolean("initialized", initialized);
        outState.putDouble("distance", distance);
        outState.putParcelable("lastPos", lastPos);


    }

    public void restoreInstance(@Observes OnMapRestoreInstanceEvent event) {
        Bundle savedInstance = event.getSavedInstance();
        GoogleMap map = event.getMap();
        initialized = savedInstance.getBoolean("initialized");
        distance = savedInstance.getDouble("distance");
        lastPos = savedInstance.getParcelable("lastPos");

        eventManager.fire(new RedrawWaypointsEvent(context, map, MARKER_OPTIONS, POLYLINE_OPTIONS));


    }

    /**
     * calculates the Distance of currentPosition and distantPosition
     *
     * @param currentPosition latlng of the current position
     * @param distantPosition latlnt of the distant position
     * @return the distance in kilometers
     */
    private Double calcDistance(LatLng currentPosition, LatLng distantPosition) {

        int R = 6371; // km earth radius
        double dLat = Math.toRadians(distantPosition.latitude - currentPosition.latitude);
        double dLon = Math.toRadians(distantPosition.longitude - currentPosition.longitude);
        double lat1 = Math.toRadians(currentPosition.latitude);
        double lat2 = Math.toRadians(distantPosition.latitude);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
