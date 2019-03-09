package com.prabandhah.prabandhah;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.prabandhah.prabandhah.tabs.Task;
import com.prabandhah.prabandhah.tabs.Event;
import com.prabandhah.prabandhah.tabs.Team;


public class pager extends FragmentStatePagerAdapter {
    int tabCount;
    public pager(FragmentManager fm, int tabCount) {
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
                Event event = new Event();
                return event;
            case 1:
                Team team = new Team();
                return team;
            case 2:
                Task task = new Task();
                return task;
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
