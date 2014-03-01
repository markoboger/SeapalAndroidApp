package de.htwg.seapal.manager.map;

import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
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

    private Map<PolylineOptions, Polyline> waypointsPolyline;
    private Map<MarkerOptions, List<Marker>> markers;

    public static final PolylineOptions DEFAULT_POLYLINE_OPTIONS = new PolylineOptions()
            .width(5);

    public static final MarkerOptions DEFAULT_MARKER_OPTIONS = new MarkerOptions()
            .anchor(0.25f, 1.0f - 0.08333f);


    @Inject
    private EventManager eventManager;

    public WaypointManager() {
        markers = new HashMap<MarkerOptions, List<Marker>>();
        waypointsPolyline = new HashMap<PolylineOptions, Polyline>();
    }

    public void addNewPolyLine(@Observes AddWaypointPolyline event) {
        GoogleMap map = event.getMap();
        PolylineOptions polylineOptions = event.getPolylineOptions();
        MarkerOptions markerOptions = event.getMarkerOptions();

        if (!waypointsPolyline.containsKey(polylineOptions) && !markers.containsKey(markerOptions)) {
            waypointsPolyline.put(polylineOptions, map.addPolyline(polylineOptions));
            markers.put(markerOptions, new LinkedList<Marker>());

        }


    }


    public void addWaypoint(@Observes AddWayointEvent event) {
        GoogleMap map = event.getMap();
        PolylineOptions polylineOptions = event.getPolylineOptions();
        MarkerOptions markerOptions = event.getMarkerOptions();
        LatLng o = event.getLatLng();

        if (waypointsPolyline.containsKey(polylineOptions) && markers.containsKey(markerOptions)) {

            markers.get(markerOptions).add(map.addMarker(markerOptions.position(o)));

            List<LatLng> waypointsLatLngList = waypointsPolyline.get(polylineOptions).getPoints();
            waypointsLatLngList.add(o);
            waypointsPolyline.get(polylineOptions).setPoints(waypointsLatLngList);
        }
    }


    public void redrawWaypoints(@Observes RedrawWaypointsEvent event) {
        GoogleMap map = event.getMap();
        PolylineOptions polylineOptions = event.getPolylineOptions();
        MarkerOptions markerOptions = event.getMarkerOptions();


        for (Map.Entry<PolylineOptions, Polyline> polylineEntry : waypointsPolyline.entrySet()) {
            Polyline polyline = polylineEntry.getValue();
            List<LatLng> latLng = polyline.getPoints();
            if (polyline != null && !latLng.isEmpty()) {
                Polyline p = map.addPolyline(polylineEntry.getKey());
                p.setPoints(latLng);
                waypointsPolyline.put(polylineOptions, p);
            }

        }

        for (Map.Entry<MarkerOptions, List<Marker>> markerOptionsListEntry : this.markers.entrySet()) {
            List<Marker> markers = markerOptionsListEntry.getValue();
            if (markers != null && !markers.isEmpty()) {

                for (Marker m : markers) {
                    map.addMarker(markerOptions.position(m.getPosition()));
                }
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
        waypointsPolyline = (Map<PolylineOptions, Polyline>) saved.getSerializable("waypoint_polyline");
        markers = (Map<MarkerOptions, List<Marker>>) saved.getSerializable("waypoint_markers");

    }


}
