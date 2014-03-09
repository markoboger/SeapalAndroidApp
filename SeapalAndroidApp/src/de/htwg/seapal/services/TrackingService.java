package de.htwg.seapal.services;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.inject.Inject;

import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.activity.MapActivity;
import de.htwg.seapal.controller.IMainController;
import de.htwg.seapal.manager.SessionManager;
import de.htwg.seapal.model.impl.Trip;
import de.htwg.seapal.model.impl.Waypoint;
import roboguice.service.RoboService;

/**
 * Created by jakub on 12/17/13.
 */
public class TrackingService extends RoboService implements LocationListener {

    public static final String TRIP_UUID = "trip_uuid";
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 100;
    public static final String TRIP_MAP = "trip_map";
    public static final String WAYPOINT_BROADCAST_RECEIVER = "broadcast_receiver";
    public static final String LAT_LNG = "trip_lat_lng";
    public static final String BOAT_UUID = "boat_uuid";
    public static final String TRIP = "trip_object";
    // Declaring a Location Manager
    protected LocationManager locationManager;
    private Context mContext;
    // flag for GPS status
    private boolean isGPSEnabled = false;
    // flag for network status
    private boolean isNetworkEnabled = false;
    // flag for GPS status
    private boolean canGetLocation = false;
    @Inject
    private IMainController mainController;

    @Inject
    private SessionManager sessionManager;

    private UUID mTrip;
    private UUID mBoat;
    private Location location; // location
    private double latitude; // latitude
    private double longitude; // longitude
    private NotificationManager mNotificationManager;
    private int NOTIFICATION = R.string.tracking_service_started_text;
    private IBinder mBinder = new TrackingServiceBinder();
    private Trip mTripObject;


    private void startGps() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                showSettingsAlert();
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(TrackingService.this);
        }
    }

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        Waypoint wp = new Waypoint();
        wp.setDate(System.currentTimeMillis());
        wp.setLatitude(location.getLatitude());
        wp.setLongitude(location.getLongitude());
        wp.setTrip(mTrip.toString());
        wp.setBoat(mBoat.toString());
        mainController.creatDocument("waypoint",wp, sessionManager.getSession());

        Intent intent = new Intent(WAYPOINT_BROADCAST_RECEIVER);
        intent.putExtra(LAT_LNG, new LatLng(location.getLatitude(), location.getLongitude()));
        sendBroadcast(intent);


    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onStart(Intent intent, int startId) {
        String tripString = intent.getStringExtra(TRIP_UUID);
        String boatString = intent.getStringExtra(BOAT_UUID);
        Bundle b = intent.getExtras();
        Trip trip = (Trip) b.get(TRIP);


        mTrip = UUID.fromString(tripString);
        mTripObject = trip;
        mBoat = UUID.fromString(boatString);
        mContext = getApplicationContext();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        startGps();
        showNotification();
        super.onStart(intent, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new TrackingServiceBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopUsingGPS();
        mNotificationManager.cancel(NOTIFICATION);
    }


    @Override
    public boolean stopService(Intent name) {
        stopUsingGPS();
        mNotificationManager.cancel(NOTIFICATION);
        return super.stopService(name);
    }

    private void showNotification() {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = getText(NOTIFICATION);
        Intent resultIntent = new Intent(this, MapActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        // Adds the back stack
        stackBuilder.addParentStack(MapActivity.class);
        // Adds the Intent to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        // Gets a PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(mContext)
                .setContentTitle(String.format("%s for Trip %s",text, mTripObject.getName()))
                .setContentText(String.format("From %s to %s",mTripObject.getFrom(), mTripObject.getTo()))
                .setSmallIcon(R.drawable.seapal_launcher)
                .setAutoCancel(false)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.seapal_launcher))
                .build();

        // Send the notification.
        mNotificationManager.notify(NOTIFICATION, notification);
    }

    public class TrackingServiceBinder extends Binder {
        TrackingService getService() {
            return TrackingService.this;
        }
    }


}
