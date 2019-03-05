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
import com.prabandhah.prabandhah.pagerAndAdepter.AdapterForEventlist;

import java.util.ArrayList;

public class Event extends Fragment{
    int role;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        //getting data from activity
        SharedPreferences pref = this.getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);
        return inflater.inflate(R.layout.activity_event, container, false);
    }
    TextView event;
    RecyclerView recyclerView;
    ArrayList<EventClass> eventlist;
    AdapterForEventlist adapterForEventlist;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        event = view.findViewById(R.id.EventListDetailsample);
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ui_Detail_ViewOfEvent.class));
            }
        });
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile profile = dataSnapshot.getValue(Profile.class);
                FirebaseDatabase.getInstance().getReference("EventMaster").child(profile.company_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        eventlist = new ArrayList<EventClass>();
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            EventClass eventclass= dataSnapshot1.getValue(EventClass.class);
                            if(eventclass.eventstatus.equals("assigned")){
                                eventlist.add(eventclass);
                            }

                        }
                        if(eventlist == null){}
                        else{

                        adapterForEventlist = new AdapterForEventlist(getContext(),eventlist,"Event");
                        recyclerView.setAdapter(adapterForEventlist);}
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
