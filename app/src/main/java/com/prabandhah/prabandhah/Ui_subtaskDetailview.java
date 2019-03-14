package com.prabandhah.prabandhah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.dataclasses.SubTask;
import com.prabandhah.prabandhah.dataclasses.SubTaskemp;
import com.prabandhah.prabandhah.pagerAndAdepter.AdapterforSubtaskemp;

import java.util.ArrayList;

public class Ui_subtaskDetailview extends AppCompatActivity {
    TextView nameoftask,status;
    AdapterforSubtaskemp adapterforSubtaskemp;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_subtask_detailview);
        Intent intent = getIntent();
        final String eventid = intent.getStringExtra("eventid");
        final String teamid = intent.getStringExtra("teamid");
        final String subtaskid = intent.getStringExtra("subtaskid");
        final String taskid = intent.getStringExtra("taskid");
        Toast.makeText(this, "eventid"+eventid+"\nteamid"+teamid+"\nsubtaskid"+subtaskid+"\ntaskid"+taskid, Toast.LENGTH_SHORT).show();
        nameoftask = findViewById(R.id.nameoftask);
        status = findViewById(R.id.status);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(eventid).child("teamid").child(teamid).child("task").child(taskid).child("subtask").child(subtaskid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SubTask subTask=dataSnapshot.getValue(SubTask.class);
                nameoftask.setText(subTask.subtask);
                status.setText(subTask.subtaskstatus);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(eventid).child("teamid").child(teamid).child("task").child(taskid).child("subtask").child(subtaskid).child("employeefortask").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<SubTaskemp> subTaskemps = new ArrayList<SubTaskemp>();
                for(final DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(eventid).child("teamid").child(teamid).child("task").child(taskid).child("subtask").child(subtaskid).child("employeefortask").child(dataSnapshot1.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Toast.makeText(Ui_subtaskDetailview.this, "j"+dataSnapshot1.getKey(), Toast.LENGTH_SHORT).show();
                            SubTaskemp subTaskemp=dataSnapshot.getValue(SubTaskemp.class);
                            subTaskemps.add(subTaskemp);
                            if(!subTaskemps.isEmpty()){

                                adapterforSubtaskemp = new AdapterforSubtaskemp(Ui_subtaskDetailview.this,subTaskemps);
                                recyclerView.setAdapter(adapterforSubtaskemp);
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
