package de.htwg.seapal.aview.gui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.activity.TripActivity;
import de.htwg.seapal.aview.gui.adapter.TripListAdapter;
import de.htwg.seapal.controller.impl.BoatController;
import de.htwg.seapal.controller.impl.TripController;
import de.htwg.seapal.model.IBoat;
import roboguice.fragment.RoboListFragment;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

/**
 * Created by jakub on 11/28/13.
 */
public class TripListFragment  extends RoboListFragment implements  AdapterView.OnItemLongClickListener, OnRefreshListener {

    private static final String ARG_POSITION = "postition";
    private static final String ARG_UUID = "uuid";
    public static final int SIMULATED_REFRESH_LENGTH = 5000;
    private List<UUID> tripList;
    @Inject
    private TripController tripController;
    @Inject
    private BoatController boatController;
    private int mCurrentPosition;
    private UUID mCurrentUUID;

    private PullToRefreshLayout mPullToRefreshLayout;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         ViewGroup viewGroup = (ViewGroup) view;

        // Now give the find the PullToRefreshLayout and set it up
        mPullToRefreshLayout = new PullToRefreshLayout(viewGroup.getContext());

        ActionBarPullToRefresh.from(getActivity())
                .insertLayoutInto(viewGroup)
                .theseChildrenArePullable(android.R.id.list, android.R.id.empty)
                .listener(this)
                .setup(mPullToRefreshLayout);


    }

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

        this.getListView().setOnItemLongClickListener(this);



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

    public void deleteTrip(int listPosition)  {
        TripListAdapter listAdapter = (TripListAdapter) getListView().getAdapter();
        UUID uuid = listAdapter.getItem(listPosition);
        tripController.deleteTrip(uuid);
        listAdapter.remove(uuid);
        notifyListAdapter();
        Toast.makeText(getActivity(), "Trip deleted",
                Toast.LENGTH_SHORT).show();

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

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        DeleteTripDialog d = new DeleteTripDialog(i);
        d.show(getFragmentManager(), "TripListFragment");
        return true;
    }


    @Override
    public void onRefreshStarted(View view) {

        Log.i("TripListFragment", "onRefreshStarted");


        /**
         * Simulate Refresh with 4 seconds sleep
         */
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(SIMULATED_REFRESH_LENGTH);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                UUID trip =  tripController.newTrip(mCurrentUUID);
                tripList.add(trip);

                Intent intent = new Intent(getActivity(),
                        TripActivity.class);
                intent.putExtra("trip", trip.toString());
                getActivity().startActivity(intent);
                mPullToRefreshLayout.setRefreshComplete();
                notifyListAdapter();

            }

        }.execute();

    }


    public class DeleteTripDialog extends DialogFragment {

        private int mPosition;

        public DeleteTripDialog(int position) {
            mPosition = position;

        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.delete_trip)
                   .setPositiveButton(R.string.delete_trip_yes, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           deleteTrip(mPosition);
                       }
                   })
                   .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           // User cancelled the dialog
                           dialog.cancel();
                       }
                   });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }






}

