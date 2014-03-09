package de.htwg.seapal.events.map.picturemanager;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by jakub on 3/2/14.
 */
public class PlacePictureOnMapEvent {
    private Context context;
    private GoogleMap map;
    private Intent data;

    public PlacePictureOnMapEvent(Context context, GoogleMap map, Intent data) {
        this.context = context;
        this.map = map;
        this.data = data;
    }

    public Intent getData() {
        return data;
    }

    public GoogleMap getMap() {
        return map;
    }

    public Context getContext() {
        return context;
    }
}
