package de.htwg.seapal.aview.gui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;

import java.util.Collection;
import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.controller.IMainController;
import de.htwg.seapal.events.boat.OnUpdateBoatView;
import de.htwg.seapal.events.boat.RequestBoatViewInformation;
import de.htwg.seapal.manager.SessionManager;
import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.model.impl.Boat;
import de.htwg.seapal.utils.seapal.BoatUtils;
import roboguice.event.EventManager;
import roboguice.event.Observes;
import roboguice.fragment.RoboFragment;


/**
 * Created by jakub on 11/16/13.
 */
public class BoatViewFragment extends RoboFragment {

    public final static String ARG_UUID = "uuid";

    private UUID mCurrentUUID;

    @Inject
    private IMainController mainController;

    @Inject
    private SessionManager sessionManager;
    @Inject
    private EventManager eventManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentUUID = (UUID) savedInstanceState.get(ARG_UUID);
        }
        return inflater.inflate(R.layout.boat_view, container, false);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_UUID, mCurrentUUID);
    }

    public void updateBoatView(@Observes OnUpdateBoatView selected) {
        if (selected.getBoat().getUUID() != null) {
            Collection<Boat> boats = (Collection<Boat>) mainController.getSingleDocument("boat", selected.getBoat().getAccount(), selected.getBoat().getUUID());
            if (boats != null &&!boats.isEmpty() && boats.iterator().hasNext()) {
                IBoat b = boats.iterator().next();
                if (b != null) {
                    BoatUtils.setViewFromBoat(getActivity(), b);
                    mCurrentUUID = selected.getBoat().getUUID();
                }
            }
        }
    }

    public void requestViewInformation(@Observes RequestBoatViewInformation viewInformation) {
        Collection<Boat> boats = (Collection<Boat>) mainController.getSingleDocument("boat", sessionManager.getSession(), mCurrentUUID);
        if (!boats.isEmpty() && boats.iterator().hasNext()) {
            IBoat b = boats.iterator().next();
            if (b != null) {
                viewInformation.setBoat(BoatUtils.convertViewToBoat(getView(), b));
            }
        }
    }




}

