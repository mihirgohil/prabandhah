package com.prabandhah.prabandhah;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.prabandhah.prabandhah.tabs.Task;
import com.prabandhah.prabandhah.tabs.Event;
import com.prabandhah.prabandhah.tabs.Team;


public class pager extends FragmentStatePagerAdapter {
    int tabCount;
    private Context context;
    public pager(FragmentManager fm, int tabCount,Context c) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
        this.context = c;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        SharedPreferences pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        int role = pref.getInt("role",0);
        //Returning the current tabs
        switch (position) {
            case 0:

                if(role == 4){
                    Task task = new Task();
                    return task;
                }
                else {
                    Event event = new Event();
                    return event;
                }

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
