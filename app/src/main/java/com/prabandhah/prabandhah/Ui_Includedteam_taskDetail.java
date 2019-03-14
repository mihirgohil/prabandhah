package com.prabandhah.prabandhah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.dataclasses.Profile;
import com.prabandhah.prabandhah.dataclasses.Task;
import com.prabandhah.prabandhah.dataclasses.Teams;
import com.prabandhah.prabandhah.pagerAndAdepter.AdapterForIncludedTaskteamtaskrecyler;

import java.util.ArrayList;

public class Ui_Includedteam_taskDetail extends AppCompatActivity {
    TextView nameofteam,nooftask,nameofteamhead;
    ArrayList<Task> tasks;
    RecyclerView  recyclerView;
    AdapterForIncludedTaskteamtaskrecyler adapterForIncludedTaskteamtaskrecyler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui__includedteam_task_detail);
        nameofteam = findViewById(R.id.nameofteam);
        nooftask = findViewById(R.id.nooftask);
        nameofteamhead = findViewById(R.id.nameofteamhead);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent= getIntent();
        final String teamid =intent.getStringExtra("selectedteam");
        final String eventid = intent.getStringExtra("eventid");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        String companyid = pref.getString("companyid","");
       final DatabaseReference dba= FirebaseDatabase.getInstance().getReference("Teams").child(companyid).child(teamid);
       dba.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Teams teams = dataSnapshot.getValue(Teams.class);
                nameofteam.setText(teams.team_name);
                dba.child("team_head").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                      String teamheadid = null;
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                          teamheadid = dataSnapshot1.getKey();
                      }
                      FirebaseDatabase.getInstance().getReference("users").child(teamheadid).addListenerForSingleValueEvent(new ValueEventListener() {
                          @Override
                          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                              Profile p = dataSnapshot.getValue(Profile.class);
                              nameofteamhead.setText(p.user_name);
                              DatabaseReference dba1 = FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(eventid).child("teamid").child(teamid).child("task");
                              dba1.addListenerForSingleValueEvent(new ValueEventListener() {
                                  @Override
                                  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                      tasks = new ArrayList<Task>();
                                      int count = 0;
                                      for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                          Task t=dataSnapshot1.getValue(Task.class);
                                          tasks.add(t);
                                          count++;
                                      }
                                      nooftask.setText(String.valueOf(count));
                                      adapterForIncludedTaskteamtaskrecyler = new AdapterForIncludedTaskteamtaskrecyler(Ui_Includedteam_taskDetail.this,tasks);
                                      recyclerView.setAdapter(adapterForIncludedTaskteamtaskrecyler);
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Ui_home.class);
        startActivity(intent);
        finish();
    }
}
