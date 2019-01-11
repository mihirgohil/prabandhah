package com.prabandhah.prabandhah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Ui_createTeam extends AppCompatActivity {
    int role;
    ImageView bckbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_create_team);
        Intent intent=getIntent();
        role = intent.getIntExtra("selected",0);
        bckbtn = findViewById(R.id.createTeam_png_backbtn);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Ui_home.class);
                intent.putExtra("selected",role);
                startActivity(intent);
                finish();
            }
        });
    }
}
