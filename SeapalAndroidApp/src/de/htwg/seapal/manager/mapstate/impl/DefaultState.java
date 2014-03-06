package de.htwg.seapal.manager.mapstate.impl;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Vibrator;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.inject.Inject;

import de.htwg.seapal.R;
import de.htwg.seapal.events.map.MarkerDeleteEvent;
import de.htwg.seapal.events.map.OnMapRestoreInstanceEvent;
import de.htwg.seapal.events.map.OnMapSaveInstanceEvent;
import de.htwg.seapal.events.map.RedrawMarkerEvent;
import de.htwg.seapal.events.map.RemoveCrosshairEvent;
import de.htwg.seapal.events.map.RequestRedrawEvent;
import de.htwg.seapal.events.map.SetMarkerEvent;
import de.htwg.seapal.events.map.SetTargetEvent;
import de.htwg.seapal.events.map.TransitionToMarker;
import de.htwg.seapal.events.map.TransitionToTarget;
import de.htwg.seapal.events.map.aimdirectionmanager.DiscardTargetEvent;
import de.htwg.seapal.manager.mapstate.Statelike;
import roboguice.event.EventManager;
import roboguice.event.Observes;
import roboguice.inject.ContextSingleton;

/**
 * Created by jakub on 2/28/14.
 */
@ContextSingleton
public class DefaultState implements Statelike {


    public static final MarkerOptions CROSSHAIR_MARKER_OPTIONS = new MarkerOptions()
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.haircross))
            .anchor(0.5f, 0.5f);


    public static final MarkerOptions TARGET_MARKER_OPTIONS = new MarkerOptions()
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.target))
            .anchor(0.5f, 0.5f);

    public static final MarkerOptions MARKER_OPTIONS = new MarkerOptions()
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ann_mark))
            .anchor(0.5f, 0.5f);

    private Marker crosshairMarker;

    private Marker target;

    private Marker mark;

    private GoogleMap map;

    private Context context;
    @Inject
    private EventManager eventManager;


    @Override
    public void onSortPress(Context context, GoogleMap map, LatLng latlng) {
        this.context = context;
        this.map = map;
        setMarker(new SetMarkerEvent(latlng));

    }

    @Override
    public void onLongPress(Context context, GoogleMap map, LatLng latlng) {
        this.map = map;
        this.context = context;

        setCrosshairMarker(latlng);
        crosshairMarker.showInfoWindow();


    }

    private void setCrosshairMarker(LatLng latLng) {

        if (crosshairMarker != null) {
            crosshairMarker.remove();
        }

        crosshairMarker = map.addMarker(CROSSHAIR_MARKER_OPTIONS.position(latLng).snippet(latLng.toString()));

        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(100);
    }


    public void transitionToMarker(@Observes TransitionToMarker event) {
        Marker m = event.getMarker();
        if (m != null && crosshairMarker != null && crosshairMarker.equals(m)) {
            crosshairMarker.remove();
            crosshairMarker = null;
            if (mark != null) mark.remove();
            mark = map.addMarker(MARKER_OPTIONS.position(m.getPosition()));
        }

    }

    public void transitionToTarget(@Observes TransitionToTarget event) {
        Marker m = event.getMarker();
        if ((m != null && crosshairMarker != null && crosshairMarker.equals(m)) ||
                (m != null && mark != null && mark.equals(m)) ) {
            m.remove();
            if (m.equals(mark)) {
                mark = null;
            }
            if (m.equals(crosshairMarker)) {
                crosshairMarker = null;
            }
            if (target != null) {
                target.remove();
            }
            target = map.addMarker(TARGET_MARKER_OPTIONS.position(m.getPosition()));
            eventManager.fire(new SetTargetEvent(context, map, target));
        }
    }

    public void removeCrosshair(@Observes RemoveCrosshairEvent event) {
        if (crosshairMarker != null) {
            crosshairMarker.remove();
        }
    }

    public void setMarker(@Observes SetMarkerEvent event) {
        LatLng latLng = event.getPosition();
        if (mark != null) {
            mark.remove();
        }
        mark = map.addMarker(MARKER_OPTIONS.position(latLng));
    }

    public void redrawMarkers(@Observes RedrawMarkerEvent event) {
        map = event.getMap();
        if (mark != null) {
            LatLng markPosition = mark.getPosition();
            mark = map.addMarker(MARKER_OPTIONS.position(markPosition));
        }

        if (crosshairMarker != null) {
            LatLng crosshairMarkerPosition = crosshairMarker.getPosition();
            crosshairMarker = map.addMarker(CROSSHAIR_MARKER_OPTIONS.position(crosshairMarkerPosition));
        }
        if (target != null) {
            LatLng targetPosition = target.getPosition();
            target = map.addMarker(TARGET_MARKER_OPTIONS.position(targetPosition));

        }
    }

    public void markerDelete(@Observes MarkerDeleteEvent event) {
        Marker m = event.getMarker();
        if (m != null && m.equals(crosshairMarker)) {
            crosshairMarker.remove();
            crosshairMarker = null;
        }
        if (m != null && m.equals(target)) {
            target.remove();
            target = null;
            eventManager.fire(new DiscardTargetEvent());
        }
        if (m != null && m.equals(mark)) {
            mark.remove();
            mark = null;
        }

    }

    public void saveInstance(@Observes OnMapSaveInstanceEvent event) {
        Bundle outState = event.getOutBundle();
        if (crosshairMarker != null)
            outState.putParcelable("default_crosshair", crosshairMarker.getPosition());
        if (target != null)
            outState.putParcelable("default_target", target.getPosition());
        if (mark != null)
            outState.putParcelable("default_mark", mark.getPosition());

    }

    public void restoreInstance(@Observes OnMapRestoreInstanceEvent event) {
        Bundle savedInstance = event.getSavedInstance();
        map = event.getMap();
        LatLng crosshair =  savedInstance.getParcelable("default_crosshair");
        LatLng mark =  savedInstance.getParcelable("default_mark");
        LatLng target =  savedInstance.getParcelable("default_target");
        if (crosshair != null)
            crosshairMarker = map.addMarker(CROSSHAIR_MARKER_OPTIONS.position(crosshair));
        if (target != null)
            this.target = map.addMarker(TARGET_MARKER_OPTIONS.position(target));
        if (mark != null)
            this.mark = map.addMarker(MARKER_OPTIONS.position(mark));


    }

    public void redrawMarkers(@Observes RequestRedrawEvent event) {
        map = event.getMap();
        if (target != null)
            this.target = map.addMarker(TARGET_MARKER_OPTIONS.position(target.getPosition()));

        if (mark != null)
            this.mark = map.addMarker(MARKER_OPTIONS.position(mark.getPosition()));

        if (crosshairMarker != null)
            crosshairMarker = map.addMarker(CROSSHAIR_MARKER_OPTIONS.position(crosshairMarker.getPosition()));

    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
