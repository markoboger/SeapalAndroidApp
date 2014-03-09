package de.htwg.seapal.events.map.waypointmanager;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by jakub on 3/2/14.
 */
public class RequestWaypointsZoom {
    private Context context;
    private GoogleMap map;

    private MarkerOptions markerOptions;
    private PolylineOptions polylineOptions;


    public RequestWaypointsZoom(Context context, GoogleMap map, MarkerOptions markerOptions, PolylineOptions polylineOptions) {
        this.context = context;
        this.map = map;
        this.markerOptions = markerOptions;
        this.polylineOptions = polylineOptions;
    }

    public GoogleMap getMap() {
        return map;
    }

    public Context getContext() {
        return context;
    }

    public MarkerOptions getMarkerOptions() {
        return markerOptions;
    }

    public PolylineOptions getPolylineOptions() {
        return polylineOptions;

    }

}
