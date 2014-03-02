package de.htwg.seapal.events.map;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by jakub on 3/2/14.
 */
public class CrosshairChangedEvent {
    private Context context;
    private GoogleMap map;
    private Marker crosshairMarker;

    public CrosshairChangedEvent(Context context, GoogleMap map, Marker crosshairMarker) {
        this.context = context;
        this.map = map;
        this.crosshairMarker = crosshairMarker;
    }

    public Context getContext() {
        return context;
    }

    public GoogleMap getMap() {
        return map;
    }

    public Marker getCrosshairMarker() {
        return crosshairMarker;
    }
}
