package de.htwg.seapal.aview.gui.fragment.search;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import de.htwg.seapal.R;
import de.htwg.seapal.aview.gui.adapter.SearchResultListAdapter;

/**
 * Created by robin on 08.01.14.
 */
public class MarkerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /////////////////
        /// Dummy Daten
        ArrayList<String> title = new ArrayList<String>();
        title.add("Maker 0");
        title.add("Maker 1");
        title.add("Maker 2");
        title.add("Maker 3");
        title.add("Maker 4");
        title.add("Maker 5");
        title.add("Maker 6");
        title.add("Maker 7");
        title.add("Maker 8");
        title.add("Maker 9");
        title.add("Maker 10");
        title.add("Maker 11");
        title.add("Maker 100");

        ArrayList<String> list = new ArrayList<String>();
        list.add("Location : N50.418716° , E006.750000° 0");
        list.add("Location : N50.418716° , E006.750000° 1");
        list.add("Location : N50.418716° , E006.750000° 2");
        list.add("Location : N50.418716° , E006.750000° 3");
        list.add("Location : N50.418716° , E006.750000° 4");
        list.add("Location : N50.418716° , E006.750000° 5");
        list.add("Location : N50.418716° , E006.750000° 6");
        list.add("Location : N50.418716° , E006.750000° 7");
        list.add("Location : N50.418716° , E006.750000° 8");
        list.add("Location : N50.418716° , E006.750000° 9");
        list.add("Location : N50.418716° , E006.750000° 10");
        list.add("Location : N50.418716° , E006.750000° 11");
        list.add("Location : N50.418716° , E006.750000° 100");
        /// Dummy Daten
        /////////////////

        String[] titleS = new String[title.size()];
        String[] textS = new String[list.size()];
        titleS = title.toArray(titleS);
        textS = list.toArray(textS);

        View rootView = inflater.inflate(R.layout.marker_fragment, container, false);
        ListView listV = (ListView) rootView.findViewById(R.id.marker_listview);
        SearchResultListAdapter aa = new SearchResultListAdapter(getActivity(),titleS, textS);
        listV.setAdapter(aa);

        return rootView;
    }

}