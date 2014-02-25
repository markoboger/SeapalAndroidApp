package de.htwg.seapal.aview.gui.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.inject.Inject;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.events.trip.OnUpdateTripListEvent;
import de.htwg.seapal.manager.SessionManager;
import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.activity.TripActivity;
import de.htwg.seapal.aview.gui.adapter.TripListAdapter;
import de.htwg.seapal.controller.IMainController;
import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.model.IModel;
import de.htwg.seapal.model.impl.Boat;
import de.htwg.seapal.model.impl.Trip;
import roboguice.event.EventManager;
import roboguice.event.Observes;
import roboguice.fragment.RoboListFragment;

/**
 * Created by jakub on 11/28/13.
 */
public class TripListFragment  extends RoboListFragment implements  AdapterView.OnItemLongClickListener {

    private static final String ARG_POSITION = "postition";
    private static final String ARG_UUID = "uuid";
    public static final int SIMULATED_REFRESH_LENGTH = 5000;
    private List<IModel> tripList;

    @Inject
    private IMainController mainController;
    @Inject
    private SessionManager sessionManager;

    private UUID mCurrentUUID;

    @Inject
    private EventManager eventManager;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         ViewGroup viewGroup = (ViewGroup) view;



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mCurrentUUID = (UUID) savedInstanceState.get(ARG_UUID);
        }


        tripList = new LinkedList<IModel>();
        TripListAdapter adapter = new TripListAdapter(getActivity(), tripList);
        setListAdapter(adapter);



    }

    @Override
    public void onStart() {
        super.onStart();

        this.getListView().setOnItemLongClickListener(this);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_UUID, mCurrentUUID);
    }

    public void updateTripView(@Observes OnUpdateTripListEvent event ) {

        if (event.getBoat().getUUID() != null) {

            Collection<Boat> boats = (Collection<Boat>) mainController.getSingleDocument("boat", event.getBoat().getAccount(), event.getBoat().getUUID());
            if (!boats.isEmpty() && boats.iterator().hasNext()) {
                IBoat b = boats.iterator().next();
                if (b != null) {
                    tripList.clear();
                    tripList.addAll(mainController.getByParent("trip", "boat", sessionManager.getSession(), b.getUUID()));
                    notifyListAdapter();
                    mCurrentUUID = event.getBoat().getUUID();
                }
            }
        }
    }

    public void deleteTrip(int listPosition)  {
        TripListAdapter listAdapter = (TripListAdapter) getListView().getAdapter();
        Trip trip = (Trip) listAdapter.getItem(listPosition);
        mainController.deleteDocument("trip", sessionManager.getSession(), trip.getUUID());
        listAdapter.remove(trip);
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
        Trip trip = (Trip) l.getAdapter().getItem(position);
        intent.putExtra("trip", trip.getId());
        getActivity().startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        DeleteTripDialog d = new DeleteTripDialog(i);
        d.show(getFragmentManager(), "TripListFragment");
        return true;
    }




    @SuppressLint("ValidFragment")
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

