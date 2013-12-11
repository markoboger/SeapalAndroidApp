package de.htwg.seapal.aview.gui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.htwg.seapal.R;
import roboguice.fragment.RoboFragment;

/**
 * Created by jakub on 12/10/13.
 */
public class LogbookFragment extends Fragment {

    private BoatListFragment mBoatListFragment;
    private LogbookSlideFragment mSlideFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logbook_main_view, container, false);
        return view;
    }
}
