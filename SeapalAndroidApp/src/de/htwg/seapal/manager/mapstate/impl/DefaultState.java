package de.htwg.seapal.manager.mapstate.impl;

import android.content.Context;
import android.os.Parcel;
import android.os.Vibrator;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.inject.Inject;

import de.htwg.seapal.R;
import de.htwg.seapal.events.map.RemoveCrosshairEvent;
import de.htwg.seapal.events.map.SetTargetEvent;
import de.htwg.seapal.events.map.TransitionToMarker;
import de.htwg.seapal.events.map.TransitionToTarget;
import de.htwg.seapal.manager.mapstate.Statelike;
import roboguice.event.EventManager;
import roboguice.event.Observes;

/**
 * Created by jakub on 2/28/14.
 */
public class DefaultState implements Statelike {

    public static final MarkerOptions CROSSHAIR_MARKER_OPTIONS = new MarkerOptions()
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.haircross))
            .anchor(0.5f, 0.5f);


    public static final MarkerOptions TARGET_MARKER_OPTIONS = new MarkerOptions()
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.target))
            .anchor(0.5f, 0.5f);

    public static final MarkerOptions  MARKER_OPTIONS= new MarkerOptions();

    private Marker crosshairMarker;

    private GoogleMap map;

    private Context context;
    @Inject
    private EventManager eventManager;


    @Override
    public void onSortPress(Context context, GoogleMap map, LatLng latlng) {


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
            map.addMarker(MARKER_OPTIONS.position(m.getPosition()));
        }

    }

    public void transitionToTarget(@Observes TransitionToTarget event) {
        Marker m = event.getMarker();
        if (m != null && crosshairMarker != null && crosshairMarker.equals(m)) {
            crosshairMarker.remove();
            crosshairMarker = null;
            eventManager.fire(new SetTargetEvent(context, map, map.addMarker(TARGET_MARKER_OPTIONS.position(m.getPosition()))));
        }
    }

    public void removeCrosshair(@Observes RemoveCrosshairEvent event) {
        if (crosshairMarker != null) {
            crosshairMarker.remove();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
