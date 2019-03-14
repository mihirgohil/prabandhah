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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.dataclasses.Company;

public class Ui_company_profile_edit extends AppCompatActivity {
    int role;
    ImageView bckbtn;
    EditText companyname,companyaddress,companyemail;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_company_profile_edit);
        bckbtn = findViewById(R.id.pro_png_backbtn);
        save = findViewById(R.id.saveBtn);
        companyname = findViewById(R.id.companyName);
        companyaddress = findViewById(R.id.companyAdress);
        companyemail = findViewById(R.id.companyemail);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);

        final String companyid = pref.getString("companyid","");
        FirebaseDatabase.getInstance().getReference("CompanyMaster").child(companyid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Company company=dataSnapshot.getValue(Company.class);
                companyname.setText(company.company_name);
                companyemail.setText(company.company_mail);
                companyaddress.setText(company.company_address);
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ui_companyProfile.class);
                startActivity(intent);
                finish();

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Company editcompany= new Company(companyname.getText().toString(),companyemail.getText().toString(),companyaddress.getText().toString());
                if(!editcompany.company_name.isEmpty() && !editcompany.company_mail.isEmpty() && !editcompany.company_address.isEmpty() ){
                    FirebaseDatabase.getInstance().getReference("CompanyMaster").child(companyid).child("company_name").setValue(editcompany.company_name);
                    FirebaseDatabase.getInstance().getReference("CompanyMaster").child(companyid).child("company_mail").setValue(editcompany.company_mail);
                    FirebaseDatabase.getInstance().getReference("CompanyMaster").child(companyid).child("company_address").setValue(editcompany.company_address);
                    Intent intent = new Intent(getApplicationContext(), Ui_companyProfile.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(Ui_company_profile_edit.this, "Fill the deatils", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.statusbarBlack));//status bar or the time bar at the top
        }
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Ui_companyProfile.class);
        startActivity(intent);
        finish();
    }
}
