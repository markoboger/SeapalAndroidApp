package de.htwg.seapal.aview.gui.fragment.search;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.adapter.SearchResultListAdapter;

/**
 * Created by robin on 08.01.14.
 */
public class POIFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /////////////////
        /// Dummy Daten
        ArrayList<String> list = new ArrayList<String>();
        list.add("Konstanz");
        list.add("Hamburg");
        list.add("Sylt");
        list.add("Joy");
        list.add("Klein Paris");
        list.add("Restaurant");
        list.add("Toller Platz");
        list.add("Münster");
        list.add("ander Tolle sachen");
        list.add("und noch viel mehr");
        list.add("mehr....");
        list.add("mehr....");
        list.add("mehr....");
        list.add("mehr....");

        ArrayList<String> beschreibung = new ArrayList<String>();
        beschreibung.add("Das ist eine Beschreibung zum diesem POI");
        beschreibung.add("Das ist eine Beschreibung zum diesem POI");
        beschreibung.add("Das ist eine Beschreibung zum diesem POI");
        beschreibung.add("Das ist eine Beschreibung zum diesem POI");
        beschreibung.add("Das ist eine Beschreibung zum diesem POI");
        beschreibung.add("Das ist eine Beschreibung zum diesem POI");
        beschreibung.add("Das ist eine Beschreibung zum diesem POI");
        beschreibung.add("Das ist eine Beschreibung zum diesem POI");
        beschreibung.add("Das ist eine Beschreibung zum diesem POI");
        beschreibung.add("Das ist eine Beschreibung zum diesem POI");
        beschreibung.add("Das ist eine Beschreibung zum diesem POI");
        beschreibung.add("Das ist eine Beschreibung zum diesem POI");
        beschreibung.add("Das ist eine Beschreibung zum diesem POI");
        beschreibung.add("Das ist eine Beschreibung zum diesem POI");
        /// Dummy Daten
        /////////////////

        String[] listS = new String[list.size()];
        String[] beschreibungS = new String[beschreibung.size()];
        listS = list.toArray(listS);
        beschreibungS = beschreibung.toArray(beschreibungS);

        View rootView = inflater.inflate(R.layout.poi_fragment, container, false);
        ListView listV = (ListView) rootView.findViewById(R.id.poi_list);
        SearchResultListAdapter aa = new SearchResultListAdapter(getActivity(), listS, beschreibungS);
        listV.setAdapter(aa);

        return rootView;
    }
}
