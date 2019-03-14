package com.prabandhah.prabandhah.tabs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.R;
import com.prabandhah.prabandhah.Ui_Detail_ViewOfEvent;
import com.prabandhah.prabandhah.Ui_team_in_DetaiView;
import com.prabandhah.prabandhah.dataclasses.EventClass;
import com.prabandhah.prabandhah.dataclasses.Profile;
import com.prabandhah.prabandhah.dataclasses.Teams;
import com.prabandhah.prabandhah.pagerAndAdepter.AdapterForEventlist;
import com.prabandhah.prabandhah.pagerAndAdepter.AdapterForTeam;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class Event extends Fragment{
    int role;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        //getting data from activity

        return inflater.inflate(R.layout.activity_event, container, false);
    }
    TextView event;
    RecyclerView recyclerView;
    ArrayList<EventClass> eventlist;
    AdapterForEventlist adapterForEventlist;
    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        final SharedPreferences pref = this.getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Profile profile = dataSnapshot.getValue(Profile.class);
                if(profile.role.equals("1") || profile.role.equals("2"))
                {
                    FirebaseDatabase.getInstance().getReference("EventMaster").child(profile.company_id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            eventlist = new ArrayList<EventClass>();
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                final EventClass eventclass= dataSnapshot1.getValue(EventClass.class);
                                //for admin all event will be showen
                                if (profile.role.equals("1")){
                                    if(eventclass.eventstatus.equals("assigned")){
                                        eventlist.add(eventclass);
                                    }
                                }
                                //for event manger only included event will shown
                                if (profile.role.equals("2")){
                                    if(eventclass.eventmanager.equals(profile.user_id)){
                                        if(eventclass.eventstatus.equals("assigned")){
                                            eventlist.add(eventclass);
                                        }
                                    }

                                }


                            }
                            if(eventlist == null){}
                            else{

                                adapterForEventlist = new AdapterForEventlist(getContext(),eventlist,"Event","1");
                                recyclerView.setAdapter(adapterForEventlist);}
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else
                    {   String companyidf = pref.getString("companyid","");
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
                                                if(profile.user_id.equals(dataSnapshot2.getKey())){
                                                    teams.add(teamsclass);
                                                }
                                            }
                                            FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    ArrayList<String> eventid = new ArrayList<String>();

                                                    for(final DataSnapshot dataSnapshot2:dataSnapshot.getChildren()){
                                                         FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(dataSnapshot2.getKey()).child("teamid").addListenerForSingleValueEvent(new ValueEventListener() {
                                                             @Override
                                                             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                 for(DataSnapshot dataSnapshot3:dataSnapshot.getChildren()){
                                                                     eventlist = new ArrayList<EventClass>();
                                                                     for(int i = 0;i<teams.size();i++){

                                                                         if(dataSnapshot3.getKey().equals(teams.get(i).team_id)){
                                                                             FirebaseDatabase.getInstance().getReference("EventMaster").child(profile.company_id).child(dataSnapshot2.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                 @Override
                                                                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                                     EventClass eventClass =dataSnapshot.getValue(EventClass.class);
                                                                                     EventClass m = new EventClass();
                                                                                     if(eventClass.eventstatus.equals("assigned")){
                                                                                        eventlist.add(eventClass);
                                                                                     }
                                                                                     if(eventlist ==null){
                                                                                         Toast.makeText(getActivity(), "list null", Toast.LENGTH_SHORT).show();
                                                                                     }
                                                                                     else {
                                                                                         if(eventlist == null){

                                                                                         }else{
                                                                                             //Toast.makeText(getActivity(), "str"+stringBuffer.toString(), Toast.LENGTH_SHORT).show();
                                                                                             ArrayList<EventClass> selectedevent = new ArrayList<EventClass>();
                                                                                             for(int j=0 ; j<eventlist.size();j++){
                                                                                                 for(int i1=j+1;i1<eventlist.size();i1++){
                                                                                                     if(eventlist.get(j).eventid.equals(eventlist.get(i1).eventid)){
                                                                                                         eventlist.remove(i1);
                                                                                                     }
                                                                                                 }
                                                                                                 adapterForEventlist = new AdapterForEventlist(getContext(),eventlist,"Event","3");
                                                                                                 recyclerView.setAdapter(adapterForEventlist);
                                                                                             }


                                                                                         }

                                                                                     }
                                                                                 }

                                                                                 @Override
                                                                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                                 }
                                                                             });
                                                                         }
                                                                     }

                                                                 }
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

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
