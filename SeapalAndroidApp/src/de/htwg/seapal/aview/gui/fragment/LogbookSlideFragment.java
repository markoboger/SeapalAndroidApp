package de.htwg.seapal.aview.gui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;

import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.UUID;

import de.htwg.seapal.Manager.SessionManager;
import de.htwg.seapal.R;
import de.htwg.seapal.controller.IMainController;
import de.htwg.seapal.model.IBoat;
import de.htwg.seapal.model.IModel;
import roboguice.fragment.RoboFragment;

/**
 * Created by jakub on 11/28/13.
 */
public class LogbookSlideFragment extends RoboFragment {

    public static final int BOAT_VIEW_FRAGMENT = 1;
    public static final int TRIP_LIST_FRAGMENT = 0;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private  TripListFragment mTripListFragment;
    private  BoatViewFragment mBoatViewFragment;

    @Inject
    private IMainController mainController;

    @Inject
    private SessionManager sessionManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.logbook_detail_view_pager, container, false);
        if (rootView != null) {
            mPager = (ViewPager) rootView.findViewById(R.id.logbook_detail_view_pager);
        }
        mPagerAdapter = new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);


        mTripListFragment = new TripListFragment();
        mBoatViewFragment = new BoatViewFragment();


        return rootView;

    }

    public void updateBoatView(int position, UUID uuid) {
        Collection<? extends IModel> l = mainController.getSingleDocument("boat", sessionManager.getSession(), uuid);
        if (!l.isEmpty() && l.iterator().hasNext()) {
            IBoat boat = (IBoat) l.iterator().next();
            String boatName = boat.getName();
            if (StringUtils.isEmpty(boatName)) {
                mPager.setCurrentItem(BOAT_VIEW_FRAGMENT);
            } else {
                mPager.setCurrentItem(TRIP_LIST_FRAGMENT);

            }
            if (mBoatViewFragment != null) {
                mBoatViewFragment.updateBoatView(position, uuid);
            }
            if (mTripListFragment != null) {
                mTripListFragment.updateTripView(position, uuid);


            }
        }

    }

    public void onNewBoat() {
        BoatListFragment boatListFragment = (BoatListFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.boat_list_fragment);
        if (boatListFragment != null) {
            boatListFragment.onNewBoat();
        }
    }

    public void onDeleteBoat() {
        BoatListFragment boatListFragment = (BoatListFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.boat_list_fragment);
        if (boatListFragment != null) {
            boatListFragment.onDeleteBoat();
        }
    }

    public void onSaveBoat() {
        BoatListFragment boatListFragment = (BoatListFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.boat_list_fragment);
        if (boatListFragment != null) {
            boatListFragment.onSaveBoat(mBoatViewFragment);
        }

    }

    public void onFavourBoat() {
        BoatListFragment boatListFragment = (BoatListFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.boat_list_fragment);
        if (boatListFragment != null) {
            boatListFragment.onFavourBoat();
        }

    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private static final int NUM_PAGES = 2;

        public ScreenSlidePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case TRIP_LIST_FRAGMENT:
                    return mTripListFragment;
                case BOAT_VIEW_FRAGMENT:
                    return mBoatViewFragment;
                default:
                    return new Fragment();
            }

        }
    }


}
