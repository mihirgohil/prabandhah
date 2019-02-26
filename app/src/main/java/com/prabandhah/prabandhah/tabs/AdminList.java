package com.prabandhah.prabandhah.tabs;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.R;
import com.prabandhah.prabandhah.dataclasses.Profile;
import com.prabandhah.prabandhah.pagerAndAdepter.AdepterForRecylerView;

import java.util.ArrayList;

public class AdminList extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.activity_admin_list, container, false);
    }
    RecyclerView recyclerView;
    DatabaseReference reference;
    ArrayList<Profile> list;
    int counter=0;
    AdepterForRecylerView adepterForRecylerView;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        recyclerView = view.findViewById(R.id.admlist_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
                                if(p.getRole().equals("1") )
                                {
                                    list.add(p);
                                }
                            }
                        }
                        counter = list.size();
                        adepterForRecylerView = new AdepterForRecylerView(getContext(),list,"AdminList");
                        recyclerView.setAdapter(adepterForRecylerView);
                        Toast.makeText(getContext(), "c"+String.valueOf(counter), Toast.LENGTH_SHORT).show();
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

