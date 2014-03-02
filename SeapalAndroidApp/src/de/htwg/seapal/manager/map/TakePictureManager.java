package de.htwg.seapal.manager.map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import de.htwg.seapal.R;
import de.htwg.seapal.events.map.picturemanager.PlacePictureOnMapEvent;
import de.htwg.seapal.events.map.picturemanager.RequestTakePictureEvent;
import de.htwg.seapal.events.map.picturemanager.ShowPictureDialogEvent;
import roboguice.event.Observes;
import roboguice.inject.ContextSingleton;

/**
 * Created by jakub on 2/28/14.
 */
@ContextSingleton
public class TakePictureManager {

    private Marker pictureMarker;
    public static final int REQUEST_IMAGE_CAPTURE = 1;


    public void drawPicture(@Observes PlacePictureOnMapEvent event) {
        Intent data = event.getData();
        GoogleMap map = event.getMap();


        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");

        Location loc = map.getMyLocation();
        LatLng coor = new LatLng(loc.getLatitude(), loc.getLongitude());

        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(imageBitmap.getWidth() + 10, imageBitmap.getHeight() + 10, conf);
        Canvas canvas1 = new Canvas(bmp);

        Paint color = new Paint();
        color.setColor(Color.BLUE);

        //modify canvas
        canvas1.drawColor(color.getColor());
        canvas1.drawBitmap(imageBitmap, 5, 5, color);

        //add marker to Map
        if (pictureMarker != null)
            pictureMarker.remove();

        pictureMarker = map.addMarker(new MarkerOptions().position(coor).icon(BitmapDescriptorFactory.fromBitmap(bmp)));

    }

    public void showPictureDialog(@Observes ShowPictureDialogEvent event) {
        Context context = event.getContext();

        // Get the layout inflater
        LayoutInflater inflater = LayoutInflater.from(context);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(inflater.inflate(R.layout.picture_fragment, null));
        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                pictureMarker.remove();
            }
        });

        builder.create().show();
    }

    private void dispatchTakePictureIntent(@Observes RequestTakePictureEvent event) {
        Activity context = (Activity) event.getContext();

        if (isPictureIntentAvailable(context)) {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (takePictureIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivityFromChild(context, takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private static boolean isPictureIntentAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> list =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
