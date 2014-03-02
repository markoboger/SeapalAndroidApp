package de.htwg.seapal.events.map.waypointmanager;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by jakub on 2/28/14.
 */
public class RedrawWaypointsEvent {
    private Context context;
    private GoogleMap map;


    public RedrawWaypointsEvent(Context context, GoogleMap map) {
        this.context = context;
        this.map = map;
    }

    public GoogleMap getMap() {
        return map;
    }

    public Context getContext() {
        return context;
    }

}

