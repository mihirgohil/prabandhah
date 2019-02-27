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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.dataclasses.Profile;
import com.prabandhah.prabandhah.dataclasses.Teams;
import com.prabandhah.prabandhah.pagerAndAdepter.AdepterForRecylerView;

import java.util.ArrayList;

public class Ui_createTeamProfile extends AppCompatActivity {
    int role;
    ImageView bckbtn;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    RecyclerView recyclerView1;
    DatabaseReference reference;
    String companyid;
    EditText nameteam;
    ArrayList<Profile> list;
    ArrayList<Profile> adminlist = new ArrayList<Profile>();
    ArrayList<String> selectedlist;
    AdepterForRecylerView adepterForRecylerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_create_team_profile);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);
        bckbtn = findViewById(R.id.createTeam_png_backbtn);
        fab = findViewById(R.id.fabtn_create_Team);
        nameteam = findViewById(R.id.nameofteam);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        selectedlist=(ArrayList<String>)bundle.getSerializable("list");

        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Ui_home.class);
                startActivity(intent);
                finish();
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DatabaseReference dba = FirebaseDatabase.getInstance().getReference("Teams").child(companyid).push();
                final String teamid=dba.getKey();
                Teams team = new Teams(teamid,nameteam.getText().toString());
                dba.setValue(team);
               //DatabaseReference dbaadmin= FirebaseDatabase.getInstance().getReference("Teams").child(companyid).child(teamid).child("admin_list");
                //forloopforaddings the admins
               for(int i=0;i<adminlist.size();i++){
                    dba.child("employee_list").child(adminlist.get(i).getUser_id()).child("admin_permission").setValue("true");
                }
                //forloopforaddings the employee
                for(int i=0;i<list.size();i++){
                    dba.child("employee_list").child(adminlist.get(i).getUser_id()).child("admin_permission").setValue("false");
                }
                Intent intent=new Intent(getApplicationContext(),Ui_home.class);
                startActivity(intent);
                finish();
            }
        });
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1 = findViewById(R.id.recycler1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
       DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Profile profile = dataSnapshot.getValue(Profile.class);
                companyid = profile.getCompany_id();
                reference = FirebaseDatabase.getInstance().getReference("users");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list = new ArrayList<Profile>();
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            Profile p = dataSnapshot1.getValue(Profile.class);
                            if(profile.getCompany_id().equals(p.getCompany_id()))
                            {   for(int i=0;i<selectedlist.size();i++)
                                { if(p.getUser_id().equals(selectedlist.get(i)))
                                {
                                    list.add(p);
                                }
                                }
                                if(p.getRole().equals("1") || p.getRole().equals("2"))
                                {
                                    adminlist.add(p);
                                }
                            }
                        }
                        StringBuffer str = new StringBuffer();
                       for(int i = 0 ; i<list.size();i++){
                           str.append(list.get(i).user_id);
                       }
                        Toast.makeText(Ui_createTeamProfile.this, "Selected List"+str.toString(), Toast.LENGTH_SHORT).show();
                        adepterForRecylerView = new AdepterForRecylerView(Ui_createTeamProfile.this,list,Ui_createTeamProfile.class.getSimpleName());
                        recyclerView.setAdapter(adepterForRecylerView);
                        adepterForRecylerView = new AdepterForRecylerView(Ui_createTeamProfile.this,adminlist,Ui_createTeamProfile.class.getSimpleName());
                        recyclerView1.setAdapter(adepterForRecylerView);
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
