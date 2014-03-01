package de.htwg.seapal.events.map;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by jakub on 2/28/14.
 */
public class AddWaypointPolyline {

    private Context context;
    private GoogleMap map;

    /**
     * lineColor is used for key in a Map so it should be handled by the event Caller
     */
    private String lineColor;

    public AddWaypointPolyline(Context context, GoogleMap map, String lineColor) {
        this.context = context;
        this.map = map;
        this.lineColor = lineColor;
    }

    public Context getContext() {
        return context;
    }

    public GoogleMap getMap() {
        return map;
    }

    public String getLineColor() {
        return lineColor;
    }
}
