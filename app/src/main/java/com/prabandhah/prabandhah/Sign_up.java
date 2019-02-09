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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;

public class Sign_up extends AppCompatActivity {
    FirebaseAuth fba;
    EditText Email,password,retypepass;
    int  REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button signin;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fba = FirebaseAuth.getInstance();
        signin = findViewById(R.id.signin);
        Email = findViewById(R.id.email);
        password = findViewById(R.id.reg_password);
        retypepass = findViewById(R.id.retype);

        //sign in
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkdatafromat();
                

            }
        });

    }


  
    void checkdatafromat()
    {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        // name nu kam baki che
        if (Email.getText().toString().trim().equalsIgnoreCase("")) {
            Email.setError("Please Enter Email");

        } else if (!Email.getText().toString().matches(emailPattern)) {
            Email.setError("Please Enter valid Email");

        } else if (password.getText().toString().trim().equalsIgnoreCase("")) {
            password.setError("Please Enter password");
        }
        else if (retypepass.getText().toString().trim().equalsIgnoreCase(""))
        {   retypepass.setError("Please Enter Password Again");
        }
        else
        {
            if(password.getText().toString().equals(retypepass.getText().toString()))
            {
                Submit(Email.getText().toString(),password.getText().toString());
            }
            else
            {
                Toast.makeText(Sign_up.this, "Password and retype password not matched", Toast.LENGTH_SHORT).show();
            }
        }
    }
    void Submit(final String Email, String password)
    {  checkmailexist(Email,password);     
    }
    void checkmailexist(final String Email, final String password){
        fba.fetchProvidersForEmail(Email).addOnCompleteListener(new OnCompleteListener<ProviderQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<ProviderQueryResult> task) {
                boolean emailpresent = !task.getResult().getProviders().isEmpty();
                
                if(!emailpresent){
                    //not present
                    CreateUserNewUserFinal(Email,password);
                }
                else
                {
                    Toast.makeText(Sign_up.this, "email id exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void CreateUserNewUserFinal(final String Email, final String password)
    {
        fba.createUserWithEmailAndPassword(Email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser Fbuser = fba.getInstance().getCurrentUser();
                    if(Fbuser != null)
                    {
                        Fbuser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {   finish();
                                    Intent intent = new Intent(getBaseContext(), verfiy_your_mail.class);
                                    intent.putExtra("Email", Email);
                                    startActivity(intent);
                                    Toast.makeText(Sign_up.this, "Verification mail has been sent", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(Sign_up.this, "Server side issue", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                }
            }
        });
    }


}
