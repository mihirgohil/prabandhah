package com.prabandhah.prabandhah;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Ui_setting_help extends AppCompatActivity {
    LinearLayout layout_call_email,layout_sub_email;
    Button btn_call,btn_email,btn_send;
    EditText edit_subject,edit_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_setting_help);
        btn_call= findViewById(R.id.btn_call);
        btn_email= findViewById(R.id.btn_email);
        btn_send= findViewById(R.id.btn_send);
        edit_subject= findViewById(R.id.edit_subject);
        edit_email= findViewById(R.id.edit_email);

        layout_call_email= findViewById(R.id.layout_call_email);
        layout_sub_email= findViewById(R.id.layout_sub_email);


        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0377778888"));

                if (ActivityCompat.checkSelfPermission(Ui_setting_help.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });
        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_sub_email.setVisibility(View.VISIBLE);


            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_sub_email.setVisibility(View.GONE);

                String to = "abc@gmail.com";
                String subject = edit_subject.getText().toString();
                String compose_email = edit_email.getText().toString();

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL, to);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT,compose_email);

                startActivity(Intent.createChooser(intent, "choose an email client :"));
            }
        });
    }
}

