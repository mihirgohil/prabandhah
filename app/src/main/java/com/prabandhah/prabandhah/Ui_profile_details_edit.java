package com.prabandhah.prabandhah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Ui_profile_details_edit extends AppCompatActivity {
    int role;
    ImageView bckbtn;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_profile_details_edit);
        bckbtn = findViewById(R.id.pro_png_backbtn);
        save = findViewById(R.id.saveBtn);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UI_Profile_details.class);
                intent.putExtra("selected", role);
                startActivity(intent);
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UI_Profile_details.class);
                startActivity(intent);
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.statusbarBlack));//status bar or the time bar at the top
        }

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), UI_Profile_details.class);
        startActivity(intent);
        finish();
    }
}
