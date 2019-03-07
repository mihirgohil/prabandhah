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
import com.prabandhah.prabandhah.dataclasses.Company;
import com.prabandhah.prabandhah.dataclasses.Profile;

public class Ui_companyProfile extends AppCompatActivity {
    ImageView bckbtn,editbtn;
    TextView cmpname,address,ownername,cmpemail;
    Button join;
    int role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_company_profile);
        Intent intent = getIntent();
        bckbtn = findViewById(R.id.pro_png_backbtn);
        editbtn = findViewById(R.id.editBtn);
        join = findViewById(R.id.joinbtn);
        cmpname = findViewById(R.id.cmpname);
        cmpemail = findViewById(R.id.cmpemail);
        ownername = findViewById(R.id.cmpownername);
        address = findViewById(R.id.cmpaddress);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);
        getcmpPro();
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ui_home.class);
                startActivity(intent);
                finish();
            }
        });
        if(role == 1){
            editbtn.setVisibility(View.VISIBLE);
        }
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ui_company_profile_edit.class);

                startActivity(intent);
                finish();
            }
        });

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
    void getcmpPro()
    {
        final DatabaseReference getUser = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        getUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String cid;
                cid = dataSnapshot.child("company_id").getValue().toString();
                if (cid.equals("")) {
                    String jon="join your Company";
                    cmpname.setText(jon);
                    cmpemail.setText(jon);
                    address.setText(jon);
                    ownername.setText(jon);
                    join.setVisibility(View.VISIBLE);
                } else {
                    DatabaseReference getCmp = FirebaseDatabase.getInstance().getReference("CompanyMaster").child(cid);
                    getCmp.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Company company= dataSnapshot.getValue(Company.class);
                            cmpname.setText(company.getCompany_name());
                            cmpemail.setText(company.getCompany_mail());
                            address.setText(company.getCompany_address());
                           String uid= company.getUser_id();
                            DatabaseReference getOwner = FirebaseDatabase.getInstance().getReference("users").child(uid);
                            getOwner.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String uname;
                                    uname = dataSnapshot.child("user_name").getValue().toString();
                                    ownername.setText(uname);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(Ui_companyProfile.this, "error in cmp detail" + databaseError, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Ui_companyProfile.this, "error in cmp id"+databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
