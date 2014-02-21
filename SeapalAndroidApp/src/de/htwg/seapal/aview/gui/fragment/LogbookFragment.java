package de.htwg.seapal.aview.gui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;
import com.google.inject.Injector;

import de.htwg.seapal.R;
import roboguice.RoboGuice;

/**
 * Created by jakub on 12/10/13.
 */
public class LogbookFragment extends Fragment {

    @Inject
    private FragmentManager fragmentManager;


    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.logbook_main_view, container, false);
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector i = RoboGuice.getInjector(getActivity());
        i.injectMembers(this);


        fragmentManager.beginTransaction()
                .replace(R.id.logbook_slide_fragment, new LogbookSlideFragment())
                .replace(R.id.boat_list_fragment, new BoatListFragment()).commit();
    }
}
