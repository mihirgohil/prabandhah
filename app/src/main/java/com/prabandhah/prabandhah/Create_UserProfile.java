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
import com.google.firebase.database.FirebaseDatabase;
import com.prabandhah.prabandhah.dataclasses.Profile;

import de.hdodenhof.circleimageview.CircleImageView;

public class Create_UserProfile extends AppCompatActivity {
    Button submit;
    String Email;
    CircleImageView ProfilePic;
    EditText name;
    String CompanyId="",team_id = "";
    int role=0;
    FirebaseAuth fba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__user_profile);
        submit = findViewById(R.id.signin);
        name = findViewById(R.id.name);
        ProfilePic = findViewById(R.id.profilePic);
        Email= getIntent().getStringExtra("Email");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senduserdata(Email);
                finish();
                startActivity(new Intent(Create_UserProfile.this,are_you_owner_or_admin.class));
            }
        });
        ProfilePic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

                profilePicUpload();
            }
        });
    }
    void senduserdata(final String Email)
    {   Profile userProfile = new Profile(Email,name.getText().toString(),String.valueOf(role),CompanyId,team_id);
        FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(userProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(Create_UserProfile.this, "data inserted", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Create_UserProfile.this, "data not saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void profilePicUpload()
    {

    }
}
