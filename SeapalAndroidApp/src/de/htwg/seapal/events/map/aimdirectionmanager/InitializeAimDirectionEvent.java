package de.htwg.seapal.events.map.aimdirectionmanager;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by jakub on 3/2/14.
 */
public class InitializeAimDirectionEvent {

    private Context context;
    private GoogleMap map;

    public InitializeAimDirectionEvent(Context context, GoogleMap map) {
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
