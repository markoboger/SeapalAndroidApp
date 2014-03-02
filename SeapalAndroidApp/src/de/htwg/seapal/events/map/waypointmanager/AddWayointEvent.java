package de.htwg.seapal.events.map.waypointmanager;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by jakub on 2/28/14.
 */
public class AddWayointEvent {

    private Context context;
    private GoogleMap map;
    private LatLng latLng;

    private MarkerOptions markerOptions;
    private PolylineOptions polylineOptions;

    public AddWayointEvent(Context context, GoogleMap map, MarkerOptions markerOptions, PolylineOptions polylineOptions, LatLng latLng) {
        this.context = context;
        this.map = map;
        this.markerOptions = markerOptions;
        this.polylineOptions = polylineOptions;
        this.latLng = latLng;
    }

    public GoogleMap getMap() {
        return map;
    }

    public Context getContext() {
        return context;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public MarkerOptions getMarkerOptions() {
        return markerOptions;
    }

    public PolylineOptions getPolylineOptions() {
        return polylineOptions;
    }
}
