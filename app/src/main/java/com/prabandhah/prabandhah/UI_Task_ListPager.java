package com.prabandhah.prabandhah;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.prabandhah.prabandhah.tabs.Assigned_task;
import com.prabandhah.prabandhah.tabs.Assing_event;
import com.prabandhah.prabandhah.tabs.Completed_event;
import com.prabandhah.prabandhah.tabs.Completed_task;
import com.prabandhah.prabandhah.tabs.Delay_event;
import com.prabandhah.prabandhah.tabs.Delay_task;


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
                Assigned_task assigned_task = new Assigned_task();
                return  assigned_task;
            case 1:
                Completed_task completed_task = new Completed_task();
                return completed_task;
            case 2:
                Delay_task delay_task = new Delay_task();
                return delay_task;
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
