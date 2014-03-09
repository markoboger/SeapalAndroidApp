package de.htwg.seapal.manager.map;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
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

import de.htwg.seapal.events.map.MarkerDeleteEvent;
import de.htwg.seapal.events.map.OnMapRestoreInstanceEvent;
import de.htwg.seapal.events.map.OnMapSaveInstanceEvent;
import de.htwg.seapal.events.map.RequestRedrawEvent;
import de.htwg.seapal.events.map.waypointmanager.AddWayointEvent;
import de.htwg.seapal.events.map.waypointmanager.AddWaypointPolylineEvent;
import de.htwg.seapal.events.map.waypointmanager.RedrawWaypointsEvent;
import de.htwg.seapal.events.map.waypointmanager.RequestWaypointsZoom;
import roboguice.event.EventManager;
import roboguice.event.Observes;
import roboguice.inject.ContextSingleton;

/**
 * Created by jakub on 2/28/14.
 */
@ContextSingleton
public class PolylineManager implements Parcelable {

    private Map<PolylineOptions, List<LatLng>> waypointsPolyline;
    private Map<MarkerOptions, List<LatLng>> markers;

    public static final PolylineOptions DEFAULT_POLYLINE_OPTIONS = new PolylineOptions()
            .width(5);

    public static final MarkerOptions DEFAULT_MARKER_OPTIONS = new MarkerOptions()
            .anchor(0.25f, 1.0f - 0.08333f);


    @Inject
    private EventManager eventManager;

    public PolylineManager() {
        markers = new HashMap<MarkerOptions, List<LatLng>>();
        waypointsPolyline = new HashMap<PolylineOptions, List<LatLng>>();
    }

    public void addNewPolyLine(@Observes AddWaypointPolylineEvent event) {
        GoogleMap map = event.getMap();
        PolylineOptions polylineOptions = event.getPolylineOptions();
        MarkerOptions markerOptions = event.getMarkerOptions();


        if (waypointsPolyline.containsKey(polylineOptions) && markers.containsKey(markerOptions)) {
            waypointsPolyline.get(polylineOptions);
            markers.remove(markerOptions);

        }

        waypointsPolyline.put(polylineOptions, map.addPolyline(polylineOptions).getPoints());
        markers.put(markerOptions, new LinkedList<LatLng>());

        eventManager.fire(new RedrawWaypointsEvent(event.getContext(), map));
    }


    public void addWaypoint(@Observes AddWayointEvent event) {
        GoogleMap map = event.getMap();
        PolylineOptions polylineOptions = event.getPolylineOptions();
        MarkerOptions markerOptions = event.getMarkerOptions();
        LatLng o = event.getLatLng();

        if (waypointsPolyline.containsKey(polylineOptions) && markers.containsKey(markerOptions)) {

            Marker m = map.addMarker(markerOptions.position(o));
            markers.get(markerOptions).add(m.getPosition());


            List<LatLng> waypointsLatLngList = waypointsPolyline.get(polylineOptions);
            waypointsLatLngList.add(o);
            map.addPolyline(polylineOptions).setPoints(waypointsLatLngList);
            waypointsPolyline.put(polylineOptions, waypointsLatLngList);
        }
    }


    public void redrawWaypoints(@Observes RequestRedrawEvent event) {
        GoogleMap map = event.getMap();

        for (Map.Entry<PolylineOptions, List<LatLng>> polylineEntry : waypointsPolyline.entrySet()) {
            List<LatLng> latLng = polylineEntry.getValue();
            if (latLng != null && !latLng.isEmpty()) {
                Polyline p = map.addPolyline(polylineEntry.getKey());
                p.setPoints(latLng);
                waypointsPolyline.put(polylineEntry.getKey(), p.getPoints());
            }

        }

        for (Map.Entry<MarkerOptions, List<LatLng>> markerOptionsListEntry : this.markers.entrySet()) {
            List<LatLng> markers = markerOptionsListEntry.getValue();
            if (markers != null && !markers.isEmpty()) {
                for (LatLng m : markers) {
                    map.addMarker(markerOptionsListEntry.getKey().position(m));
                }
            }

        }


    }

    public void zoomToWaypointRoute(@Observes RequestWaypointsZoom event) {

        MarkerOptions markerOptions = event.getMarkerOptions();
        PolylineOptions polylineOptions = event.getPolylineOptions();
        GoogleMap map = event.getMap();

        if (waypointsPolyline.containsKey(polylineOptions) && markers.containsKey(markerOptions)) {

            List<LatLng> waypointList = waypointsPolyline.get(polylineOptions);
            if (waypointList != null && !waypointList.isEmpty()) {
                LatLngBounds.Builder boundsBuilder = LatLngBounds.builder();
                for (LatLng latLng : waypointList) {
                    boundsBuilder.include(latLng);
                }
                LatLngBounds latLngBounds = boundsBuilder.build();
                CameraUpdate yourLocation = CameraUpdateFactory.newLatLngBounds(latLngBounds, 50);
                map.animateCamera(yourLocation);
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
        waypointsPolyline = (Map<PolylineOptions, List<LatLng>>) saved.getSerializable("waypoint_polyline");
        markers = (Map<MarkerOptions, List<LatLng>>) saved.getSerializable("waypoint_markers");

    }


    public final Parcelable.Creator<PolylineManager> CREATOR
            = new Parcelable.Creator<PolylineManager>() {
        public PolylineManager createFromParcel(Parcel in) {
            Bundle b = in.readBundle();
            PolylineManager.this.waypointsPolyline = (Map<PolylineOptions, List<LatLng>>) b.getParcelable("waypoints");
            PolylineManager.this.markers = (Map<MarkerOptions, List<LatLng>>) b.getParcelable("markers");
            return PolylineManager.this;
        }

        public PolylineManager[] newArray(int size) {
            return new PolylineManager[size];
        }
    };

    public void deletePolylineFromMarker(@Observes MarkerDeleteEvent event) {
        LatLng position = event.getMarker().getPosition();
        PolylineOptions foundPolylineOptions = null;
        MarkerOptions foundMarkerOptions = null;
        for (Map.Entry<PolylineOptions, List<LatLng>> entry : waypointsPolyline.entrySet()) {
            if (entry.getValue().contains(position))
                foundPolylineOptions = entry.getKey();
        }
        for (Map.Entry<MarkerOptions, List<LatLng>> entry : markers.entrySet()) {
            if (entry.getValue().contains(position))
                foundMarkerOptions = entry.getKey();
        }
        if (foundMarkerOptions != null && foundPolylineOptions != null) {
            waypointsPolyline.remove(foundPolylineOptions);
            markers.remove(foundMarkerOptions);

        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle b = new Bundle();
        b.putSerializable("waypoints", (Serializable) waypointsPolyline);
        b.putSerializable("markers", (Serializable) markers);
        dest.writeBundle(b);
    }
}
