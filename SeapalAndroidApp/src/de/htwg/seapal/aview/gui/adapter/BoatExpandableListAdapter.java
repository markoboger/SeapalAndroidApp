package de.htwg.seapal.aview.gui.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.Injector;

import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.activity.LogbookTabsActivity;
import de.htwg.seapal.controller.IAccountController;
import de.htwg.seapal.controller.IMainController;
import de.htwg.seapal.controller.IPersonController;
import de.htwg.seapal.events.boat.BoatCreatedEvent;
import de.htwg.seapal.events.boat.BoatDeletedEvent;
import de.htwg.seapal.events.boat.BoatFavoredEvent;
import de.htwg.seapal.events.boat.BoatSavedEvent;
import de.htwg.seapal.events.boat.RequestBoatViewInformation;
import de.htwg.seapal.events.boat.RequestSelectedPackedPosition;
import de.htwg.seapal.manager.SessionManager;
import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.model.IModel;
import de.htwg.seapal.model.impl.Boat;
import roboguice.RoboGuice;
import roboguice.event.EventManager;
import roboguice.event.Observes;

/**
 * Created by jakub on 2/24/14.
 */
public class BoatExpandableListAdapter extends BaseExpandableListAdapter {
    public static final String YOUR_BOATS = "Your Boats";
    public static final String BOATS_OF_CREW = "Boats of your Crew";


    @Inject
    private IMainController mainController;

    @Inject
    private IPersonController personController;

    @Inject
    private IAccountController accountController;

    @Inject
    private SessionManager sessionManager;

    private Context _context;

    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private Map<String, List<IBoat>> _listDataChild;

    @Inject
    private EventManager eventManager;

    public BoatExpandableListAdapter(Context context, List<String> listDataHeader,
                                     Map<String, List<IBoat>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;

        Injector i = RoboGuice.getInjector(context);
        i.injectMembers(this);


    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        Collection c = this._listDataChild.get(this._listDataHeader.get(groupPosition));
        return new LinkedList<IModel>(c).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Boat child = (Boat) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.boat_list_view, null);
        }




        TextView boatName = (TextView) convertView.findViewById(R.id.boat_name);
        TextView boatType = (TextView) convertView.findViewById(R.id.boat_type);
        TextView productionYear = (TextView) convertView.findViewById(R.id.production_year);
        TextView constructor = (TextView) convertView.findViewById(R.id.constructor);
        ImageView favImageView = (ImageView) convertView.findViewById(R.id.favour_button);

        SharedPreferences s = _context.getSharedPreferences(LogbookTabsActivity.LOGBOOK_PREFS, 0);
        String favouredBoatUUIDString = s.getString(LogbookTabsActivity.LOGBOOK_BOAT_FAVOURED, "");


        if (boatName != null && boatType != null && productionYear != null && constructor != null) {
            boatName.setText(child.getName());
            boatType.setText(child.getType());
            if (child.getYearOfConstruction() != 0) {
                productionYear.setText(child.getYearOfConstruction().toString());
            } else {
                productionYear.setText("");
            }
            constructor.setText(child.getBoatConstructor());

            UUID uuid = child.getUUID();

            if (!StringUtils.isEmpty(favouredBoatUUIDString)) {
                UUID uuidFavoured = UUID.fromString(favouredBoatUUIDString);
                if (uuidFavoured.equals(uuid))
                    favImageView.setBackgroundResource(android.R.drawable.star_big_on);
                else {
                    favImageView.setBackgroundResource(android.R.drawable.star_big_off);

                }

            }
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }



    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.boat_list_group, null);
        }


        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }



    public void deleteBoat(@Observes BoatDeletedEvent event) {
        if (event.getCurrentPosition() != -1) {
            int child  = ExpandableListView.getPackedPositionChild(event.getCurrentPosition());
            int group  = ExpandableListView.getPackedPositionGroup(event.getCurrentPosition());
            String groupHeader  = _listDataHeader.get(group);
            _listDataChild.get(groupHeader).remove(child);
            notifyDataSetInvalidated();
            notifyDataSetChanged();
        }

    }


    public void onBoatCreated(@Observes BoatCreatedEvent event) {
        _listDataChild.get(YOUR_BOATS).add(event.getBoat());
        notifyDataSetInvalidated();
        notifyDataSetChanged();
    }

    public void onBoatFavoured(@Observes BoatFavoredEvent event) {
        notifyDataSetInvalidated();
        notifyDataSetChanged();
    }

    public void onBoatSaved(@Observes BoatSavedEvent event) {

        RequestSelectedPackedPosition r = new RequestSelectedPackedPosition();
        eventManager.fire(r);
        if (r.getPackedPosition() != null){
            int child  = ExpandableListView.getPackedPositionChild(r.getPackedPosition());
            int group  = ExpandableListView.getPackedPositionGroup(r.getPackedPosition());
            String groupHeader  = _listDataHeader.get(group);
            RequestBoatViewInformation boatViewInformation = new RequestBoatViewInformation();
            eventManager.fire(boatViewInformation);
            if (boatViewInformation != null && boatViewInformation.getBoat() != null) {
                _listDataChild.get(groupHeader).set(child, boatViewInformation.getBoat());
            }

        }
        notifyDataSetInvalidated();
        notifyDataSetChanged();
    }
}
