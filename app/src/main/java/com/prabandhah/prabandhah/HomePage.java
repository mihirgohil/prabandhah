package com.prabandhah.prabandhah;

import android.app.SearchManager;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
//sample for beta testing
//Implementing the interface OnTabSelectedListener to our MainActivity
//This interface would help in swiping views
public class HomePage extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    //This is our tablayout
    private TabLayout tabLayout;
    Toolbar toolbar;
    //This is our viewPager
    private ViewPager viewPager;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //floating action button
        fab = findViewById(R.id.fabtn);
        //Adding toolbar to the activity
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Initializing the tablayout
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutF);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Event"));
        tabLayout.addTab(tabLayout.newTab().setText("Team"));
        tabLayout.addTab(tabLayout.newTab().setText("Chat"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = findViewById(R.id.pager);

        //Creating our pager adapter
        pager adapter = new pager(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        tabLayout.setOnTabSelectedListener(this);
        if(viewPager.getCurrentItem() == 1)
        {
           fab.setImageResource(R.drawable.ic_create_group_button);
        }
        if(viewPager.getCurrentItem() == 2)
        {
            fab.setImageResource(R.drawable.ic_chat);
        }
        if(viewPager.getCurrentItem() == 0)
        {
            fab.setImageResource(R.drawable.ic_add_black_24dp);
        }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    //menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}