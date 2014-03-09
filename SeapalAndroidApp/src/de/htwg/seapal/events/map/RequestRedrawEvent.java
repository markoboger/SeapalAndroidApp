package de.htwg.seapal.events.map;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by jakub on 3/6/14.
 */
public class RequestRedrawEvent {
    private Context context;

    private GoogleMap map;

    public RequestRedrawEvent(Context context, GoogleMap map) {
        this.context = context;
        this.map = map;
    }

    public Context getContext() {
        return context;
    }

    public GoogleMap getMap() {
        return map;
    }
}
