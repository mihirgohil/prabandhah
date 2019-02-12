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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class are_you_owner_or_admin extends AppCompatActivity {
    RadioGroup rg1;
    Button b1;
    int role,retrole;
    String ro;
    RadioButton rb1,rb2;
    DatabaseReference rootRef,dataref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_are_you_owner_or_admin);
        rg1 = findViewById(R.id.rg1);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        b1 = findViewById(R.id.btn_chose_role);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb1 = findViewById(R.id.Rb1);
                rb2=findViewById(R.id.Rb2);
                if(rb1.isChecked())
                {   role = 1 ;
                    sendrole();
                    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    dataref = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    dataref.keepSynced(true);
                    dataref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ro = dataSnapshot.child("role").getValue().toString();
                            Toast.makeText(are_you_owner_or_admin.this, "at editor" + ro, Toast.LENGTH_SHORT).show();
                            if (ro.equals("1")) {
                                // role = Integer.getInteger(ro);
                                retrole = Integer.parseInt(ro);
                                editor.putInt("role", retrole);
                                editor.commit();
                                startActivity(new Intent(are_you_owner_or_admin.this, Create_companyProfile1.class));
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(are_you_owner_or_admin.this, "in cancel :"+databaseError.toString(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else if (rb2.isChecked())
                {   role = 4 ;
                    sendrole();
                    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    dataref = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    dataref.keepSynced(true);
                    dataref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ro = dataSnapshot.child("role").getValue().toString();
                            Toast.makeText(are_you_owner_or_admin.this, "at editor" + ro, Toast.LENGTH_SHORT).show();
                            if (ro.equals("4")) {
                               // role = Integer.getInteger(ro);
                                retrole = Integer.parseInt(ro);
                                editor.putInt("role", retrole);
                                editor.commit();
                                startActivity(new Intent(are_you_owner_or_admin.this, Join_your_company.class));
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(are_you_owner_or_admin.this, "in cancel :"+databaseError.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });
        //status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.statusbarBlack));//status bar or the time bar at the top
        }


    }

    void sendrole()
    {
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("role").setValue(String.valueOf(role)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(are_you_owner_or_admin.this, "role inserted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(are_you_owner_or_admin.this, "role not saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
