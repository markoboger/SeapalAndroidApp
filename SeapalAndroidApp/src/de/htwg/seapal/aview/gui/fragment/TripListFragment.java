package de.htwg.seapal.aview.gui.fragment;

import android.content.Intent;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.inject.Inject;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.activity.TripActivity;
import de.htwg.seapal.aview.gui.activity.TripListActivity;
import de.htwg.seapal.aview.gui.adapter.TripListAdapter;
import de.htwg.seapal.controller.impl.BoatController;
import de.htwg.seapal.controller.impl.TripController;
import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.model.ITrip;
import roboguice.fragment.RoboFragment;
import roboguice.fragment.RoboListFragment;

/**
 * Created by jakub on 11/28/13.
 */
public class TripListFragment  extends RoboListFragment {

    private static final String ARG_POSITION = "postition";
    private static final String ARG_UUID = "uuid";
    private List<UUID> tripList;
    @Inject
    private TripController tripController;
    @Inject
    private BoatController boatController;
    private int mCurrentPosition;
    private UUID mCurrentUUID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
            mCurrentUUID = (UUID) savedInstanceState.get(ARG_UUID);
        }


        tripList = new LinkedList<UUID>();
        TripListAdapter adapter = new TripListAdapter(getActivity(), tripList, tripController);
        setListAdapter(adapter);


    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
            updateTripView(args.getInt(ARG_POSITION), (UUID) args.get(ARG_UUID));
        } else {
            updateTripView(mCurrentPosition, mCurrentUUID);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARG_POSITION, mCurrentPosition);
        outState.putSerializable(ARG_UUID, mCurrentUUID);
    }

    public void updateTripView(int position, UUID uuid) {
        if (uuid != null) {
            IBoat b = boatController.getBoat(uuid);
            if (b != null) {
                tripList.clear();
                tripList.addAll(tripController.getTrips(b.getUUID()));
                notifyListAdapter();
                mCurrentPosition = position;
                mCurrentUUID = uuid;
            }
        }

    }

    private void notifyListAdapter() {
        TripListAdapter listAdapter = (TripListAdapter) getListAdapter();
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(),
                TripActivity.class);
        UUID trip = (UUID) l.getAdapter().getItem(position);
        intent.putExtra("trip", trip.toString());
        getActivity().startActivity(intent);
    }
}

