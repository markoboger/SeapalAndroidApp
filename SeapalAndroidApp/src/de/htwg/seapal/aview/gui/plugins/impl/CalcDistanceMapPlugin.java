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
import de.htwg.seapal.aview.gui.plugins.IMapPlugin;

/**
 * Created by jakub on 12/27/13.
 */
@Deprecated
public class CalcDistanceMapPlugin implements IMapPlugin<LatLng, Double> {

    private static final String ORANGE = "#FFBB03";
    private MarkerOptions calcDistanceMarkerOptions;

    private List<Marker> calcDistanceMarker;

    private PolylineOptions calcDistancePolylineOptions;

    private LatLng mLastPos;

    private double calcDistance;

    private Polyline calcDistanceRoute;

    public CalcDistanceMapPlugin(GoogleMap map, LatLng firstCord) {
        calcDistanceMarkerOptions = new MarkerOptions()
            .anchor(0.25f, 1.0f - 0.08333f)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ann_distance));

        calcDistancePolylineOptions = new PolylineOptions()
            .width(5)
            .color(Color.parseColor(ORANGE));

        calcDistanceMarker = new LinkedList<Marker>();

        mLastPos = firstCord;

        calcDistance = 0.0;
        calcDistanceMarker.add(map.addMarker(calcDistanceMarkerOptions.position(mLastPos)));
        calcDistanceRoute = map.addPolyline(calcDistancePolylineOptions.add(mLastPos));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double doAction(GoogleMap mMap, LatLng latLng) {
        calcDistance += calcDistance(mLastPos, latLng);
        mLastPos = latLng;
        List<LatLng> calcDistanceRoutePoints = calcDistanceRoute.getPoints();
        calcDistanceRoutePoints.add(latLng);
        calcDistanceMarker.add(mMap.addMarker(calcDistanceMarkerOptions.position(latLng)));
        calcDistanceRoute.setPoints(calcDistanceRoutePoints);

        return calcDistance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void redraw(GoogleMap mMap) {
        for (Marker m : calcDistanceMarker) {
            mMap.addMarker(calcDistanceMarkerOptions.position(m.getPosition()));
        }
        List<LatLng> calcDistanceRoutePoints = calcDistanceRoute.getPoints();
        calcDistanceRoute = mMap.addPolyline(calcDistancePolylineOptions);
        calcDistanceRoute.setPoints(calcDistanceRoutePoints);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        calcDistanceMarker.clear();
        calcDistanceRoute.remove();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void zoomTo(GoogleMap map) {

    }

    /**
     * calculates the Distance of currentPosition and distantPosition
     *
     * @param currentPosition latlng of the current position
     * @param distantPosition latlnt of the distant position
     * @return the distance in kilometers
     */
    private Double calcDistance(LatLng currentPosition, LatLng distantPosition) {

        int R = 6371; // km earth radius
        double dLat = Math.toRadians(distantPosition.latitude - currentPosition.latitude);
        double dLon = Math.toRadians(distantPosition.longitude - currentPosition.longitude);
        double lat1 = Math.toRadians(currentPosition.latitude);
        double lat2 = Math.toRadians(distantPosition.latitude);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
