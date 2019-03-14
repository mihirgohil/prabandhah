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

public class Account_Settings_Change_Password extends AppCompatActivity {

    private EditText passwordEmail;
    private Button resetpassword;
    private FirebaseAuth fba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__settings__change__password);
        passwordEmail = findViewById(R.id.change_pass_email);
        resetpassword = findViewById(R.id.change_pass_button);
        fba = FirebaseAuth.getInstance();
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMail = passwordEmail.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (userMail.equalsIgnoreCase("")) {
                    passwordEmail.setError("Please Enter Email");

                } else if (!userMail.matches(emailPattern)) {
                    passwordEmail.setError("Please Enter valid Email");

                }  else {
                    fba.sendPasswordResetEmail(userMail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(Account_Settings_Change_Password.this, "Password Reset Mail has Been Sent!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Account_Settings_Change_Password.this,Ui_settings_Account_setting.class));
                            }
                            else
                            {
                                Toast.makeText(Account_Settings_Change_Password.this, "Error in Sending Reset password mail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
