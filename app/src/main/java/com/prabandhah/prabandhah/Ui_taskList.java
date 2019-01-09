package com.prabandhah.prabandhah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Ui_taskList extends AppCompatActivity {

    ImageView bckbtn;
    int role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_task_list);
        Intent intent = getIntent();
        bckbtn = findViewById(R.id.tasklist_bckbtn);
        role = intent.getIntExtra("selected",0);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ui_home.class);
                intent.putExtra("selected", role);
                startActivity(intent);
                finish();
            }
        });
    }
}
