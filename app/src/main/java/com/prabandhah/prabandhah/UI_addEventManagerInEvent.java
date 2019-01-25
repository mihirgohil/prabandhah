package com.prabandhah.prabandhah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class UI_addEventManagerInEvent extends AppCompatActivity {
    int role;
    ImageView addeventmanger;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_add_event_manager_in_event);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);
        fab = findViewById(R.id.fabtn_create_event);
        addeventmanger = findViewById(R.id.add_new_eventManager);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Ui_home.class);
                intent.putExtra("selected",role);
                startActivity(intent);
                finish();
            }
        });
        addeventmanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UI_addEventManagerInEvent.this,UI_addEventManagerInEvent.class));
            }
        });

    }
}
