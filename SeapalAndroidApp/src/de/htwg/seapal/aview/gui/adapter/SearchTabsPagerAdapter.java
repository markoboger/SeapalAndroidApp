package de.htwg.seapal.aview.gui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

import de.htwg.seapal.aview.gui.fragment.search.MarkerFragment;
import de.htwg.seapal.aview.gui.fragment.search.POIFragment;

/**
 * Created by robin on 08.01.14.
 */
public class SearchTabsPagerAdapter extends FragmentPagerAdapter {

    public SearchTabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Marker fragment activity
                return new MarkerFragment();
            case 1:
                // POI fragment activity
                return new POIFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}

