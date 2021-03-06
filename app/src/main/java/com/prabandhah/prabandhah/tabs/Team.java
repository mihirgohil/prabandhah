package com.prabandhah.prabandhah.tabs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.R;
import com.prabandhah.prabandhah.Ui_home;
import com.prabandhah.prabandhah.Ui_team_in_DetaiView;
import com.prabandhah.prabandhah.dataclasses.Profile;
import com.prabandhah.prabandhah.dataclasses.Teams;
import com.prabandhah.prabandhah.pagerAndAdepter.AdapterForTeam;

import java.util.ArrayList;

public class Team extends Fragment {
    int role;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        //getting data from activity

        return inflater.inflate(R.layout.activity_team, container, false);
    }
    TextView team;
    RecyclerView recyclerView;
    DatabaseReference dba;
    String companyid;
    AdapterForTeam adapterForTeam;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        final SharedPreferences pref = this.getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);
        final String companyidf = pref.getString("companyid","");
        recyclerView = view.findViewById(R.id.teamlist_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if(role == 1 || role ==2){
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
                                adapterForTeam = new AdapterForTeam(getContext(),teamlist,"Team");
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
                    Toast.makeText(getContext(), "error"+databaseError, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(role == 3 || role == 4){
            final String cuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            final DatabaseReference dba =FirebaseDatabase.getInstance().getReference("Teams").child(companyidf);
            dba.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final ArrayList<Teams> teams = new ArrayList<Teams>();
                    for(final DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        final Teams teamsclass = dataSnapshot1.getValue(Teams.class);
                        dba.child(dataSnapshot1.getKey()).child("team_head").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot dataSnapshot2:dataSnapshot.getChildren()){
                                    if(cuid.equals(dataSnapshot2.getKey())){
                                        teams.add(teamsclass);
                                    }
                                }
                                dba.child(dataSnapshot1.getKey()).child("employee_list").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(DataSnapshot dataSnapshot3:dataSnapshot.getChildren()){
                                            if(cuid.equals(dataSnapshot3.getKey())){
                                                teams.add(teamsclass);
                                            }
                                        }
                                        if(teams == null){

                                        }
                                        else{
                                            adapterForTeam = new AdapterForTeam(getContext(),teams,"Team");
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

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }
}
