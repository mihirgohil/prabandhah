package com.prabandhah.prabandhah;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prabandhah.prabandhah.dataclasses.Company;

public class Create_companyProfile extends AppCompatActivity {
    Button done;
    EditText name,email,address;
    Boolean status=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.print("starting activity create company...");
        setContentView(R.layout.activity_create_company_profile);
        done = findViewById(R.id.donebtn);
        name = findViewById(R.id.cmpname);
        email = findViewById(R.id.cmpemail);
        address = findViewById(R.id.cmpaddress);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (email.getText().toString().trim().equalsIgnoreCase("")) {
                    email.setError("Please Enter Email");

                } else if (!email.getText().toString().matches(emailPattern)) {
                    email.setError("Please Enter valid Email");

                } else if (name.getText().toString().trim().equalsIgnoreCase("")) {
                    name.setError("Please Enter Name");
                }
                else if(address.getText().toString().trim().equalsIgnoreCase("")) {
                    address.setError("Please Enter Company Address");
                }
                else{ passdataToDb();
                        intnt();

                }
            }
        });
    }
    void intnt()
    {
        finish();
        finishAffinity();
        startActivity(new Intent(Create_companyProfile.this, Ui_home.class));
    }


    void passdataToDb()
    {  final FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference =mdatabase.getReference("CompanyMaster");
        final DatabaseReference newref = reference.push();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Company cmp = new Company(name.getText().toString(),email.getText().toString(),address.getText().toString(),uid,newref.getKey());
        newref.setValue(cmp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    //Toast.makeText(Create_companyProfile.this, "cmp data inserted", Toast.LENGTH_SHORT).show();
                    //put CompanyId In UserMaster
                    mdatabase .getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("company_id").setValue(newref.getKey()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {Toast.makeText(Create_companyProfile.this, "Cmp data user update inserted", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            else
                            {
                                Toast.makeText(Create_companyProfile.this, "Cmp data user update not inserted", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(Create_companyProfile.this, "cmp data not saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

