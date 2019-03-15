package com.prabandhah.prabandhah;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.preference.PreferenceManager;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.dataclasses.Company;
import com.prabandhah.prabandhah.dataclasses.Profile;

public class Join_your_company extends AppCompatActivity {
    Button done;
    String cid,codestr;
    EditText code;
    DatabaseReference cmp = FirebaseDatabase.getInstance().getReference().child("CompanyMaster");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_your_company);
        done = findViewById(R.id.donebtn);
        code = findViewById(R.id.code);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!code.getText().toString().trim().equalsIgnoreCase("")) {
                    passdataToDb();
                }
            }
        });
    }
    void passdataToDb()
    {

        codestr = code.getText().toString();
         cmp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               Boolean flag = dataSnapshot.child(codestr).exists();
               if (flag)
               {   Profile profile = new Profile(codestr);
                   FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("company_id").setValue(profile.getCompany_id()).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful())
                           {    finishAffinity();
                               startActivity(new Intent(getApplicationContext(),Ui_home.class));

                           }
                           else
                           {
                               Toast.makeText(Join_your_company.this, "locha join", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });
               }
               else
               {
                   Toast.makeText(Join_your_company.this, "Invalid company code", Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
