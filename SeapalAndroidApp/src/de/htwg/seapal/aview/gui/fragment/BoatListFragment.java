package de.htwg.seapal.aview.gui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.inject.Inject;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.adapter.BoatExpandableListAdapter;
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
import de.htwg.seapal.events.boat.RequestSelectedPackedPosition;
import de.htwg.seapal.events.boat.SaveBoatEvent;
import de.htwg.seapal.events.crew.CrewAcceptedEvent;
import de.htwg.seapal.events.crew.CrewRemovedEvent;
import de.htwg.seapal.manager.SessionManager;
import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.model.impl.Boat;
import roboguice.event.EventManager;
import roboguice.event.Observes;
import roboguice.fragment.RoboFragment;

/**
 * Created by jakub on 11/16/13.
 */
public class BoatListFragment extends RoboFragment {


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

    private ExpandableListView boatView;

    private List<IBoat> boatListFriends;




    /**
     * is the selected position inside the list
     * in packed form form ExpandableListView
     */
    private long mCurrentPosition = -1;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (boatView == null)
            boatView = new ExpandableListView(getActivity());

        return boatView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        List<String> boatHeadings = new LinkedList<String>();
        boatHeadings.add(BoatExpandableListAdapter.YOUR_BOATS);
        boatHeadings.add(BoatExpandableListAdapter.BOATS_OF_CREW);

        Map<String, List<IBoat>> boatMap = new LinkedHashMap<String, List<IBoat>>();

        boatList = (List<IBoat>) mainController.getDocuments("boat", sessionManager.getSession(),sessionManager.getSession(), "own");
        boatListFriends = (List<IBoat>) mainController.getDocuments("boat", sessionManager.getSession(),sessionManager.getSession(), "friends");

        boatMap.put(BoatExpandableListAdapter.YOUR_BOATS, boatList);
        boatMap.put(BoatExpandableListAdapter.BOATS_OF_CREW, boatListFriends);


        boatView.setAdapter(new BoatExpandableListAdapter(getActivity(), boatHeadings, boatMap));
        boatView.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);



        boatView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ExpandableListAdapter adapter = parent.getExpandableListAdapter();
                Boat boat = (Boat) adapter.getChild(groupPosition, childPosition);
                UUID boatUuid = boat.getUUID();
                for (View a: parent.getTouchables()) {
                    View caret =  a.findViewById(R.id.caret);
                    if (caret != null)
                        caret.setVisibility(View.INVISIBLE);
                }
                v.findViewById(R.id.caret).setVisibility(View.VISIBLE);
                mCurrentPosition = ExpandableListView.getPackedPositionForChild(groupPosition, childPosition);

                eventManager.fire(new OnBoatSelected(boat));

                return true;
            }
        });



        super.onActivityCreated(savedInstanceState);
    }

    /**
     * onNewBoat handles the request to create a new boat.
     * it adds the boat in the database and in boatList,
     * which is used in the list-adapter
     */
    public void onNewBoat(@Observes CreateBoatEvent createBoatEvent) {
        IBoat boat = (IBoat) mainController.creatDocument("boat", new Boat(), sessionManager.getSession());
        if  (boat != null) {
            Toast.makeText(getActivity(), "New Boat Created",
                    Toast.LENGTH_SHORT).show();
            eventManager.fire(new BoatCreatedEvent());
        }
    }

    /**
     * onDeleteBoat handles the request to delete a boat.
     * it removes the boat in the database and in boatList,
     * which is used in the list-adapter
     */
    public void onDeleteBoat(@Observes DeleteBoatEvent deleteBoatEvent) {
        for (View a: boatView.getTouchables()) {
            View caret =  a.findViewById(R.id.caret);
            if (caret != null)
                caret.setVisibility(View.INVISIBLE);
        }
        int group = ExpandableListView.getPackedPositionGroup(mCurrentPosition);
        int child = ExpandableListView.getPackedPositionChild(mCurrentPosition);
        Boat b = (Boat) boatView.getExpandableListAdapter().getChild(group, child);
        if (b != null) {
            mainController.deleteDocument("boat", sessionManager.getSession(), b.getUUID());
            eventManager.fire(new BoatDeletedEvent());
        }
    }


    /**
     * onSaveBoat handles the request to save a boat.
     * it saves the boat in the database and in boatList,
     * which is used in the list-adapter
     */
    public void onSaveBoat(@Observes SaveBoatEvent saveBoatEvent) {
        RequestBoatViewInformation r = new RequestBoatViewInformation();
        eventManager.fire(r);
        Boat b = (Boat) r.getBoat();
        if (b != null) {
            mainController.creatDocument("boat", b, sessionManager.getSession());
            Toast.makeText(getActivity(), "Boat Saved", Toast.LENGTH_LONG).show();
            eventManager.fire(new BoatSavedEvent());

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
        if (mCurrentPosition != -1) {
            int group = ExpandableListView.getPackedPositionGroup(mCurrentPosition);
            int child = ExpandableListView.getPackedPositionChild(mCurrentPosition);
            Boat b = (Boat) boatView.getExpandableListAdapter().getChild(group,child);
            if (b != null ) {
                eventManager.fire(new BoatFavoredEvent(b.getUUID()));
                Toast.makeText(getActivity(), "Boat Favoured", Toast.LENGTH_LONG).show();
            }


        }
    }

    public void requestPackedPosition(@Observes RequestSelectedPackedPosition c) {
        if (mCurrentPosition != -1) {
            c.setPackedPosition(mCurrentPosition);
        }
    }
    public void crewRemoved(@Observes CrewAcceptedEvent c) {
        onActivityCreated(new Bundle());
    }
    public void crewAccepted(@Observes CrewRemovedEvent c) {
        onActivityCreated(new Bundle());
    }

}
