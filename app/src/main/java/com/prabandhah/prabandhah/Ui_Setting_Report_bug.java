package com.prabandhah.prabandhah;

import android.content.Intent;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.util.Log;
import android.widget.Toast;

public class Ui_Setting_Report_bug extends AppCompatActivity {
    ImageView bckbtn;
    Button send;
    EditText subject, details_of_bug;
    AlertDialog.Builder alertDialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui__setting__report_bug);
        bckbtn = findViewById(R.id.Setting_bckbtn);
        send = findViewById(R.id.btn_report);
        subject = findViewById(R.id.bug_subject);
        details_of_bug = findViewById(R.id.bug_detail);
        alertDialogBuilder = new AlertDialog.Builder(this);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogBuilder.setTitle("Welcome");
                alertDialogBuilder.setMessage("Send the bug which you have find!!!");
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Throwable e = null;
                        //String stackTrace = Log.getStackTraceString(e);
                        //String message = e.getMessage();

                       /* Intent intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setType("message/rfc822");
                       // intent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                        intent.putExtra (Intent.EXTRA_EMAIL, new String[] {"typrojectcpi@gmail.com"});
                        intent.putExtra (Intent.EXTRA_SUBJECT, "Bug Details");
                        intent.putExtra (Intent.EXTRA_TEXT, details_of_bug.getText().toString());
                        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Intent.createChooser(intent, "Send email..."));*/
                        Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Bug Details");
                        intent.putExtra(Intent.EXTRA_TEXT, details_of_bug.getText().toString());
                        intent.setData(Uri.parse("mailto:typrojectcpi@gmail.com")); // or just "mailto:" for blank
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                        startActivity(intent);
                    }
                });
                alertDialogBuilder.setNegativeButton("No", null);
                alertDialogBuilder.show();



            }
        });

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
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), Ui_setting.class);

        startActivity(intent);
        finish();
    };
}


