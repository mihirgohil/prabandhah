package com.prabandhah.prabandhah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.dataclasses.SubTask;
import com.prabandhah.prabandhah.dataclasses.SubTaskemp;
import com.prabandhah.prabandhah.pagerAndAdepter.AdapterempsubtaskPartner;

import java.util.ArrayList;

public class Ui_TaskpartnersDetail_for_emp extends AppCompatActivity {
    RecyclerView recyclerView;
    AdapterempsubtaskPartner adapterempsubtaskPartner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui__taskpartners_detail_for_emp);
        Intent intent =getIntent();
        String eventid = intent.getStringExtra("eventid");
        String maintask = intent.getStringExtra("maintask");
        String subtaskid = intent.getStringExtra("subtaskid");
        String teamid = intent.getStringExtra("teamid");
        //Toast.makeText(this, "event"+eventid+"\n"+maintask+"\n"+subtaskid+"\n"+teamid, Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ArrayList<String> empid= new ArrayList<String>();
        final ArrayList<SubTaskemp> subTaskemps=new ArrayList<SubTaskemp>();
       final String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(eventid).child("teamid").child(teamid).child("task").child(maintask).child("subtask").child(subtaskid).child("employeefortask").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    if(!dataSnapshot1.getKey().equals(uid)){
                         SubTaskemp subTaskemp=dataSnapshot1.getValue(SubTaskemp.class);
                         subTaskemps.add(subTaskemp);
                    }
                }
                adapterempsubtaskPartner = new AdapterempsubtaskPartner(Ui_TaskpartnersDetail_for_emp.this,subTaskemps);
                recyclerView.setAdapter(adapterempsubtaskPartner);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
