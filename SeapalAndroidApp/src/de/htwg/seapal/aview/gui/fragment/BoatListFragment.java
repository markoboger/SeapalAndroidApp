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

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.activity.LogbookTabsActivity;
import de.htwg.seapal.controller.IBoatController;
import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.model.impl.Boat;
import roboguice.fragment.RoboListFragment;

/**
 * Created by jakub on 11/16/13.
 */
public class BoatListFragment extends RoboListFragment {


    /**
     * callback the activity if a boat got selected
     */
    private OnBoatNameSelectedListener mBoatSelectedCallback;
    /**
     * callback the activity if a boat got favoured
     */
    private OnBoatFavouredListener mBoatFavouredCallback;

    @Inject
    private IBoatController boatController;

    /**
     * boatList ist used by the ListAdapter to handle the list of boats
     */
    private List<IBoat> boatList;

    /**
     * is the selected position inside the list
     */
    private int mPosition = -1;
    private View mSelectedView;


    /**
     * interface for callback if a Boat inside the list is selected
     */
    public interface OnBoatNameSelectedListener {

        public void onBoatSelected(int position, UUID uuid);

    }


    /**
     * interface for callback if a Boat in the list is favoured
     */
    public interface OnBoatFavouredListener {
        public void onBoatFavoured(UUID uuid);

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boatList = boatController.getAllBoats();
        final int layout = R.layout.boat_list_view;



        setListAdapter(new ArrayAdapter<IBoat>(getActivity(), layout, boatList) {
            /**
             * {@inheritDoc}
             */
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
                ImageView favImageView = (ImageView) v.findViewById(R.id.favour_button);

                SharedPreferences s = getContext().getSharedPreferences(LogbookTabsActivity.LOGBOOK_PREFS,0);
                String favouredBoatUUIDString = s.getString(LogbookTabsActivity.LOGBOOK_BOAT_FAVOURED, "");




                IBoat b = getItem(position);
                if (boatName != null && boatType != null && productionYear != null && constructor != null) {
                    boatName.setText(b.getBoatName());
                    boatType.setText(b.getType());
                    if (b.getYearOfConstruction() != 0) {
                        productionYear.setText(b.getYearOfConstruction().toString());
                    } else {
                        productionYear.setText("");
                    }
                    constructor.setText(b.getConstructor());

                    UUID uuid = b.getUUID();

                    if (!StringUtils.isEmpty(favouredBoatUUIDString)) {
                        UUID uuidFavoured = UUID.fromString(favouredBoatUUIDString);
                        if (uuidFavoured.equals(uuid))
                            favImageView.setBackgroundResource(android.R.drawable.star_big_on);

                    }
                }


                return v;
            }

        });


    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStart() {
        super.onStart();
        setSelection(0);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Notify the parent activity of selected item
        IBoat boat = (IBoat) l.getAdapter().getItem(position);
        mBoatSelectedCallback.onBoatSelected(position, boat.getUUID());
        mPosition = position;
        mSelectedView = v;
        v.setSelected(true);
        for (View a: l.getTouchables()) {
            a.findViewById(R.id.caret).setVisibility(View.INVISIBLE);
        }
        v.findViewById(R.id.caret).setVisibility(View.VISIBLE);

    }

    /**
     * onNewBoat handles the request to create a new boat.
     * it adds the boat in the database and in boatList,
     * which is used in the list-adapter
     */
    public void onNewBoat() {
        UUID uuid = boatController.newBoat();
        IBoat boat = boatController.getBoat(uuid);
        boatList.add(boat);
        notifyListAdapter();
        getListView().setSelection(boatList.size());
        Toast.makeText(getActivity(), "New Boat Created",
                Toast.LENGTH_SHORT).show();
    }

    /**
     * onDeleteBoat handles the request to delete a boat.
     * it removes the boat in the database and in boatList,
     * which is used in the list-adapter
     */
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


    /**
     * onSaveBoat handles the request to save a boat.
     * it saves the boat in the database and in boatList,
     * which is used in the list-adapter
     */
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


    /**
     * onFavourBoat handles the request to favour a boat
     * it just  switches off the stars of all other boats
     * and replaces the favoured boat star with a drawable star_big_on.
     * After this it will callback the activities onBoatFavoured to save
     * the uuid in SharedPreferences.
     */
    public void onFavourBoat() {
        if (mPosition >= 0 && mSelectedView != null){
            ListView l = getListView();
            for (View a: l.getTouchables()) {
                ImageView switchStarOffView = (ImageView) a.findViewById(R.id.favour_button);
                switchStarOffView.setBackgroundResource(android.R.drawable.star_big_off);

            }
            ImageView view = (ImageView) mSelectedView.findViewById(R.id.favour_button);
            view.setBackgroundResource(android.R.drawable.star_big_on);
            IBoat boat = (IBoat) getListAdapter().getItem(mPosition);
            mBoatFavouredCallback.onBoatFavoured(boat.getUUID());
        }

    }

    /**
     * notifies the listView's list-adapter that the data-set has changed.
     */
    private void notifyListAdapter() {
        ArrayAdapter<IBoat> listAdapter = (ArrayAdapter<IBoat>) getListAdapter();
        listAdapter.notifyDataSetChanged();
    }


}
