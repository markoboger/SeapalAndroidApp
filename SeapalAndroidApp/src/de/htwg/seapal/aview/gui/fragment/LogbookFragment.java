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
import de.htwg.seapal.events.session.LogOutEvent;
import roboguice.RoboGuice;
import roboguice.event.Observes;

/**
 * Created by jakub on 12/10/13.
 */
public class LogbookFragment extends Fragment {

    @Inject
    private FragmentManager fragmentManager;


    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.logbook_main_view, container, false);
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector i = RoboGuice.getInjector(getActivity());
        i.injectMembers(this);

    }

    public void onLogout(@Observes LogOutEvent e) {
        if (view != null) {
            ViewGroup v = (ViewGroup) view.getParent();
            v.removeView(view);
            view = null;
        }

    }
}
