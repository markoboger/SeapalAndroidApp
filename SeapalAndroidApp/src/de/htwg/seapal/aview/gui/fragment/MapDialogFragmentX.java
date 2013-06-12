package de.htwg.seapal.aview.gui.fragment;


import de.htwg.seapal.R;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageButton;

public class MapDialogFragmentX extends DialogFragment {


	public MapDialogFragmentX() {
		// Empty constructor required for DialogFragment
	}

//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
		//		View view = inflater.inflate(R.layout.map_menu, container);
		//		ImageButton b1 = (ImageButton) view.findViewById(R.id.setMark);
		//		getDialog().setTitle("Hello");
		//
		//		return view;
//	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		Context mctx = getActivity().getApplicationContext();
		Dialog m_dialog = new Dialog(mctx, R.style.Dialog_No_Border);
		LayoutInflater m_inflater = getActivity().getLayoutInflater();
		View v = LayoutInflater.from(mctx).inflate(R.layout.map_menu, null, false);
		// SET ALL THE VIEWS
		m_dialog.setTitle(null);
		m_dialog.setContentView(v);
		m_dialog.show();
		
		return m_dialog;
	}



	//	@Override
	//	public Dialog onCreateDialog(Bundle savedInstanceState) {
	//		
	//	
	////
	////
	////		// Use the Builder class for convenient dialog construction
	////		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	////
	////		//		builder.setTitle("MENU");
	////		//		builder.setPositiveButton("YEAH", null);
	////		//		builder.setNegativeButton("Nope", null);
	////
	////		LayoutInflater inflater = getActivity().getLayoutInflater();
	////
	////		// Inflate and set the layout for the dialog
	////		// Pass null as the parent view because its going in the dialog layout
	////
	////		builder.setView(inflater.inflate(R.layout.map_menu, null));
	////		setStyle(android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, 0);
	////
	////
	////		// Create the AlertDialog object and return it
	////		return builder.create();
	//	}

}
