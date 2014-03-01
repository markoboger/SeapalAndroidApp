package de.htwg.seapal.events.map;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by jakub on 2/28/14.
 */
public class StartTrackingEvent {

    private Context context;
    private GoogleMap map;

    public StartTrackingEvent(Context context, GoogleMap map) {
        this.map = map;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public GoogleMap getMap() {
        return map;
    }
}
