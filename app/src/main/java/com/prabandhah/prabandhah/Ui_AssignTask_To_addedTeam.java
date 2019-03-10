package com.prabandhah.prabandhah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.dataclasses.EventClass;
import com.prabandhah.prabandhah.dataclasses.Profile;
import com.prabandhah.prabandhah.dataclasses.Task;
import com.prabandhah.prabandhah.dataclasses.Teams;

public class Ui_AssignTask_To_addedTeam extends AppCompatActivity {
    FloatingActionButton fab;
    TextView teamname;
    EditText task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui__assign_task__to_added_team);
        fab = findViewById(R.id.fabtn);
        teamname = findViewById(R.id.nameofteam);
        task = findViewById(R.id.taskforteamhead);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        final String companyid = pref.getString("companyid","");
        Intent intent = getIntent();
        final String eventid= intent.getStringExtra("eventid");
        final String teamid = intent.getStringExtra("teamid");

        FirebaseDatabase.getInstance().getReference("Teams").child(companyid).child(teamid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Teams teams = dataSnapshot.getValue(Teams.class);
                teamname.setText(teams.team_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dba=FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(eventid).child("teamid").child(teamid).child("task").push();
                String taskid= dba.getKey();
                Task task1 = new Task(taskid,task.getText().toString(),"assigned",teamid);
                dba.setValue(task1);
                Intent intent = new Intent(getApplicationContext(), Ui_Detail_ViewOfEvent.class);
                intent.putExtra("eventid",eventid);
                startActivity(intent);
                finish();
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
