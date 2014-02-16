package de.htwg.seapal.aview.gui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;
import com.google.inject.Injector;

import de.htwg.seapal.R;
import de.htwg.seapal.controller.IMainController;
import roboguice.RoboGuice;

/**
 * Created by jakub on 12/10/13.
 */
public class CrewFragment extends Fragment {

    @Inject
    private IMainController controller;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // cuz this uses Fragment and not Robofragment, cuz Roboguice has no support for roboActionbar
        // so its a mixin with support fragments and android fragments.
        Injector i = RoboGuice.getInjector(getActivity());
        i.injectMembers(this);

        assert controller != null;

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
        }
        return inflater.inflate(R.layout.crew_view, container, false);
    }
}
