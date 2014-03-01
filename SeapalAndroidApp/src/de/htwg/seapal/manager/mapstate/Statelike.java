package de.htwg.seapal.manager.mapstate;

import android.content.Context;
import android.os.Parcelable;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by jakub on 2/28/14.
 */
public interface Statelike extends Parcelable {


    void doAction(Context context, GoogleMap map, LatLng latlng);

}
