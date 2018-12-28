package com.prabandhah.prabandhah;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class Sign_up extends AppCompatActivity {
    FirebaseAuth fba;
    ImageView img;
    int  REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button signin;
        final EditText Email,password,retypepass;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fba = FirebaseAuth.getInstance();
        signin = findViewById(R.id.signin);
        img = findViewById(R.id.profile_pic_signup);
        Email = findViewById(R.id.email);
        password = findViewById(R.id.reg_password);
        retypepass = findViewById(R.id.retype);
        //sign in
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        //image upload to profile pic
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create the Intent for Image Gallery.
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
                startActivityForResult(i, REQUEST_CODE);

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri pickedImage = data.getData();
            // Let's read picked image path using content resolver
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(pickedImage, filePath, null, null, null);
            cursor.moveToFirst();
            String imagePath = cursor.getString(cursor.getColumnIndex(filePath[0]));

            // Now we need to set the GUI ImageView data with data read from the picked file.
            img.setImageBitmap(BitmapFactory.decodeFile(imagePath));

            // At the end remember to close the cursor or you will end with the RuntimeException!
            cursor.close();
        }
    }


    void Submit(String Email,String password)
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
                                    startActivity(new Intent(Sign_up.this,LoginPage.class));
                                    Toast.makeText(Sign_up.this, "Verification mail has been sent", Toast.LENGTH_SHORT).show();
                                    fba.signOut();
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
