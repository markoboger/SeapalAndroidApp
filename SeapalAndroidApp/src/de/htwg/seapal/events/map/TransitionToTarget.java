package de.htwg.seapal.events.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by jakub on 3/2/14.
 */
public class TransitionToTarget {
    private final Marker marker;
    private GoogleMap map;


    public TransitionToTarget(GoogleMap map, Marker marker) {
        this.map = map;
        this.marker = marker;


    }

    public GoogleMap getMap() {
        return map;
    }

    public Marker getMarker() {
        return marker;
    }
}
