package de.htwg.seapal.events.map;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by jakub on 2/28/14.
 */
public class AddWayointEvent {

    private Context context;
    private GoogleMap map;
    private LatLng latLng;
    /**
     * lineColor is used for key in a Map so it should be handled by the event Caller
     */
    private String lineColor;

    public AddWayointEvent(Context context, GoogleMap map, LatLng latLng, String lineColor) {
        this.context = context;
        this.map = map;
        this.latLng = latLng;
        this.lineColor = lineColor;
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

    public String getLineColor() {
        return lineColor;
    }
}
