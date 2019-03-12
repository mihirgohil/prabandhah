package com.prabandhah.prabandhah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.dataclasses.Profile;

public class Ui_profile_details_edit extends AppCompatActivity {
    int role;
    ImageView bckbtn;
    EditText name;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_profile_details_edit);
        bckbtn = findViewById(R.id.pro_png_backbtn);
        name = findViewById(R.id.name);
        save = findViewById(R.id.saveBtn);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UI_Profile_details.class);
                intent.putExtra("selected", role);
                startActivity(intent);
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!name.getText().toString().isEmpty()){
                    FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("user_name").setValue(name.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), UI_Profile_details.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(Ui_profile_details_edit.this, "User name can't be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile p = dataSnapshot.getValue(Profile.class);
                name.setText(p.user_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.statusbarBlack));//status bar or the time bar at the top
        }

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), UI_Profile_details.class);
        startActivity(intent);
        finish();
    }
}
