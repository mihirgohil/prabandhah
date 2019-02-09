package com.prabandhah.prabandhah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.dataclasses.Profile;

public class UI_Profile_details extends AppCompatActivity {
    ImageView bckbtn,editbtn;
    DatabaseReference dataref;
    Button joincmp;
    TextView name,post,companyname,email;
    int role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui__profile_details);
        Intent intent = getIntent();
        bckbtn = findViewById(R.id.pro_png_backbtn);
        editbtn = findViewById(R.id.editBtn);
        joincmp = findViewById(R.id.joinbtn);
        name= findViewById(R.id.name);
        post = findViewById(R.id.Post);
        companyname = findViewById(R.id.companyName);
        email = findViewById(R.id.email);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ui_home.class);
                startActivity(intent);
                finish();
            }
        });
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ui_profile_details_edit.class);
                startActivity(intent);
                finish();
            }
        });
        getprofile();
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.statusbarBlack));//status bar or the time bar at the top
        }
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Ui_home.class);
        startActivity(intent);
        finish();
    }
    void getprofile()
    {

        final DatabaseReference cmpRef = FirebaseDatabase.getInstance().getReference("CompanyMaster");
        final DatabaseReference role = FirebaseDatabase.getInstance().getReference("role");
        dataref = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dataref.keepSynced(true);
        dataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Profile newUserProfilef= dataSnapshot.getValue(Profile.class);
                name.setText(newUserProfilef.getUser_name());
                email.setText(newUserProfilef.getUser_mail_id());
                post.setText(newUserProfilef.getRole());
                final String cid = dataSnapshot.child("company_id").getValue().toString();
                if (cid.equals("")) {
                    String jon="join your Company";
                    companyname.setText(jon);
                    joincmp.setVisibility(View.VISIBLE);
                }
                else {
                    cmpRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            companyname.setText(dataSnapshot.child(cid).child("company_name").getValue().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(UI_Profile_details.this, "in cancel cmp :" + databaseError.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
                role.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        post.setText(dataSnapshot.child(newUserProfilef.getRole()).getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(UI_Profile_details.this, "in cancel role :"+databaseError.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UI_Profile_details.this, "in cancel :"+databaseError.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
