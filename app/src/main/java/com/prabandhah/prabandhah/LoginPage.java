package com.prabandhah.prabandhah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {
    Button login;
    EditText mail,password;
    FirebaseAuth fba;
    TextView signin,signin1,forgetpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        login = findViewById(R.id.btn_login);
        forgetpass = findViewById(R.id.forget_pass_page);
        mail = findViewById(R.id.txt_email);
        password = findViewById(R.id.txt_password);
        signin1 = findViewById(R.id.sigin1);
        signin = findViewById(R.id.signin);
        fba = FirebaseAuth.getInstance();
        //signin
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginPage.this,Sign_up.class));
            }
        });
        signin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginPage.this,Sign_up.class));
            }
        });

        //login btn click
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (mail.getText().toString().trim().equalsIgnoreCase("")) {
                    mail.setError("Please Enter Email");

                } else if (!mail.getText().toString().matches(emailPattern)) {
                    mail.setError("Please Enter valid Email");

                } else if (password.getText().toString().trim().equalsIgnoreCase("")) {
                    password.setError("Please Enter password");
                } else {
                    validate(mail.getText().toString(),password.getText().toString());
                }

            }
        });
        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,ForgetPassNewPassword.class));

            }
        });
    }
    private void validate(String email,String password)
    {
        fba.signInWithEmailAndPassword(email , password ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    email_verifed();
                }
                else{
                    Toast.makeText(LoginPage.this, "No account Exist on This Mail id!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void email_verifed()
    {
        FirebaseUser fbu =fba.getInstance().getCurrentUser();
        Boolean mailflag = fbu.isEmailVerified();
        if (mailflag)
        {   SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            final SharedPreferences.Editor editor = pref.edit();
            DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            dataref.keepSynced(true);
            dataref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String ro = dataSnapshot.child("role").getValue().toString();
                    Toast.makeText(LoginPage.this, "at Login role:" + ro, Toast.LENGTH_SHORT).show();
                    if (ro.equals("1") || ro.equals("2") || ro.equals("3") || ro.equals("4")) {
                        int retrole;
                        Toast.makeText(LoginPage.this, "at retrole", Toast.LENGTH_SHORT).show();
                        retrole = Integer.parseInt(ro);
                        editor.putInt("role", retrole);
                        editor.commit();
                        finish();
                        Intent mainIntent = new Intent(LoginPage.this,Ui_home.class);
                        LoginPage.this.startActivity(mainIntent);
                        LoginPage.this.finish();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(LoginPage.this, "in cancel :"+databaseError.toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
        else if(mailflag == false)
        {   finish();
            String Email = mail.getText().toString();
            Intent intent = new Intent(getBaseContext(), verfiy_your_mail.class);
            intent.putExtra("Email", Email);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Error on Email verfication", Toast.LENGTH_SHORT).show();
        }
    }

}
