package com.prabandhah.prabandhah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.dataclasses.Task;
import com.prabandhah.prabandhah.dataclasses.Teams;
import com.prabandhah.prabandhah.pagerAndAdepter.AdapterForIncludedTaskteamtaskrecyler;
import com.prabandhah.prabandhah.pagerAndAdepter.AdpaterForTasklistteamhead;

import java.util.ArrayList;

public class Ui_tasklistofteam extends AppCompatActivity {
    ImageView bckbtn;
    RecyclerView recyclerView,recyclerView1;
    AdpaterForTasklistteamhead adapterForIncludedTaskteamtaskrecyler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_tasklistofteam);
        bckbtn = findViewById(R.id.createEvent_png_backbtn);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1 = findViewById(R.id.recycler1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        final String companyid = pref.getString("companyid","");
        final String eventid=intent.getStringExtra("eventid");
        final DatabaseReference dba =FirebaseDatabase.getInstance().getReference("Teams").child(companyid);
        dba.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String cuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                final ArrayList<Teams> teams = new ArrayList<Teams>();
                for(final DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    final Teams teamsclass = dataSnapshot1.getValue(Teams.class);
                    dba.child(dataSnapshot1.getKey()).child("team_head").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot dataSnapshot2:dataSnapshot.getChildren()){
                                if(cuid.equals(dataSnapshot2.getKey())){
                                    teams.add(teamsclass);
                                }
                            }
                            FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(eventid).child("teamid").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    final ArrayList<Task> assignedtasks= new ArrayList<Task>();
                                    final ArrayList<Task> completedtasks= new ArrayList<Task>();
                                    for(DataSnapshot dataSnapshot3:dataSnapshot.getChildren()){
                                       for(int i=0;i<teams.size();i++){
                                           if(dataSnapshot3.getKey().equals(teams.get(i).team_id)){

                                               FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(eventid).child("teamid").child(dataSnapshot3.getKey()).child("task").addValueEventListener(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                       for(DataSnapshot dataSnapshot4:dataSnapshot.getChildren()){
                                                                 Task task = dataSnapshot4.getValue(Task.class);
                                                                 if(task.taskstatus.equals("assigned")){
                                                                     assignedtasks.add(task);
                                                                 }
                                                                 else if(task.taskstatus.equals("completed")){
                                                                     completedtasks.add(task);
                                                                 }
                                                       }
                                                       adapterForIncludedTaskteamtaskrecyler = new AdpaterForTasklistteamhead(Ui_tasklistofteam.this,assignedtasks,companyid,eventid);
                                                       recyclerView.setAdapter(adapterForIncludedTaskteamtaskrecyler);
                                                       adapterForIncludedTaskteamtaskrecyler = new AdpaterForTasklistteamhead(Ui_tasklistofteam.this,completedtasks,companyid,eventid);
                                                       recyclerView1.setAdapter(adapterForIncludedTaskteamtaskrecyler);
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

        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ui_home.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
