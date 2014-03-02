package de.htwg.seapal.manager.map;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.inject.Inject;

import java.util.List;

import de.htwg.seapal.R;
import de.htwg.seapal.events.map.SetTargetEvent;
import de.htwg.seapal.events.map.aimdirectionmanager.DiscardTargetEvent;
import de.htwg.seapal.events.map.aimdirectionmanager.InitializeAimDirectionEvent;
import roboguice.event.Observes;
import roboguice.inject.ContextSingleton;

import static java.lang.Math.asin;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.toDegrees;

/**
 * Created by jakub on 3/2/14.
 */
@ContextSingleton
public class AimDirectionManager {

    private Marker movingDirectionMarker;
    private Marker aimDirectionArrow;
    private GoogleMap map;
    private Location oldLocation;

    private Marker crosshairMarker;

    @Inject
    private Context context;

    public void discardTarget(@Observes DiscardTargetEvent event) {
        // Discard Target
        if (aimDirectionArrow != null) {
            aimDirectionArrow.remove();
            aimDirectionArrow = null;
        }

    }

    public void initializeAimDirection(@Observes InitializeAimDirectionEvent event) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = manager.getProviders(false);
        map = event.getMap();

        final LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                if (oldLocation != null) {
                    float angle = calualteArrowDirection(oldLocation, location);
                    showMovingDirection(angle);
                }
                oldLocation = location;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        manager.requestLocationUpdates(providers.get(1), 0, 0, locationListener);
    }


    public void crosshairChangeListener(@Observes SetTargetEvent event) {
        map = event.getMap();
        context = event.getContext();
        crosshairMarker = event.getCrosshairMarker();
        drawAimArrow();

    }

    private void drawAimArrow() {

        Location targetPos = new Location("Target");
        targetPos.setLatitude(crosshairMarker.getPosition().latitude);
        targetPos.setLongitude(crosshairMarker.getPosition().longitude);

        Location myPos = map.getMyLocation();

        float angle = calualteArrowDirection(myPos, targetPos);

        showTargetDirection(angle, targetPos);

    }

    private void showMovingDirection(float direction) {

        if (movingDirectionMarker != null) {
            movingDirectionMarker.remove();
        }

        if (map.getMyLocation() != null) {

            LatLng position = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());

            movingDirectionMarker = map.addMarker(new MarkerOptions()
                    .position(position)
                    .rotation(direction)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.moving_direction))
            );
        }
    }

    private void showTargetDirection(float direction, Location target) {

        if (aimDirectionArrow != null) {
            aimDirectionArrow.remove();
        }


        if (map.getMyLocation() != null) {

            LatLng position = new LatLng(map.getMyLocation().getLatitude(), map.getMyLocation().getLongitude());

            aimDirectionArrow = map.addMarker(new MarkerOptions()
                    .position(position)
                    .rotation(direction)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.aim_direction))
            );
        }
    }

    private float calualteArrowDirection(Location location, Location markerPos) {

        double hypo = sqrt(pow(markerPos.getLongitude() - location.getLongitude(), 2) + pow(markerPos.getLatitude() - location.getLatitude(), 2));
        float sin = (float) asin((markerPos.getLongitude() - location.getLongitude()) / hypo);

        float angle = (float) toDegrees(sin);

        angle = markerPos.getLatitude() < location.getLatitude() ? 180 - angle : angle;

        return angle;
    }
}
