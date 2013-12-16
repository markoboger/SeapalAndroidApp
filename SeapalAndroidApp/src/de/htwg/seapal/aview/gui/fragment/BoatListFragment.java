package de.htwg.seapal.aview.gui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.controller.IBoatController;
import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.model.impl.Boat;
import roboguice.fragment.RoboListFragment;

/**
 * Created by jakub on 11/16/13.
 */
public class BoatListFragment extends RoboListFragment {


    private OnBoatNameSelectedListener mBoatSelectedCallback;
    private OnBoatFavouredListener mBoatFavouredCallback;
    @Inject
    private IBoatController boatController;

    public static final String BOATLIST_PREF_NAME = "boat_list_pref_name";
    public static final String BOAT_UUID_PREF_NAME = "boat_uuid_pref_name";

    private List<IBoat> boatList;

    private int mPosition = -1;

    private SharedPreferences settings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boatList = boatController.getAllBoats();
        final int layout = R.layout.boat_list_view;



        setListAdapter(new ArrayAdapter<IBoat>(getActivity(), layout, boatList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                TextView text;
                if (v == null) {
                    LayoutInflater l = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = l.inflate(layout, parent, false);
                }

                TextView boatName = (TextView) v.findViewById(R.id.boat_name);
                TextView boatType = (TextView) v.findViewById(R.id.boat_type);
                TextView productionYear = (TextView) v.findViewById(R.id.production_year);
                TextView constructor = (TextView) v.findViewById(R.id.constructor);


                IBoat b = getItem(position);
                if (boatName != null && boatType != null && productionYear != null && constructor != null)
                    boatName.setText(b.getBoatName());
                    boatType.setText(b.getType());
                    if (b.getYearOfConstruction() != 0) {
                        productionYear.setText(b.getYearOfConstruction().toString());
                    } else {
                        productionYear.setText("");
                    }
                    constructor.setText(b.getConstructor());


                return v;
            }

        });


    }

    @Override
    public void onStart() {
        super.onStart();
        setSelection(0);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mBoatSelectedCallback = (OnBoatNameSelectedListener) activity;
            mBoatFavouredCallback = (OnBoatFavouredListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBoatNameSelectedListener and OnBoatFavouredListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Notify the parent activity of selected item
        IBoat boat = (IBoat) l.getAdapter().getItem(position);
        mBoatSelectedCallback.onBoatSelected(position, boat.getUUID());
        mPosition = position;
        v.setSelected(true);
        for (View a: l.getTouchables()) {
            a.findViewById(R.id.caret).setVisibility(View.INVISIBLE);
        }
        v.findViewById(R.id.caret).setVisibility(View.VISIBLE);

    }

    public void onNewBoat() {
        UUID uuid = boatController.newBoat();
        IBoat boat = boatController.getBoat(uuid);
        boatList.add(boat);
        notifyListAdapter();
        getListView().setSelection(boatList.size());
        Toast.makeText(getActivity(), "New Boat Created",
                Toast.LENGTH_SHORT).show();
    }

    public void onDeleteBoat() {
        if (mPosition >= 0) {
            IBoat boat = (IBoat) getListAdapter().getItem(mPosition);
            boatList.remove(mPosition);
            boatController.deleteBoat(boat.getUUID());
            notifyListAdapter();
            getListView().setSelection(mPosition);
            Toast.makeText(getActivity(), "Boat Deleted",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Please select a Boat",
                    Toast.LENGTH_SHORT).show();
        }
    }


    public void onSaveBoat(BoatViewFragment boatViewFragment) {
        Boat boat = (Boat) boatViewFragment.getBoatFromCurrentView();
        if (mPosition >= 0) {
            boatList.set(mPosition, boat);
            boatController.saveBoat(boat);
            notifyListAdapter();
            Toast.makeText(getActivity(), "Boat Saved",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Please select a Boat",
                    Toast.LENGTH_SHORT).show();

        }

    }

    public void onFavourBoat() {
        if (mPosition >= 0){
            IBoat boat = (IBoat) getListAdapter().getItem(mPosition);
            ListView v = getListView();
            View a = v.getSelectedView();
            ImageView view = (ImageView) a.findViewById(R.id.favour_button);
            view.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.star_on));

        }

    }

    private void notifyListAdapter() {
        ArrayAdapter<IBoat> listAdapter = (ArrayAdapter<IBoat>) getListAdapter();
        listAdapter.notifyDataSetChanged();
    }


    public interface OnBoatNameSelectedListener {

        public void onBoatSelected(int position, UUID uuid);

    }


    public interface OnBoatFavouredListener {
        public void onBoatFavoured(UUID uuid);

    }
}
