package com.prabandhah.prabandhah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.dataclasses.EventClass;
import com.prabandhah.prabandhah.dataclasses.Profile;

public class Ui_Detail_ViewOfEvent extends AppCompatActivity {
    ImageView bckbtn;
    TextView nameofevent;
    TextView typeofevent;
    TextView startdate;
    TextView enddate;
    TextView starttime;
    TextView endtime;
    TextView noofguest;
    TextView budget;
    TextView address;
    TextView des;
    Toolbar toolbar;
    String eventid;
    int role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui__detail__view_of_event);
        Intent intent= getIntent();
        eventid=intent.getStringExtra("eventid");
        nameofevent = findViewById(R.id.nameofevent);
        typeofevent = findViewById(R.id.eventtype);
        startdate = findViewById(R.id.strdate);
        enddate = findViewById(R.id.enddate);
        starttime = findViewById(R.id.starttime);
        endtime = findViewById(R.id.endtime);
        noofguest = findViewById(R.id.noofguest);
        budget = findViewById(R.id.budget);
        address = findViewById(R.id.address);
        des = findViewById(R.id.description);
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile profile = dataSnapshot.getValue(Profile.class);
                FirebaseDatabase.getInstance().getReference("EventMaster").child(profile.company_id).child(eventid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        EventClass event= dataSnapshot.getValue(EventClass.class);
                        nameofevent.setText(event.eventname);
                        typeofevent.setText(event.eventype);
                        startdate.setText(event.startdate);
                        //starttime.setText(event.starttime);
                        enddate.setText(event.enddate);
                        endtime.setText(event.endtime);
                        noofguest.setText(event.noofguest);
                        budget.setText(event.budget);
                        address.setText(event.address);
                        des.setText(event.description);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        bckbtn = findViewById(R.id.createEvent_png_backbtn);
        //getting role
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ui_home.class);

                startActivity(intent);
                finish();
            }
        });

        //Adding toolbar to the activity
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Ui_home.class);
        startActivity(intent);
        finish();
    }
    //menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(role == 1) {
            inflater.inflate(R.menu.team_included_in_event, menu);
            inflater.inflate(R.menu.editbtn, menu);
        }
        else if(role == 2)
        {
        }
        return super.onCreateOptionsMenu(menu);
    }
    // menu item click
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            //for admin
            case R.id.menu_toolbarincludedTeam:
                Intent intent=new Intent(getApplicationContext(),Ui_incluededTeams.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_toolbareditbtn:
                intent = new Intent(getApplicationContext(), Ui_edit_Detail_of_event.class);
                startActivity(intent);
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

