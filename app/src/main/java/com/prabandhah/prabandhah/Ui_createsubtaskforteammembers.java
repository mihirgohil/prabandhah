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
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.dataclasses.Profile;
import com.prabandhah.prabandhah.dataclasses.SubTask;
import com.prabandhah.prabandhah.dataclasses.SubTaskemp;
import com.prabandhah.prabandhah.pagerAndAdepter.AdapterForTeammemebers;
import com.prabandhah.prabandhah.pagerAndAdepter.AdapterforSubtaskmembers;

import java.util.ArrayList;

public class Ui_createsubtaskforteammembers extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText task;
    ArrayList<Profile> profiles;
    FloatingActionButton fab;
    AdapterforSubtaskmembers adapterForTeammemebers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_createsubtaskforteammembers);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        fab = findViewById(R.id.fabtn);
        task = findViewById(R.id.task);
        final Intent intent= getIntent();
        final String teamid =intent.getStringExtra("teamid");
        final String taskid = intent.getStringExtra("taskid");
        final String eventid = intent.getStringExtra("eventid");
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        String companyid = pref.getString("companyid","");
        FirebaseDatabase.getInstance().getReference("Teams").child(companyid).child(teamid).child("employee_list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                profiles = new ArrayList<Profile>();
              for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                  FirebaseDatabase.getInstance().getReference("users").child(dataSnapshot1.getKey()).addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           Profile p =dataSnapshot.getValue(Profile.class);
                           profiles.add(p);
                           adapterForTeammemebers = new AdapterforSubtaskmembers(Ui_createsubtaskforteammembers.this,profiles);
                           recyclerView.setAdapter(adapterForTeammemebers);
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> selectedmember = new ArrayList<String>();
                selectedmember = adapterForTeammemebers.getselected();
               if(!selectedmember.isEmpty())
               {  DatabaseReference dba= FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(eventid).child("teamid").child(teamid).child("task").child(taskid).child("subtask").push();
                  String subtaskid = dba.getKey();
                  SubTask subTask= new SubTask(subtaskid,task.getText().toString(),"assigned",teamid,taskid);
                  dba.setValue(subTask);
                  dba = dba.child("employeefortask");
                  for(int i=0;i<selectedmember.size();i++){
                      SubTaskemp subTaskemp = new SubTaskemp(selectedmember.get(i),subtaskid,"assigned",task.getText().toString(),taskid,eventid,teamid);
                      dba.child(selectedmember.get(i)).setValue(subTaskemp);
                  }
                  startActivity(new Intent(Ui_createsubtaskforteammembers.this,Ui_home.class));
                  finish();
                  Toast.makeText(Ui_createsubtaskforteammembers.this, "taskdone", Toast.LENGTH_SHORT).show();
               }
            }
        });

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Ui_home.class);
        startActivity(intent);
        finish();
    }
}
