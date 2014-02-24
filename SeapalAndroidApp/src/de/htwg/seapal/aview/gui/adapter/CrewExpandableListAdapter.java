package de.htwg.seapal.aview.gui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.htwg.seapal.manager.SessionManager;
import de.htwg.seapal.R;
import de.htwg.seapal.controller.IAccountController;
import de.htwg.seapal.controller.IMainController;
import de.htwg.seapal.controller.IPersonController;
import de.htwg.seapal.model.IModel;
import de.htwg.seapal.model.impl.Person;
import roboguice.RoboGuice;

/**
 * Created by jakub on 2/20/14.
 */
public class CrewExpandableListAdapter extends BaseExpandableListAdapter {


    public static final String YOUR_CREW = "Your Crew";
    public static final String YOU_ARE_MEMBER_OF = "You are Crew Member of";
    public static final String REQUEST_PENDING = "Friend Requests Pending";
    public static final String FRIENDS_TO_ACCEPT = "Friends to accept";


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
    private Map<String, Collection<? extends IModel>> _listDataChild;

    public CrewExpandableListAdapter(Context context, List<String> listDataHeader,
                                     Map<String, Collection<? extends IModel>> listChildData) {
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

        final Person child = (Person) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.crew_list_item, null);
        }


        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);



        if (this._listDataHeader.get(groupPosition).equals(YOUR_CREW)) {
            txtListChild.setOnClickListener(new YouCrewClickListener(_context,child));
        }

        if (this._listDataHeader.get(groupPosition).equals(FRIENDS_TO_ACCEPT)) {

            txtListChild.setOnClickListener(new FriendsToAcceptClickListener(_context,child));

        }

        txtListChild.setText(String.format("%s %s", child.getFirstname(), child.getLastname()));
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
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.crew_list_group, null);
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






    private class YouCrewClickListener implements View.OnClickListener {
        private Person person;
        private Context context;

        public YouCrewClickListener(Context context, Person child) {
            person = child;
            this.context = context;
        }

        @Override
        public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.crew_dialog_delete_friend)
                    .setPositiveButton(R.string.crew_dialog_delete_friend_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                mainController.abortRequest(sessionManager.getSession(), UUID.fromString(person.getAccount()));
                            } catch (NullPointerException e) {
                                Toast.makeText(context, "Something Strange Happend", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });
            // Create the AlertDialog object and show it.
            builder.create().show();
        }
    }



    private class FriendsToAcceptClickListener implements View.OnClickListener {
        private Person person;
        private Context context;

        public FriendsToAcceptClickListener(Context context, Person child) {
            person = child;
            this.context = context;
        }

        @Override
        public void onClick(View v) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(R.string.crew_dialog_accept_friend)
                    .setPositiveButton(R.string.crew_dialog_accept_friend_yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                mainController.addFriend(sessionManager.getSession(), UUID.fromString(person.getAccount()));
                            } catch (NullPointerException e) {
                                Toast.makeText(context, "Something Strange Happend", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNeutralButton(R.string.crew_dialog_accept_friend_decline, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                mainController.abortRequest(sessionManager.getSession(), UUID.fromString(person.getAccount()));
                            } catch (NullPointerException e) {
                                Toast.makeText(context, "Something Strange Happend", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();

                        }
                    });
            // Create the AlertDialog object and show it.
            builder.create().show();
        }
    }
}
