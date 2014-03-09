package de.htwg.seapal.events.map;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by jakub on 2/28/14.
 */
public class OnMapRestoreInstanceEvent {
    private GoogleMap map;
    private Bundle savedInstance;

    public OnMapRestoreInstanceEvent(GoogleMap map, Bundle savedInstance) {
        this.map = map;
        this.savedInstance = savedInstance;
    }

    public Bundle getSavedInstance() {
        return savedInstance;
    }

    public GoogleMap getMap() {
        return map;
    }
}
