package com.prabandhah.prabandhah;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.prabandhah.prabandhah.dataclasses.Company;
import com.prabandhah.prabandhah.dataclasses.Profile;

public class Create_companyProfile1 extends AppCompatActivity {
    Button done;
    EditText cmpname,cmpaddress,cmdemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_company_profile1);
        done = findViewById(R.id.donebtn);
        cmpname = findViewById(R.id.cmpname);
        cmdemail = findViewById(R.id.cmpemail);
        cmpaddress = findViewById(R.id.cmpaddress);
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cmpname.getText().toString().isEmpty()&&!cmdemail.getText().toString().isEmpty()&&!cmpaddress.getText().toString().isEmpty()){
                    if (cmdemail.getText().toString().matches(emailPattern)){
                        FirebaseUser fbu = FirebaseAuth.getInstance().getCurrentUser();
                        String uid=fbu.getUid();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("CompanyMaster");
                        DatabaseReference cmpid = databaseReference.push();
                        String cmpidstr = cmpid.getKey();
                        Company cmp = new Company(cmpname.getText().toString(),cmdemail.getText().toString(),cmpaddress.getText().toString(),uid,cmpid.getKey());
                        cmpid.setValue(cmp);
                        //passtouser(cmpidstr);
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
                        databaseReference.keepSynced(true);
                        Profile profile = new Profile(cmpidstr);
                        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("company_id").setValue(profile.getCompany_id()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    startActivity(new Intent(getApplicationContext(),Ui_home.class));
                                    finishAffinity();
                                }
                                else
                                {
                                    Toast.makeText(Create_companyProfile1.this, "locha", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(Create_companyProfile1.this, "Fill Details properly", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }
    void passtouser(String cmpidstr){

    }
}
