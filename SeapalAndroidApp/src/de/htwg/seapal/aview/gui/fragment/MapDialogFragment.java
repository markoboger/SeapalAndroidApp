package de.htwg.seapal.aview.gui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.text.DecimalFormat;

import de.htwg.seapal.R;

public class MapDialogFragment extends DialogFragment {

    private final Marker marker;

    public MapDialogFragment(Marker marker) {
        this.marker = marker;
    }

    public interface MapDialogListener {
        public void onDialogSetMarkClick(DialogFragment dialog, Marker m);

        public void onDialogSetRouteClick(DialogFragment dialog, Marker m);

        public void onDialogcalcDistanceClick(DialogFragment dialog, Marker m);

        public void onDialogSetTargetClick(DialogFragment dialog, Marker m);

        public void onDialogDeleteClick(DialogFragment dialog, Marker m);
    }

	private MapDialogListener mListener;

	// Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the NoticeDialogListener so we can send events to the host
			mListener = (MapDialogListener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString()
					+ " must implement MapDialogListener");
		}
	}

    @Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {


		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.map_menu, null);
		View titleView = inflater.inflate(R.layout.map_menu_title, null);

        ImageButton setMark = (ImageButton) dialogView.findViewById(R.id.setMark);
        ImageButton setRoute = (ImageButton) dialogView.findViewById(R.id.setRoute);
        ImageButton calcDistance = (ImageButton) dialogView.findViewById(R.id.calcDistance);
        ImageButton setTarget = (ImageButton) dialogView.findViewById(R.id.makeFinish);
        ImageButton delete = (ImageButton) dialogView.findViewById(R.id.delete);


		TextView t = (TextView) titleView.findViewById(R.id.menuTitleLabel);

        LatLng pos = marker.getPosition();
        String lat = formatLatitude(pos.latitude);
        String lng = formatLongitude(pos.longitude);
        t.setText(lat + "       " + lng);
        builder.setCustomTitle(titleView).setView(dialogView);

		setMark.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                mListener.onDialogSetMarkClick(MapDialogFragment.this, marker);
            }
        });

		setRoute.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                mListener.onDialogSetRouteClick(MapDialogFragment.this, marker);
            }
        });

		calcDistance.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                mListener.onDialogcalcDistanceClick(MapDialogFragment.this, marker);
            }
        });

		setTarget.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                mListener.onDialogSetTargetClick(MapDialogFragment.this, marker);
            }
        });

		delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                mListener.onDialogDeleteClick(MapDialogFragment.this, marker);
            }
        });


		return builder.create();
	}

	String formatLongitude(double lng) {
		String orientation;
		DecimalFormat df= new DecimalFormat("#0");
		if (lng >= 0)
			orientation = "E";
		else
			orientation = "W";
		lng = Math.abs(lng);
		String degrees = df.format(lng);
		lng -= Double.parseDouble(degrees);
		String minutes = df.format(Math.abs(((lng * 60) % 60)));
		return degrees + "°" + minutes + "'" + orientation;
	}

	String formatLatitude(double lat) {
		String orientation;
		DecimalFormat df= new DecimalFormat("#0");
		if (lat >= 0)
			orientation = "N";
		else
			orientation = "S";
		lat = Math.abs(lat);
		String degrees = df.format(lat);
		String minutes = df.format(Math.abs(((lat * 60) % 60)));
		return degrees + "°" + minutes + "'" + orientation;

	}
}
