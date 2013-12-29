package de.htwg.seapal.aview.gui.plugins.impl;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.LinkedList;
import java.util.List;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.activity.MapActivity;
import de.htwg.seapal.aview.gui.plugins.IMapPlugin;

/**
 * Created by jakub on 12/27/13.
 */
public class RouteDrawingMapPlugin  implements IMapPlugin<LatLng, Void> {

    private MarkerOptions routeMarkerOptions;
    private PolylineOptions routePolylineOptions;
    private List<Marker> markers;
    private Polyline route;


    public RouteDrawingMapPlugin(GoogleMap map, LatLng initialPosition) {
        routeMarkerOptions = new MarkerOptions()
                .anchor(0.25f, 1.0f - 0.08333f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ann_route));

        routePolylineOptions = new PolylineOptions()
                .width(5)
                .color(Color.RED);


        map.addMarker(routeMarkerOptions.position(initialPosition));
        route = map.addPolyline(routePolylineOptions.add(initialPosition));
        markers = new LinkedList<Marker>();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Void doAction(GoogleMap map, LatLng o) {
        markers.add(map.addMarker(routeMarkerOptions.position(o)));
        List<LatLng> routeList = route.getPoints();
        routeList.add(o);
        route.setPoints(routeList);
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void redraw(GoogleMap map) {
        for (Marker m : markers) {
            map.addMarker(routeMarkerOptions.position(m.getPosition()));
        }
        List<LatLng> latLngs = route.getPoints();
        route = map.addPolyline(routePolylineOptions);
        route.setPoints(latLngs);

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        route.remove();
        markers.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void zoomTo(GoogleMap map) {
        MapActivity.zoomToWaypointRoute(map, route.getPoints());

    }
}
