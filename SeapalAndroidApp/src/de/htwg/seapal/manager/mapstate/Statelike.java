package de.htwg.seapal.manager.mapstate;

import android.content.Context;
import android.os.Parcelable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by jakub on 2/28/14.
 */
public interface Statelike extends Parcelable {


    void onSortPress(Context context, GoogleMap map, LatLng latlng);

    void onLongPress(Context context, GoogleMap map, LatLng latlng);

}
