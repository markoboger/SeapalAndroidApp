package de.htwg.seapal.manager.mapstate.impl;

import android.content.Context;
import android.os.Parcel;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import de.htwg.seapal.manager.mapstate.Statelike;

/**
 * Created by jakub on 2/28/14.
 */
public class MarkState implements Statelike {
    @Override
    public void onSortPress(Context context, GoogleMap map, LatLng latlng) {

    }

    @Override
    public void onLongPress(Context context, GoogleMap map, LatLng latlng) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
