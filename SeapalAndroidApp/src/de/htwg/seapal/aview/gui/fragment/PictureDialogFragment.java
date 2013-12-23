package de.htwg.seapal.aview.gui.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.htwg.seapal.R;

public class PictureDialogFragment extends DialogFragment {

    private ImageView imageView;
    private TextView pictureDescription;

    public PictureDialogFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.picture_fragment, container);
        pictureDescription = (TextView) view.findViewById(R.id.taken_picture_description_TextView);
        getDialog().setTitle("Picture");

        return view;
    }



}
