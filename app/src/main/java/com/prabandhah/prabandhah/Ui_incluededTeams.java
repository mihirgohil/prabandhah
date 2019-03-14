package com.prabandhah.prabandhah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ThrowOnExtraProperties;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.pagerAndAdepter.AdapterForIncludedteam;
import com.prabandhah.prabandhah.pagerAndAdepter.AdapterForTeam;

import java.util.ArrayList;

public class Ui_incluededTeams extends AppCompatActivity {
    ImageView bckbtn;
    RecyclerView recyclerView;
    DatabaseReference dba;
    ArrayList<String> incluededteam;
    AdapterForIncludedteam adapterForIncludedteam;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_inclueded_teams);
        bckbtn = findViewById(R.id.inclued_png_backbtn);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        final String eventid= intent.getStringExtra("eventid");
        dba = FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(eventid).child("teamid");
                dba.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                incluededteam = new ArrayList<String>();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    incluededteam.add(dataSnapshot1.getKey().toString());
                }
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                final SharedPreferences.Editor editor = pref.edit();
                String companyid = pref.getString("companyid","");
                adapterForIncludedteam = new AdapterForIncludedteam(Ui_incluededTeams.this,incluededteam,companyid,eventid);
                recyclerView.setAdapter(adapterForIncludedteam);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ui_Detail_ViewOfEvent.class);
                intent.putExtra("eventid",eventid);
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
