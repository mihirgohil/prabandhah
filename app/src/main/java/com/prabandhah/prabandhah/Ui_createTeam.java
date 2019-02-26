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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.dataclasses.Profile;
import com.prabandhah.prabandhah.pagerAndAdepter.AdepterForRecylerView;

import java.util.ArrayList;

public class Ui_createTeam extends AppCompatActivity {
    int role;
    ImageView bckbtn;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    DatabaseReference reference;
    ArrayList<Profile> list;
    AdepterForRecylerView adepterForRecylerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_create_team);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);
        bckbtn = findViewById(R.id.createTeam_png_backbtn);
        fab = findViewById(R.id.fabtn_create_Team);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Ui_home.class);
                startActivity(intent);
                finish();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),Ui_createTeamProfile.class);
                startActivity(intent);
                finish();
            }
        });
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Profile profile = dataSnapshot.getValue(Profile.class);
                reference = FirebaseDatabase.getInstance().getReference("users");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list = new ArrayList<Profile>();
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            Profile p = dataSnapshot1.getValue(Profile.class);
                            if(profile.getCompany_id().equals(p.getCompany_id()))
                            {
                                if(p.getRole().equals("4") )
                                {
                                    list.add(p);
                                }
                            }
                        }
                        adepterForRecylerView = new AdepterForRecylerView(Ui_createTeam.this,list,Ui_createTeam.class.getSimpleName());
                        recyclerView.setAdapter(adepterForRecylerView);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //    Toast.makeText(getContext(), "error"+databaseError, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
