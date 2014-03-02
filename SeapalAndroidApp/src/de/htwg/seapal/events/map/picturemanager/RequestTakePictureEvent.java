package de.htwg.seapal.events.map.picturemanager;

import android.content.Context;

/**
 * Created by jakub on 3/2/14.
 */
public class RequestTakePictureEvent {
    private Context context;

    public RequestTakePictureEvent(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
