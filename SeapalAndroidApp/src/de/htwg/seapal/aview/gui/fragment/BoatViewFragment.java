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
import de.htwg.seapal.events.boat.OnBoatSelected;
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
    public final static String ARG_POSITION = "position";
    public final static String ARG_UUID = "uuid";

    private int mCurrentPosition = -1;
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
        sessionManager.addListener(new SessionManager.OnLogOutListener() {
            @Override
            public void onLogout() {
                mCurrentPosition = -1;
                mCurrentUUID = null;
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
            mCurrentUUID = (UUID) savedInstanceState.get(ARG_UUID);
        }
        return inflater.inflate(R.layout.boat_view, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            eventManager.fire(new OnBoatSelected(args.getInt(ARG_POSITION), (UUID) args.get(ARG_UUID)));
        } else {
            eventManager.fire(new OnBoatSelected(mCurrentPosition, mCurrentUUID));
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mCurrentPosition);
        outState.putSerializable(ARG_UUID, mCurrentUUID);
    }

    public void updateBoatView(@Observes OnUpdateBoatView selected) {
        if (selected.getBoatUUID() != null) {
            Collection<Boat> boats = (Collection<Boat>) mainController.getSingleDocument("boat", sessionManager.getSession(), selected.getBoatUUID());
            if (boats != null &&!boats.isEmpty() && boats.iterator().hasNext()) {
                IBoat b = boats.iterator().next();
                if (b != null) {
                    BoatUtils.setViewFromBoat(getActivity(), b);

                    mCurrentPosition = selected.getPosition();
                    mCurrentUUID = selected.getBoatUUID();
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
