package com.prabandhah.prabandhah;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class verfiy_your_mail extends Activity {

    Button next;
    FirebaseAuth fba;
    ProgressDialog progressBar;
    FirebaseUser fbu =fba.getInstance().getCurrentUser();
    Boolean mailflag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfiy_your_mail);
        next =findViewById(R.id.next);
        progressBar = new ProgressDialog(this);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbu.reload().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressBar.setCancelable(false);
                        progressBar.setTitle("Welcome");
                        progressBar.setMessage("Please,wait, we are verfiying your mail...");

                        progressBar.setCanceledOnTouchOutside(false);
                        progressBar.show();
                        mailflag = fbu.isEmailVerified();
                        if (mailflag)
                        {   progressBar.hide();
                            finish();
                            String Email= getIntent().getStringExtra("Email");
                            Intent intent = new Intent(getBaseContext(), Create_UserProfile.class);
                            intent.putExtra("Email", Email);
                            startActivity(intent);
                        }
                        else if(!mailflag)
                        {   progressBar.hide();
                            Toast.makeText(verfiy_your_mail.this, "Check your mail box for verfication mail", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

    }



}
