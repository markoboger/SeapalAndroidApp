package de.htwg.seapal.events.crew;

import android.content.Context;

/**
 * Created by jakub on 2/22/14.
 */
public class OnCrewAddEvent {

    public OnCrewAddEvent(Context context) {
        this.context = context;
    }

    private Context context;

    public Context getContext() {
        return context;
    }
}
