package de.htwg.seapal.manager.map;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.inject.Inject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.htwg.seapal.R;
import de.htwg.seapal.events.map.AddWayointEvent;
import de.htwg.seapal.events.map.AddWaypointPolyline;
import de.htwg.seapal.events.map.OnMapRestoreInstanceEvent;
import de.htwg.seapal.events.map.OnMapSaveInstanceEvent;
import de.htwg.seapal.events.map.RedrawWaypointsEvent;
import roboguice.event.EventManager;
import roboguice.event.Observes;

/**
 * Created by jakub on 2/28/14.
 */
public class WaypointManager {

    private Map<String, Polyline> waypointsPolyline;
    private MarkerOptions waypointsMarkerOptions;
    private Map<String, List<Marker>> markers;
    private PolylineOptions waypointPolylineOption;


    @Inject
    private EventManager eventManager;

    public WaypointManager() {
        waypointsMarkerOptions = new MarkerOptions()
                .anchor(0.25f, 1.0f - 0.08333f);

        waypointPolylineOption = new PolylineOptions()
                .width(5);
        markers = new HashMap<String, List<Marker>>();
        waypointsPolyline = new HashMap<String, Polyline>();
    }

    public void addNewPolyLine(@Observes AddWaypointPolyline event) {
        String lineColor = event.getLineColor();
        GoogleMap map = event.getMap();

        waypointsMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ann_mark));
        waypointPolylineOption.color(Color.parseColor(lineColor));
        if (!waypointsPolyline.containsKey(lineColor) && !markers.containsKey(lineColor)) {
            waypointsPolyline.put(lineColor, map.addPolyline(waypointPolylineOption));
            markers.put(lineColor, new LinkedList<Marker>());

        }


    }


    public void addWaypoint(@Observes AddWayointEvent event) {
        GoogleMap map = event.getMap();
        LatLng o = event.getLatLng();
        String lineColor = event.getLineColor();
        if (waypointsPolyline.containsKey(lineColor) && markers.containsKey(lineColor)) {
            List<LatLng> waypointsLatLngList = waypointsPolyline.get(lineColor).getPoints();
            markers.get(lineColor).add(map.addMarker(waypointsMarkerOptions.position(o)));
            waypointsLatLngList.add(o);
            waypointsPolyline.get(lineColor).setPoints(waypointsLatLngList);
        }
    }


    public void redrawWaypoints(@Observes RedrawWaypointsEvent event) {
        GoogleMap map = event.getMap();
        String lineColor = event.getLineColor();

        if (waypointsPolyline.containsKey(lineColor) && markers.containsKey(lineColor)) {

            List<Marker> markers = this.markers.get(lineColor);
            Polyline polyline = waypointsPolyline.get(lineColor);
            List<LatLng> latLng = polyline.getPoints();

            if (markers != null && !markers.isEmpty() && polyline != null && !latLng.isEmpty()) {

                for (Marker m : markers) {
                    waypointsMarkerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ann_mark));
                    map.addMarker(waypointsMarkerOptions.position(m.getPosition()));
                }
                Polyline p = map.addPolyline(waypointPolylineOption.color(Color.parseColor(lineColor)));
                p.setPoints(latLng);
                waypointsPolyline.put(lineColor, p);
            }
        }
    }


    public void saveInstance(@Observes OnMapSaveInstanceEvent event) {
        Bundle out = event.getOutBundle();
        if (waypointsPolyline != null) {
            out.putSerializable("waypoint_polyline", (Serializable) waypointsPolyline);
        }
        if (markers != null)
            out.putSerializable("waypoint_markers", (Serializable) markers);
    }

    public void restoreInstance(@Observes OnMapRestoreInstanceEvent event) {
        Bundle saved = event.getSavedInstance();
        GoogleMap map = event.getMap();
        waypointsPolyline = (Map<String, Polyline>) saved.getSerializable("waypoint_polyline");
        markers = (Map<String, List<Marker>>) saved.getSerializable("waypoint_markers");

        for (Map.Entry<String, List<Marker>> entry : markers.entrySet()) {
            if (waypointsPolyline.containsKey(entry.getKey())) {
                redrawWaypoints(new RedrawWaypointsEvent(null, map, entry.getKey()));
            }
        }


    }


}
