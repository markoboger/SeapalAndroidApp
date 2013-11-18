package de.htwg.seapal.aview.gui.fragment;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.inject.Inject;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.controller.IBoatController;
import de.htwg.seapal.model.IBoat;
import roboguice.RoboGuice;

/**
 * Created by jakub on 11/16/13.
 */
public class BoatNamesFragment extends ListFragment {

    OnBoatNameSelectedListener mCallback;
    @Inject
    private IBoatController boatController;
    private List<IBoat> boatList;
    private List<String> namesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoboGuice.getInjector(getActivity()).injectMembersWithoutViews(this);

        boatList = boatController.getAllBoats();
        final int layout = R.layout.boat_names_list_view;

        namesList = new LinkedList<String>();


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
                    productionYear.setText(b.getYearOfConstruction().toString());
                    constructor.setText(b.getConstructor());


                return v;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.
        try {
            mCallback = (OnBoatNameSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnBoatNameSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Notify the parent activity of selected item
        IBoat boat = (IBoat) l.getAdapter().getItem(position);
        mCallback.onBoatSelected(position, boat.getUUID());
        v.setSelected(true);
        for (View a: l.getTouchables()) {
            a.findViewById(R.id.caret).setVisibility(View.INVISIBLE);

        }

        v.findViewById(R.id.caret).setVisibility(View.VISIBLE);

    }


    public interface OnBoatNameSelectedListener {

        public void onBoatSelected(int position, UUID uuid);

    }

}
