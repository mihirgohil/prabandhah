package com.prabandhah.prabandhah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ui_employeeList extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    ImageView bckbtn;
    int role;
    FloatingActionButton fab;
    TabLayout tabLayout;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_employee_list);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);
        fab = findViewById(R.id.fabtn);
        bckbtn = findViewById(R.id.emplist_bckbtn);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ui_home.class);
                startActivity(intent);
                finish();
            }
        });
        tabLayout = findViewById(R.id.tabLayoutE);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Event Manger"));
        tabLayout.addTab(tabLayout.newTab().setText("Employee"));
        tabLayout.addTab(tabLayout.newTab().setText("Owner/Admin"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = findViewById(R.id.pager);

        //Creating our pager adapter
        UI_employeeListPager adapter = new UI_employeeListPager(getSupportFragmentManager(), tabLayout.getTabCount());
        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //ui changer fuction
        uichange(role);
        //action btn clicked
      fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionBtnClick();
            }
        });
    }
    public void uichange(int role)
    {
        //floating action button visblity
        if(role == 1)
        {
            fab.show();
        }
        else if(role == 2)
        {

        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        tabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
    //back btn press
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Ui_home.class);
        startActivity(intent);
        finish();
    }
    public void actionBtnClick()
    {
        if(viewPager.getCurrentItem() == 0)
        {   Intent intent = getIntent();
            intent=new Intent(getApplicationContext(),Ui_AssignNewEventmanger.class);
            startActivity(intent);
            finish();
        }
        if(viewPager.getCurrentItem() == 1)
        {
            final DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String cmpcode = dataSnapshot.child("company_id").getValue().toString();
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    String Sharesub="Your Company Code:"+"\n"+cmpcode;
                    intent.putExtra(Intent.EXTRA_TEXT,Sharesub);
                    startActivity(Intent.createChooser(intent,"Invite Using"));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        if(viewPager.getCurrentItem() == 2)
        {
            Intent intent = getIntent();
            intent=new Intent(getApplicationContext(),UI_AssignNewAdmin.class);
            startActivity(intent);
            finish();
        }

    }
}
