package com.prabandhah.prabandhah;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.dataclasses.SubTask;
import com.prabandhah.prabandhah.dataclasses.Task;
import com.prabandhah.prabandhah.pagerAndAdepter.AdapterforSubtask;

import java.util.ArrayList;

public class Ui_subtaskForteammembers extends AppCompatActivity {
    ImageView bckbtn;
    TextView nameoftask,status;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    AdapterforSubtask adapterforSubtask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_subtask_forteammembers);
        bckbtn = findViewById(R.id.createEvent_png_backbtn);
        nameoftask = findViewById(R.id.nameoftask);
        status = findViewById(R.id.status);
        fab = findViewById(R.id.fabtn);
        recyclerView = findViewById(R.id.recycler);
        final Intent intent = getIntent();
        final String eventid = intent.getStringExtra("eventid");
        final String taskid = intent.getStringExtra("taskid");
        final String teamid = intent.getStringExtra("teamid");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(eventid).child("teamid").child(teamid).child("task").child(taskid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Task task=dataSnapshot.getValue(Task.class);
                nameoftask.setText(task.task);
                status.setText(task.taskstatus);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final ArrayList<SubTask> tasks= new ArrayList<SubTask>();
        FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(eventid).child("teamid").child(teamid).child("task").child(taskid).child("subtask").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                   SubTask subTask=dataSnapshot1.getValue(SubTask.class);
                   tasks.add(subTask);
               }
               adapterforSubtask = new AdapterforSubtask(Ui_subtaskForteammembers.this,tasks,eventid);
               recyclerView.setAdapter(adapterforSubtask);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Ui_subtaskForteammembers.this,Ui_createsubtaskforteammembers.class);
                intent1.putExtra("teamid",teamid);
                intent1.putExtra("taskid",taskid);
                intent1.putExtra("eventid",eventid);
                startActivity(intent1);
                finish();
            }
        });
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Ui_home.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
