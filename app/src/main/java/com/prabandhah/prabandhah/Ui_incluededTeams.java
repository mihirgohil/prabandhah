package com.prabandhah.prabandhah;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Ui_incluededTeams extends AppCompatActivity {
    ImageView bckbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_inclueded_teams);
        bckbtn = findViewById(R.id.inclued_png_backbtn);
        Intent intent = getIntent();
        final String eventid= intent.getStringExtra("eventid");

        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ui_Detail_ViewOfEvent.class);
                intent.putExtra("eventid",eventid);
                startActivity(intent);
                finish();
            }
        });
    }

}
