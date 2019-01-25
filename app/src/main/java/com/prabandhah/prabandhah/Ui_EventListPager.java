package com.prabandhah.prabandhah;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.prabandhah.prabandhah.tabs.Assing_event;
import com.prabandhah.prabandhah.tabs.Completed_event;


public class Ui_EventListPager extends FragmentStatePagerAdapter {
    int tabCount;
    public Ui_EventListPager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;

    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                Assing_event assing_event = new Assing_event();
                return assing_event;
            case 1:
                Completed_event completed_event = new Completed_event();
                return completed_event;

            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
