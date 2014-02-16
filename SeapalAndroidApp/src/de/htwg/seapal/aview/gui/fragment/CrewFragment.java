package de.htwg.seapal.aview.gui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.htwg.seapal.R;

/**
 * Created by jakub on 12/10/13.
 */
public class CrewFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
        }
        return inflater.inflate(R.layout.crew_view, container, false);
    }
}
