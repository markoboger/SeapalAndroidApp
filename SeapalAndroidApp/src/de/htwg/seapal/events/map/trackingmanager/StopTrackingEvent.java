package de.htwg.seapal.events.map.trackingmanager;

import android.content.Context;

/**
 * Created by jakub on 2/28/14.
 */
public class StopTrackingEvent {

    private Context context;

    public StopTrackingEvent(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
