package de.htwg.seapal.events.map;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by jakub on 3/2/14.
 */
public class RemoveCrosshairEvent {
    private LatLng position;

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }
}
