package com.prabandhah.prabandhah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Ui_setting extends AppCompatActivity {
    ImageView bckbtn;
<<<<<<< Updated upstream
    TextView accountSetting,chatSetting,notificationSetting,aboutus,help;
=======
    TextView accountSetting,chatSetting,notificationSetting,aboutus,reportbug;
>>>>>>> Stashed changes
    int role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_setting);
        Intent intent = getIntent();
        bckbtn = findViewById(R.id.Setting_bckbtn);
        accountSetting = findViewById(R.id.accountSetting);
        chatSetting = findViewById(R.id.chatSetting);
        notificationSetting = findViewById(R.id.notificationSetting);
        help = findViewById(R.id.help);
        aboutus = findViewById(R.id.aboutus);
        reportbug = findViewById(R.id.reportbug);
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
        accountSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ui_settings_Account_setting.class);

                startActivity(intent);
                finish();
            }
        });
        chatSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UI_settings_Chat_Settings.class);

                startActivity(intent);
                finish();
            }
        });
        notificationSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ui_settings_notification_setting.class);

                startActivity(intent);
                finish();
            }
        });
        reportbug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ui_Setting_Report_bug.class);

                startActivity(intent);
                finish();
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ui_settings_about_us.class);

                startActivity(intent);
                finish();
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ui_setting_help.class);

                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Ui_home.class);
        startActivity(intent);
        finish();
    }
}
