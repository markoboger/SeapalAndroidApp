package de.htwg.seapal.aview.gui.fragment;

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
import de.htwg.seapal.controller.IMainController;
import de.htwg.seapal.events.boat.BoatCreatedEvent;
import de.htwg.seapal.events.boat.BoatDeletedEvent;
import de.htwg.seapal.events.boat.BoatFavoredEvent;
import de.htwg.seapal.events.boat.BoatSavedEvent;
import de.htwg.seapal.events.boat.CreateBoatEvent;
import de.htwg.seapal.events.boat.DeleteBoatEvent;
import de.htwg.seapal.events.boat.FavourBoatEvent;
import de.htwg.seapal.events.boat.OnBoatSelected;
import de.htwg.seapal.events.boat.RequestBoatViewInformation;
import de.htwg.seapal.events.boat.SaveBoatEvent;
import de.htwg.seapal.manager.SessionManager;
import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.model.impl.Boat;
import roboguice.event.EventManager;
import roboguice.event.Observes;
import roboguice.fragment.RoboListFragment;

/**
 * Created by jakub on 11/16/13.
 */
public class BoatListFragment extends RoboListFragment {



    @Inject
    private IMainController mainController;

    @Inject
    private SessionManager sessionManager;

    @Inject
    private EventManager eventManager;

    /**
     * boatList ist used by the ListAdapter to handle the list of boats
     */
    private List<IBoat> boatList;

    private List<IBoat> boatListFriends;
    /**
     * is the selected position inside the list
     */
    private int mPosition = -1;
    private View mSelectedView;





    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boatList = (List<IBoat>) mainController.getDocuments("boat", sessionManager.getSession(),sessionManager.getSession(), "own");
        boatListFriends = (List<IBoat>) mainController.getDocuments("boat", sessionManager.getSession(),sessionManager.getSession(), "friends");

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

                SharedPreferences s = getContext().getSharedPreferences(LogbookTabsActivity.LOGBOOK_PREFS, 0);
                String favouredBoatUUIDString = s.getString(LogbookTabsActivity.LOGBOOK_BOAT_FAVOURED, "");


                IBoat b = getItem(position);
                if (boatName != null && boatType != null && productionYear != null && constructor != null) {
                    boatName.setText(b.getName());
                    boatType.setText(b.getType());
                    if (b.getYearOfConstruction() != 0) {
                        productionYear.setText(b.getYearOfConstruction().toString());
                    } else {
                        productionYear.setText("");
                    }
                    constructor.setText(b.getBoatConstructor());

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
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Notify the parent activity of selected item
        IBoat boat = (IBoat) l.getAdapter().getItem(position);
        UUID boatUuid = boat.getUUID();
        eventManager.fire(new OnBoatSelected(position, boatUuid));
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
    public void onNewBoat(@Observes CreateBoatEvent createBoatEvent) {
        IBoat boat = (IBoat) mainController.creatDocument("boat", new Boat(), sessionManager.getSession());
        boatList.add(boat);
        notifyListAdapter();
        getListView().setSelection(boatList.size());
        Toast.makeText(getActivity(), "New Boat Created",
                Toast.LENGTH_SHORT).show();
        eventManager.fire(new BoatCreatedEvent());
    }

    /**
     * onDeleteBoat handles the request to delete a boat.
     * it removes the boat in the database and in boatList,
     * which is used in the list-adapter
     */
    public void onDeleteBoat(@Observes DeleteBoatEvent deleteBoatEvent) {
        if (mPosition >= 0) {
            IBoat boat = (IBoat) getListAdapter().getItem(mPosition);
            boatList.remove(mPosition);
            mainController.deleteDocument("boat", sessionManager.getSession(), boat.getUUID());
            notifyListAdapter();
            getListView().setSelection(mPosition);
            Toast.makeText(getActivity(), "Boat Deleted",
                    Toast.LENGTH_SHORT).show();
            eventManager.fire(new BoatDeletedEvent());
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
    public void onSaveBoat(@Observes SaveBoatEvent saveBoatEvent) {
        RequestBoatViewInformation b = new RequestBoatViewInformation();
        eventManager.fire(b);
        IBoat iboat = b.getBoat();

        if(iboat != null) {
            Boat boat = (Boat) iboat;
            if (mPosition >= 0) {
                boatList.set(mPosition, boat);
                mainController.creatDocument("boat", boat, sessionManager.getSession());
                notifyListAdapter();
                Toast.makeText(getActivity(), "Boat Saved",
                        Toast.LENGTH_SHORT).show();
                eventManager.fire(new BoatSavedEvent());
            } else {
                Toast.makeText(getActivity(), "Please select a Boat",
                        Toast.LENGTH_SHORT).show();

            }
        }

    }


    /**
     * onFavourBoat handles the request to favour a boat
     * it just  switches off the stars of all other boats
     * and replaces the favoured boat star with a drawable star_big_on.
     * After this it will callback the activities onBoatFavoured to save
     * the uuid in SharedPreferences.
     */
    public void onFavourBoat(@Observes FavourBoatEvent c) {
        if (mPosition >= 0 && mSelectedView != null){
            ListView l = getListView();
            for (View a: l.getTouchables()) {
                ImageView switchStarOffView = (ImageView) a.findViewById(R.id.favour_button);
                switchStarOffView.setBackgroundResource(android.R.drawable.star_big_off);

            }
            ImageView view = (ImageView) mSelectedView.findViewById(R.id.favour_button);
            view.setBackgroundResource(android.R.drawable.star_big_on);
            IBoat boat = (IBoat) getListAdapter().getItem(mPosition);
            eventManager.fire(new BoatFavoredEvent(boat.getUUID()));
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
