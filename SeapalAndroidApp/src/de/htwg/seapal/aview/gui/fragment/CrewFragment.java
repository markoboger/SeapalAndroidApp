package de.htwg.seapal.aview.gui.fragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.google.inject.Inject;
import com.google.inject.Injector;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.adapter.CrewExpandableListAdapter;
import de.htwg.seapal.controller.IMainController;
import de.htwg.seapal.controller.IPersonController;
import de.htwg.seapal.events.crew.OnCrewAddEvent;
import de.htwg.seapal.manager.SessionManager;
import de.htwg.seapal.model.IModel;
import roboguice.RoboGuice;
import roboguice.event.Observes;

/**
 * Created by jakub on 12/10/13.
 */
public class CrewFragment extends Fragment {

    @Inject
    private IMainController mainController;

    @Inject
    private IPersonController personController;

    @Inject
    private SessionManager sessionManager;

    private List<String> listHeader;

    private Map<String, Collection<? extends IModel>> groupLists;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // cuz this uses Fragment and not Robofragment, cuz Roboguice has no support for roboActionbar
        // so its a mixin with support fragments and android fragments.
        Injector i = RoboGuice.getInjector(getActivity());
        i.injectMembers(this);


        listHeader = new LinkedList<String>();
        listHeader.add(CrewExpandableListAdapter.YOUR_CREW);
        listHeader.add(CrewExpandableListAdapter.FRIENDS_TO_ACCEPT);

        groupLists = new LinkedHashMap<String, Collection<? extends IModel>>();
        if(sessionManager.isLoggedIn()) {
            Collection<? extends IModel> yourCrew = mainController.getDocuments("person", sessionManager.getSession(), "friends");
            Collection<? extends IModel> friendsToAccept = mainController.getDocuments("person", sessionManager.getSession(), "asking");
            groupLists.put(CrewExpandableListAdapter.YOUR_CREW, yourCrew);
            groupLists.put(CrewExpandableListAdapter.FRIENDS_TO_ACCEPT, friendsToAccept);

        }




        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
        }
        return inflater.inflate(R.layout.crew_view, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ExpandableListView listView = (ExpandableListView) view.findViewById(R.id.crew_expandable_list);
        listView.setAdapter(new CrewExpandableListAdapter(getActivity(), listHeader, groupLists));



    }

    public void onCrewAdd(@Observes OnCrewAddEvent event) {
        LayoutInflater i = (LayoutInflater) event.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = i.inflate(R.layout.crew_add_dialog, null);
        new AlertDialog.Builder(getActivity())
                .setView(view)
                .setMessage(R.string.crew_add_title_text)
                .setPositiveButton(R.string.crew_add_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText e = null;
                        if (view != null) {
                            e = (EditText) view.findViewById(R.id.text);
                            if (e != null) {
                                if (sessionManager.isLoggedIn()) {
                                    mainController.addFriend(sessionManager.getSession(), e.getText().toString());
                                }
                            }
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create().show();
    }
}
