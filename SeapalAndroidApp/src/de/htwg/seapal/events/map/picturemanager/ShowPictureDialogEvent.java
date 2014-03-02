package de.htwg.seapal.events.map.picturemanager;

import android.content.Context;

/**
 * Created by jakub on 3/2/14.
 */
public class ShowPictureDialogEvent {
    private Context context;

    public ShowPictureDialogEvent(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
