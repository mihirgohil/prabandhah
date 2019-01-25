package com.prabandhah.prabandhah;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.prabandhah.prabandhah.tabs.AdminList;
import com.prabandhah.prabandhah.tabs.Chat;
import com.prabandhah.prabandhah.tabs.EmpList;
import com.prabandhah.prabandhah.tabs.Event;
import com.prabandhah.prabandhah.tabs.EventMangerList;
import com.prabandhah.prabandhah.tabs.Team;

public class UI_employeeListPager extends FragmentStatePagerAdapter {
    int tabCount;
    public UI_employeeListPager(FragmentManager fm, int tabCount) {
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
                EventMangerList eventmanger = new EventMangerList();
                return eventmanger;
            case 1:
                EmpList empList = new EmpList();
                return empList;
            case 2:
                 AdminList adminList= new AdminList();
                return adminList;
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
