package de.htwg.seapal.aview.gui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;

import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.controller.impl.BoatController;
import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.utils.seapal.BoatUtils;
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
    private BoatController boatController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            updateBoatView(args.getInt(ARG_POSITION), (UUID) args.get(ARG_UUID));
        } else {
            updateBoatView(mCurrentPosition, mCurrentUUID);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mCurrentPosition);
        outState.putSerializable(ARG_UUID, mCurrentUUID);
    }

    public void updateBoatView(int position, UUID uuid) {
        if (uuid != null) {
            IBoat b = boatController.getBoat(uuid);
            if (b != null) {
                BoatUtils.setViewFromBoat(getActivity(), b);

                mCurrentPosition = position;
                mCurrentUUID = uuid;
            }
        }
    }

    public IBoat getBoatFromCurrentView() {
        IBoat boat = boatController.getBoat(mCurrentUUID);
        return BoatUtils.convertViewToBoat(getView(), boat);
    }
}
