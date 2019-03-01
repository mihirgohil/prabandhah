package com.prabandhah.prabandhah;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Ui_Setting_Report_bug extends AppCompatActivity {
    Button send;
    EditText subject,details_of_bug;
    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui__setting__report_bug);
        send = findViewById(R.id.btn_report);
        subject = findViewById(R.id.bug_subject);
        details_of_bug = findViewById(R.id.bug_detail);
        alertDialogBuilder = new AlertDialog.Builder(this);
// hey


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialogBuilder.setMessage("Your Report has been sent");


            }
        });
    }
}
