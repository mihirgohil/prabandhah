package com.prabandhah.prabandhah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class Ui_Detail_ViewOfEvent extends AppCompatActivity {
    ImageView bckbtn;
    Toolbar toolbar;
    int role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui__detail__view_of_event);

        bckbtn = findViewById(R.id.createEvent_png_backbtn);
        //getting role
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ui_home.class);

                startActivity(intent);
                finish();
            }
        });
        //Adding toolbar to the activity
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Ui_home.class);
        startActivity(intent);
        finish();
    }
    //menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(role == 1) {
            inflater.inflate(R.menu.team_included_in_event, menu);
            inflater.inflate(R.menu.editbtn, menu);
        }
        else if(role == 2)
        {
        }
        return super.onCreateOptionsMenu(menu);
    }
    // menu item click
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            //for admin
            case R.id.menu_toolbarincludedTeam:
                Intent intent=new Intent(getApplicationContext(),Ui_incluededTeams.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_toolbareditbtn:
                intent = new Intent(getApplicationContext(), Ui_edit_Detail_of_event.class);
                startActivity(intent);
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

