package de.htwg.seapal.events.map;

import android.os.Bundle;

/**
 * Created by jakub on 2/28/14.
 */
public class OnMapSaveInstanceEvent {
    private Bundle outBundle;


    public OnMapSaveInstanceEvent(Bundle outState) {
        outBundle = outState;
    }

    public Bundle getOutBundle() {
        return outBundle;
    }
}
