package de.htwg.seapal.aview.gui.plugins;

import com.google.android.gms.maps.GoogleMap;

import java.io.Serializable;

/**
 * Created by jakub on 12/27/13.
 * A serializable interface for IMapPlugin
 *
 * It is a generic interface using the class T and R  see {doAction} for T and R representation
 *
 */
@Deprecated
public interface IMapPlugin<T, R> extends Serializable {
    /**
     * Does a action on the map and using the {@link com.google.android.gms.maps.GoogleMap}
     *
     * @param map the map on which the action is done
     * @param t the parameter of class T, usually a {@link com.google.android.gms.maps.model.LatLng}
     * @return return a value of the class R, maybe some calculations or lists.
     */
    R doAction(GoogleMap map,T t);

    /**
     * redraws the state of the plugin to a new instance of {@link com.google.android.gms.maps.GoogleMap}
     * @param map the map to redraw.
     */
    void redraw(GoogleMap map);

    /**
     * clears the state of the plugin.
     */
    void clear();

    /**
     * enables zooming to Markers and routes
     * @param map the map on which zooming is done
     */
    void zoomTo(GoogleMap map);


}
