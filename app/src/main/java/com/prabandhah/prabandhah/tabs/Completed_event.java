package com.prabandhah.prabandhah.tabs;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.R;
import com.prabandhah.prabandhah.dataclasses.EventClass;
import com.prabandhah.prabandhah.dataclasses.Profile;
import com.prabandhah.prabandhah.pagerAndAdepter.AdapterForEventlist;

import java.util.ArrayList;

public class Completed_event extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.activity_completed_event, container, false);
    }
    RecyclerView recyclerView;
    ArrayList<EventClass> eventlist;
    AdapterForEventlist adapterForEventlist;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                            if(eventclass.eventstatus.equals("completed")){
                                eventlist.add(eventclass);
                            }

                        }
                        if(eventlist == null){}
                        else{

                            adapterForEventlist = new AdapterForEventlist(getContext(),eventlist,"Completed_event");
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
