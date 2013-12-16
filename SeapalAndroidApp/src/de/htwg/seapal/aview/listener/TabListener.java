package de.htwg.seapal.aview.listener;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * Created by jakub on 12/11/13.
 */
public class TabListener<T extends Fragment> implements ActionBar.TabListener {
    private Fragment mFragment;
    private final Activity mActivity;
    private final Class<T> mClass;
    private final int mTabsId;
    private final OnCreateOptionsMenuListener mOnCreateOptionsMenuListener;

    /** Constructor used each time a new tab is created.
     * @param idOfTabsViewResource resource id of TabView
     * @param clz  The fragment's Class, used to instantiate the fragment
     * @param activity  The host Activity, used to instantiate the fragment
     */
    public TabListener(int idOfTabsViewResource, Class<T> clz, Activity activity, OnCreateOptionsMenuListener listener ) {
        mTabsId = idOfTabsViewResource;
        mActivity = activity;
        mClass = clz;
        mOnCreateOptionsMenuListener = listener;
    }

    /* The following are each of the ActionBar.TabListener callbacks */

    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        // Check if the fragment is already initialized
        if (mFragment == null) {
            // If not, instantiate and add it to the activity
            mFragment = Fragment.instantiate(mActivity, mClass.getName());
            ft.replace(mTabsId, mFragment);
        } else {
            // If it exists, simply attach it in order to show it
            ft.attach(mFragment);
        }
        mOnCreateOptionsMenuListener.onCreateMenu();
    }

    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (mFragment != null) {
            // Detach the fragment, because another one is being attached
            ft.detach(mFragment);
        }
    }

    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        // User selected the already selected tab. Usually do nothing.
    }
}
