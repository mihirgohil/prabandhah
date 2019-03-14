package com.prabandhah.prabandhah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.dataclasses.Profile;
import com.prabandhah.prabandhah.dataclasses.Teams;
import com.prabandhah.prabandhah.pagerAndAdepter.AdapterForTeam;

import java.util.ArrayList;

public class Ui_addTeamToEvent extends AppCompatActivity {
    FloatingActionButton fab;
    RecyclerView recyclerView;
    DatabaseReference dba;
    String companyid;

    AdapterForTeam adapterForTeam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_add_team_to_event);
        fab = findViewById(R.id.fabtn);
        Intent intent = getIntent();
        final String eventid= intent.getStringExtra("eventid");
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile profile=dataSnapshot.getValue(Profile.class);
                companyid = profile.getCompany_id();
                //Toast.makeText(getContext(), "in snap"+companyid, Toast.LENGTH_SHORT).show();
                dba = FirebaseDatabase.getInstance().getReference("Teams").child(companyid);
                //Toast.makeText(getContext(), "cmpid"+companyid, Toast.LENGTH_SHORT).show();
                //getting team list
                dba.addListenerForSingleValueEvent(new ValueEventListener() {
                    ArrayList<Teams> teamlist = new ArrayList<Teams>();
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                            Teams tm = dataSnapshot1.getValue(Teams.class);
                            teamlist.add(tm);
                        }
                        if(teamlist == null){

                        }
                        else{
                            adapterForTeam = new AdapterForTeam(Ui_addTeamToEvent.this,teamlist,Ui_addTeamToEvent.class.getSimpleName());
                            recyclerView.setAdapter(adapterForTeam);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Ui_addTeamToEvent.this, "error"+databaseError, Toast.LENGTH_SHORT).show();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedteam = adapterForTeam.getselectedteamid();
                Intent intent = new Intent(getApplicationContext(), Ui_AssignTask_To_addedTeam.class);
                intent.putExtra("eventid",eventid);
                intent.putExtra("teamid",selectedteam);
                startActivity(intent);
                finish();
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
