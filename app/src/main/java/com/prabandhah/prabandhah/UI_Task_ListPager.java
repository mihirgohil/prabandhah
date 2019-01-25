package com.prabandhah.prabandhah;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.prabandhah.prabandhah.tabs.Assing_event;
import com.prabandhah.prabandhah.tabs.Completed_event;
import com.prabandhah.prabandhah.tabs.Delay_event;


public class UI_Task_ListPager extends FragmentStatePagerAdapter {
    int tabCount;
    public UI_Task_ListPager(FragmentManager fm, int tabCount) {
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
            case 2:
                Delay_event delay_event = new Delay_event();
                return delay_event;
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
