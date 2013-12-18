package de.htwg.seapal.aview.gui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.htwg.seapal.R;

/**
 * Created by jakub on 12/10/13.
 */
public class LogbookFragment extends Fragment {

    private View logbookView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (logbookView == null)
            logbookView  = inflater.inflate(R.layout.logbook_main_view, container, false);
        else
            Log.i("LogbookFragment", "layout inflated");

        return logbookView;
    }
}
