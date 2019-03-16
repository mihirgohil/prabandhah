package com.prabandhah.prabandhah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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
    TextView nameofeventManger;
    CardView addteamtoevent,tasklistforevent,taskpartnerlist;
    TextView address;
    TextView des;
    Toolbar toolbar;
    String eventid,maintask,subtaskid,teamid,prev;
    int role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui__detail__view_of_event);
        final Intent intent= getIntent();
        eventid=intent.getStringExtra("eventid");
        maintask = intent.getStringExtra("maintask");
        subtaskid = intent.getStringExtra("subtaskid");
        teamid = intent.getStringExtra("teamid");
        prev = intent.getStringExtra("prev");
        nameofevent = findViewById(R.id.nameofevent);
        typeofevent = findViewById(R.id.eventtype);
        tasklistforevent = findViewById(R.id.eventtasklist);
        taskpartnerlist = findViewById(R.id.taskpartner);
        startdate = findViewById(R.id.strdate);
        enddate = findViewById(R.id.enddate);
        starttime = findViewById(R.id.strtime);
        endtime = findViewById(R.id.endtime);
        noofguest = findViewById(R.id.noofguest);
        nameofeventManger = findViewById(R.id.nameofeventmanger);
        budget = findViewById(R.id.budget);
        address = findViewById(R.id.address);
        des = findViewById(R.id.description);
        addteamtoevent = findViewById(R.id.addteamtoevent); 
        //getting  data
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Profile profile = dataSnapshot.getValue(Profile.class);
                FirebaseDatabase.getInstance().getReference("EventMaster").child(profile.company_id).child(eventid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        EventClass event= dataSnapshot.getValue(EventClass.class);
                        nameofevent.setText(event.eventname);
                        typeofevent.setText(event.eventype);
                        startdate.setText(event.startdate);
                        Toast.makeText(Ui_Detail_ViewOfEvent.this, "starttime"+event.starttime, Toast.LENGTH_SHORT).show();
                        starttime.setText(event.starttime);
                        enddate.setText(event.enddate);
                        endtime.setText(event.endtime);
                        noofguest.setText(event.noofguest);
                        budget.setText(event.budget);
                        address.setText(event.address);
                        des.setText(event.description);
                        FirebaseDatabase.getInstance().getReference("users").child(event.eventmanager).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Profile profile1= dataSnapshot.getValue(Profile.class);
                                nameofeventManger.setText(profile1.user_name);
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
        //getting ui according  to role
        if(role == 2){
            addteamtoevent.setVisibility(View.VISIBLE);
        }
        if(role == 3){
            tasklistforevent.setVisibility(View.VISIBLE);
        }
        if(role == 3 || role == 4){
            if(prev != null && !prev.isEmpty()){
                if(prev.equals("show")){
                    tasklistforevent.setVisibility(View.INVISIBLE);
                    taskpartnerlist.setVisibility(View.VISIBLE);
                }
            }

        }

        //Adding toolbar to the activity
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        addteamtoevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Ui_Detail_ViewOfEvent.this, "add team to event", Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(Ui_Detail_ViewOfEvent.this,Ui_addTeamToEvent.class);
                intent1.putExtra("eventid",eventid);
                startActivity(intent1);
                finish();
            }
        });
        tasklistforevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Ui_Detail_ViewOfEvent.this,Ui_tasklistofteam.class);
                intent1.putExtra("eventid",eventid);
                startActivity(intent1);
                finish();
            }
        });
        taskpartnerlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Ui_Detail_ViewOfEvent.this, Ui_TaskpartnersDetail_for_emp.class);
                intent1.putExtra("eventid",eventid);
                intent1.putExtra("maintask",maintask);
                intent1.putExtra("subtaskid",subtaskid);
                intent1.putExtra("teamid",teamid);
                finish();
                startActivity(intent1);
            }
        });
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
        {   inflater.inflate(R.menu.team_included_in_event, menu);
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
                intent.putExtra("eventid",eventid);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_toolbareditbtn:
                intent = new Intent(getApplicationContext(), Ui_edit_Detail_of_event.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

