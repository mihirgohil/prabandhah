package com.prabandhah.prabandhah;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.prabandhah.prabandhah.tabs.Team;

//sample for beta testing
//Implementing the interface OnTabSelectedListener to our MainActivity
//This interface would help in swiping views
public class Ui_home extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    int role;
    String str;
    TextView textView;
    //This is our tablayout
    private TabLayout tabLayout;
    Toolbar toolbar;
    //This is our viewPager
    private ViewPager viewPager;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_home);
        textView =findViewById(R.id.text);
        //search view

        //getting role
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);
        // floating action button
        fab = findViewById(R.id.fabtn);
        //Adding toolbar to the activity
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //Initializing the tablayout
        tabLayout = findViewById(R.id.tabLayoutF);
        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Event"));
        tabLayout.addTab(tabLayout.newTab().setText("Team"));
      //  tabLayout.addTab(tabLayout.newTab().setText("Chat"));
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

        //floating action button click
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionBtnClick();
            }
        });
        //ui changer fuction
        uichange(role);
    }
    public void uichange(int role)
    {
        //floating action button visblity
        if(role == 1)
        {   str = "admin ui";
            textView.setText(str);
            fab.show();
        }
        else if(role == 2)
        {
            str = "Event manager ui";
            textView.setText(str);
        }
        else if(role == 3)
        {
            str = "team head ui";
            textView.setText(str);
        }
        else if(role == 4)
        {
            str = "employee ui";
            textView.setText(str);
        }

    }
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        tabLayout.setOnTabSelectedListener(this);
        if(role == 1)
        {
            if(viewPager.getCurrentItem() == 0)
            {   fab.show();
                fab.setImageResource(R.drawable.ic_add_black_24dp);
            }
            if(viewPager.getCurrentItem() == 1)
            {   fab.show();
                fab.setImageResource(R.drawable.ic_create_group_button);

            }
            if(viewPager.getCurrentItem() == 2)
            {
                fab.hide();
            }
        }
        if(role == 2)
        {
            if(viewPager.getCurrentItem() == 0)
            {   fab.hide();

            }
            if(viewPager.getCurrentItem() == 1)
            {   fab.show();
                fab.setImageResource(R.drawable.ic_create_group_button);

            }
            if(viewPager.getCurrentItem() == 2)
            {
                fab.hide();
            }
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
        if(role == 1) {
            inflater.inflate(R.menu.optn_for_admin, menu);

        }
        else if(role == 2)
        {
            inflater.inflate(R.menu.optn_for_eventmanger, menu);
        }
        else if(role == 3)
        {
            inflater.inflate(R.menu.optn_for_eventmanger, menu);
        }

        else if(role == 4)
        {
            inflater.inflate(R.menu.optn_for_emp, menu);
        }
        inflater.inflate(R.menu.notification,menu);
        //inflater.inflate(R.menu.search,menu);
        return super.onCreateOptionsMenu(menu);
    }
   // menu item click
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle item selection
        switch (item.getItemId()) {
            //for admin
            case R.id.menu_toolbarnotification:
                Intent intent=new Intent(getApplicationContext(),Ui_notification.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_toolbarsearch:
                SearchView searchView = (SearchView)item.getActionView();

                return true;
            //admin options
            case R.id.adm_profile:
                intent=new Intent(getApplicationContext(),UI_Profile_details.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.adm_companyProfile:
                intent=new Intent(getApplicationContext(),Ui_companyProfile.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.adm_empList:
                intent=new Intent(getApplicationContext(),Ui_employeeList.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.adm_eventList:
                intent=new Intent(getApplicationContext(),Ui_eventList.class);
                startActivity(intent);
                finish();
                return true;
           /* case R.id.adm_teamList:
                intent=new Intent(getApplicationContext(),Ui_teamList.class);
                intent.putExtra("selected",role);
                startActivity(intent);
                finish();
                return true;*/
            case R.id.adm_taskList:
                intent=new Intent(getApplicationContext(),Ui_taskList.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.adm_setting:
                intent=new Intent(getApplicationContext(),Ui_setting.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.Logout:
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("LogOut!")
                        .setMessage("Are you sure you want to Logout?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth firebaseAuth =FirebaseAuth.getInstance();
                                firebaseAuth.signOut();
                                startActivity(new Intent(Ui_home.this,LoginPage.class));
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //floating action button click
    public void actionBtnClick()
    {
        if(viewPager.getCurrentItem() == 0)
        {   Intent intent = getIntent();
            intent=new Intent(getApplicationContext(),Ui_createEvent.class);
            startActivity(intent);
        }
        if(viewPager.getCurrentItem() == 1)
        {
            Intent intent = getIntent();
            intent=new Intent(getApplicationContext(),Ui_createTeam.class);
            startActivity(intent);

        }

    }


}
