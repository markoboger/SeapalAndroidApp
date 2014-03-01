package de.htwg.seapal.events.map;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by jakub on 2/28/14.
 */
public class RedrawWaypointsEvent {
    private Context context;
    private GoogleMap map;


    private String lineColor;


    public RedrawWaypointsEvent(Context context, GoogleMap map, String lineColor) {
        this.context = context;
        this.map = map;
        this.lineColor = lineColor;
    }

    public GoogleMap getMap() {
        return map;
    }

    public Context getContext() {
        return context;
    }

    public String getLineColor() {
        return lineColor;
    }
}
