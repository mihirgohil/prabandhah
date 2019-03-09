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
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.dataclasses.Profile;
import com.prabandhah.prabandhah.dataclasses.Teams;
import com.prabandhah.prabandhah.pagerAndAdepter.AdapterForTeammemebers;
import com.prabandhah.prabandhah.pagerAndAdepter.AdepterForRecylerView;
import com.prabandhah.prabandhah.tabs.AdminList;

import java.util.ArrayList;

public class Ui_team_in_DetaiView extends AppCompatActivity {
    int role;
    TextView textView,teamheadname;
    FloatingActionButton fab;
    String teamid,companyid;
    AdapterForTeammemebers adepterForRecylerView;
    RecyclerView recyclerView;
    RecyclerView recyclerView1;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_team_in__detai_view);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);
        Intent intent=getIntent();
        textView=findViewById(R.id.teamname);
        teamheadname = findViewById(R.id.teamheadname);
        teamid =intent.getStringExtra("teamid");
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1 = findViewById(R.id.recycler1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        //getting company id
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile p =dataSnapshot.getValue(Profile.class);
                companyid=p.getCompany_id();
                //getting team detail
                FirebaseDatabase.getInstance().getReference("Teams").child(companyid).child(teamid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Teams teams= dataSnapshot.getValue(Teams.class);
                        textView.setText(teams.getTeam_name());
                        final ArrayList<String> adminlist = new ArrayList<String>();
                        final ArrayList<String> employeelist = new ArrayList<String>();

                        //getting admin list
                        FirebaseDatabase.getInstance().getReference("Teams").child(companyid).child(teamid).child("Admin_list").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                   for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                                   {
                                       adminlist.add(dataSnapshot1.getKey().toString());
                                   }
                                   //getting user profiles
                                    final ArrayList<Profile> adminprofiles = new ArrayList<Profile>();
                                   FirebaseDatabase.getInstance().getReference("users").addValueEventListener(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                           for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                                           {   Profile profile= dataSnapshot1.getValue(Profile.class);
                                              for(int i=0; i<adminlist.size() ; i++){
                                                  if(profile.user_id.equals("")){}
                                                  else
                                                  {if(profile.user_id.equals(adminlist.get(i))){
                                                       adminprofiles.add(profile);
                                                   }
                                                  }
                                               }
                                            //   Toast.makeText(Ui_team_in_DetaiView.this, "profile"+profile.user_id, Toast.LENGTH_SHORT).show();
                                           }
                                           adepterForRecylerView = new AdapterForTeammemebers(Ui_team_in_DetaiView.this,adminprofiles,Ui_team_in_DetaiView.class.getSimpleName());
                                           recyclerView1.setAdapter(adepterForRecylerView);
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
                        //getting employee list
                        FirebaseDatabase.getInstance().getReference("Teams").child(companyid).child(teamid).child("employee_list").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                                {
                                    employeelist.add(dataSnapshot1.getKey().toString());
                                }
                                //getting employee profile
                                final ArrayList<Profile> employeeprofiles = new ArrayList<Profile>();
                                FirebaseDatabase.getInstance().getReference("Teams").child(companyid).child(teamid).child("team_head").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        String teamheadid = null;
                                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                                        {
                                            teamheadid = dataSnapshot1.getKey();
                                        }
                                        FirebaseDatabase.getInstance().getReference().child("users").child(teamheadid).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    Profile profile1 = dataSnapshot.getValue(Profile.class);
                                                    teamheadname.setText(profile1.user_name);
                                                    employeelist.add(profile1.user_id);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        FirebaseDatabase.getInstance().getReference("users").addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                                                {   Profile profile= dataSnapshot1.getValue(Profile.class);
                                                    for(int i=0; i<employeelist.size() ; i++){
                                                        if(profile.user_id.equals("")){}
                                                        else
                                                        {if(profile.user_id.equals(employeelist.get(i))){
                                                            employeeprofiles.add(profile);

                                                        }
                                                        }
                                                    }
                                                    //  Toast.makeText(Ui_team_in_DetaiView.this, " e profile"+profile.user_id, Toast.LENGTH_SHORT).show();
                                                }
                                                adepterForRecylerView = new AdapterForTeammemebers(Ui_team_in_DetaiView.this,employeeprofiles,Ui_team_in_DetaiView.class.getSimpleName());
                                                recyclerView.setAdapter(adepterForRecylerView);
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
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fab = findViewById(R.id.fabtn);
        uichange(role);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(getApplicationContext(),Ui_add_EmpInTeam.class));
             finish();
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
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Ui_home.class);
        startActivity(intent);
        finish();
    }
}
