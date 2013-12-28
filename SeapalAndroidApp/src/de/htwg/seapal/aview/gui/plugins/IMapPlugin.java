package de.htwg.seapal.aview.gui.plugins;

import com.google.android.gms.maps.GoogleMap;

import java.io.Serializable;

/**
 * Created by jakub on 12/27/13.
 */

public interface IMapPlugin<T, R> extends Serializable {
    R doAction(GoogleMap map,T t);
    void redraw(GoogleMap map);
    void clear();

    void zoomTo(GoogleMap map);


}
