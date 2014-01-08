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
public class WaypointDrawingMapPlugin  implements IMapPlugin<LatLng, List<LatLng>>{

    private Polyline waypointsPolyline;
    private MarkerOptions waypointsMarkerOptions;
    private List<Marker> markers;
    private PolylineOptions waypointPolylineOption;

    public WaypointDrawingMapPlugin(GoogleMap map, String lineColor) {

        waypointsMarkerOptions = new MarkerOptions()
                .anchor(0.25f, 1.0f - 0.08333f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ann_mark));

        waypointPolylineOption = new PolylineOptions()
                .width(5)
                .color(Color.parseColor(lineColor));


        waypointsPolyline = map.addPolyline(waypointPolylineOption);
        markers = new LinkedList<Marker>();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LatLng> doAction(GoogleMap map, LatLng o) {
        List<LatLng> waypointsLatLngList = waypointsPolyline.getPoints();
        markers.add(map.addMarker(waypointsMarkerOptions.position(o)));
        waypointsLatLngList.add(o);
        waypointsPolyline.setPoints(waypointsLatLngList);
        return waypointsPolyline.getPoints();
    }

    @Override
    public void redraw(GoogleMap map) {
        for (Marker m : markers) {
            map.addMarker(waypointsMarkerOptions.position(m.getPosition()));
        }
        List<LatLng> latLngs = waypointsPolyline.getPoints();
        waypointsPolyline = map.addPolyline(waypointPolylineOption);
        waypointsPolyline.setPoints(latLngs);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void zoomTo(GoogleMap map) {
        MapActivity.zoomToWaypointRoute(map, waypointsPolyline.getPoints());
    }
}
