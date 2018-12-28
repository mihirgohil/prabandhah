package com.prabandhah.prabandhah;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash_screen extends AppCompatActivity {
    int SPLASH_DISPLAY_LENGTH=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Login-Activity. */
                Intent mainIntent = new Intent(Splash_screen.this,LoginPage.class);
                Splash_screen.this.startActivity(mainIntent);
                Splash_screen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
