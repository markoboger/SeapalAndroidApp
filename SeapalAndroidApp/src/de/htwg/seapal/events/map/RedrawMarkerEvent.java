package de.htwg.seapal.events.map;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by jakub on 3/6/14.
 */
public class RedrawMarkerEvent {
    private GoogleMap map;
    private Context context;

    public RedrawMarkerEvent(Context context, GoogleMap map) {
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
