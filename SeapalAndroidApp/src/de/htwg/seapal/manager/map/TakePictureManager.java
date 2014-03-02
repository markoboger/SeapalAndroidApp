package de.htwg.seapal.manager.map;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

import roboguice.inject.ContextSingleton;

/**
 * Created by jakub on 2/28/14.
 */
@ContextSingleton
public class TakePictureManager {

    private LatLng pictureMarker;
    private Uri fileUri;
    private String mCurrentPhotoPath;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
}
