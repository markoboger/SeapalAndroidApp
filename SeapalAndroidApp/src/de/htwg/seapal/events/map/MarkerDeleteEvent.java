package de.htwg.seapal.events.map;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by jakub on 3/2/14.
 */
public class MarkerDeleteEvent {
    private Marker marker;

    public MarkerDeleteEvent(Marker marker) {
        this.marker = marker;

    }

    public Marker getMarker() {
        return marker;
    }
}
