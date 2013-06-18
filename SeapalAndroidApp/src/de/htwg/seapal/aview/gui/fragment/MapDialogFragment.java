package de.htwg.seapal.aview.gui.fragment;

import java.text.DecimalFormat;

import com.google.android.gms.maps.model.LatLng;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.activity.MapActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MapDialogFragment extends DialogFragment {

	public interface MapDialogListener {
		public void onDialogSetMarkClick(DialogFragment dialog);
		public void onDialogSetRouteClick(DialogFragment dialog);
		public void onDialogcalcDistanceClick(DialogFragment dialog);
		public void onDialogSetTargetClick(DialogFragment dialog);
		public void onDialogDeleteClick(DialogFragment dialog);
	}

	MapDialogListener mListener;

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

	private ImageButton setMark, setRoute, calcDistance, setTarget, delete;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.map_menu, null);
		View titleView = inflater.inflate(R.layout.map_menu_title, null);

		setMark = (ImageButton) dialogView.findViewById(R.id.setMark);
		setRoute = (ImageButton)dialogView.findViewById(R.id.setRoute);
		calcDistance = (ImageButton)dialogView.findViewById(R.id.calcDistance);
		setTarget = (ImageButton)dialogView.findViewById(R.id.makeFinish);
		delete = (ImageButton)dialogView.findViewById(R.id.delete);


		TextView t = (TextView) titleView.findViewById(R.id.menuTitleLabel);

		LatLng pos = MapActivity.crosshairMarker.getPosition();
		String lat = formatLatitude(pos.latitude);
		String lng = formatLongitude(pos.longitude);
		t.setText(lat + "       " +  lng);
		builder.setCustomTitle(titleView).setView(dialogView);

		setMark.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				mListener.onDialogSetMarkClick(MapDialogFragment.this);
			}
		});

		setRoute.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				mListener.onDialogSetRouteClick(MapDialogFragment.this);
			}
		});

		calcDistance.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				mListener.onDialogcalcDistanceClick(MapDialogFragment.this);
			}
		});

		setTarget.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				mListener.onDialogSetTargetClick(MapDialogFragment.this);
			}
		});

		delete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
				mListener.onDialogDeleteClick(MapDialogFragment.this);
			}
		});


		return builder.create();
	}

	public String formatLongitude(double lng) {
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

	public String formatLatitude(double lat) {
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
