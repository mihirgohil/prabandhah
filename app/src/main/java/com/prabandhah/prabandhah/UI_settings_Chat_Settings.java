package com.prabandhah.prabandhah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class UI_settings_Chat_Settings extends AppCompatActivity {
    ImageView bckbtn;
    int role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_settings__chat__settings);
        Intent intent = getIntent();
        bckbtn = findViewById(R.id.Setting_bckbtn);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ui_setting.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Ui_setting.class);
        startActivity(intent);
        finish();
    }
}


