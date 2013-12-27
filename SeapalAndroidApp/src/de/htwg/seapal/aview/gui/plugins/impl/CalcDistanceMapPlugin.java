package de.htwg.seapal.aview.gui.plugins.impl;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.plugins.IMapPlugin;

/**
 * Created by jakub on 12/27/13.
 */
public class CalcDistanceMapPlugin implements IMapPlugin<LatLng> {

    private static final String ORANGE = "#FFBB03";
    private MarkerOptions calcDistanceMarkerOptions;

    private PolylineOptions calcDistancePolylineOptions;
    private GoogleMap mMap;

    public CalcDistanceMapPlugin(GoogleMap map) {
        mMap = map;
        calcDistanceMarkerOptions = new MarkerOptions()
            .anchor(0.25f, 1.0f - 0.08333f)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ann_distance));

        calcDistancePolylineOptions = new PolylineOptions()
            .width(5)
            .color(Color.parseColor(ORANGE));
    }

    @Override
    public void doAction(LatLng latLng) {

    }
}
