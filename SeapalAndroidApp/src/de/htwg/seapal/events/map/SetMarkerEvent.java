package de.htwg.seapal.events.map;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by jakub on 3/6/14.
 */
public class SetMarkerEvent {
    private LatLng position;

    public SetMarkerEvent(LatLng position) {
        this.position = position;
    }

    public LatLng getPosition() {
        return position;
    }
}
