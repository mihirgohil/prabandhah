package com.prabandhah.prabandhah;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Temp_role_testing extends AppCompatActivity {
    RadioGroup rg1;
    Button b1;
    RadioButton rb1,rb2,rb3,rb4,rb5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_role_testing);
        rg1 = findViewById(R.id.rg1);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        b1 = findViewById(R.id.btn_chose_role);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb1 = findViewById(R.id.Rb1);
                rb2=findViewById(R.id.Rb2);
                rb3=findViewById(R.id.Rb3);
                rb4=findViewById(R.id.Rb4);
                if(rb1.isChecked())
                {
                    editor.putInt("role", 1);
                    editor.commit();
                    startActivity(new Intent(Temp_role_testing.this,Ui_home.class));
                }
                else if (rb2.isChecked())
                {

                    editor.putInt("role", 2);
                    editor.commit();
                    startActivity(new Intent(Temp_role_testing.this,Ui_home.class));
                }
                else if (rb3.isChecked())
                {

                    editor.putInt("role", 3);
                    editor.commit();
                    startActivity(new Intent(Temp_role_testing.this,Ui_home.class));
                }
                else if (rb4.isChecked())
                {

                    editor.putInt("role", 4);
                    editor.commit();
                    startActivity(new Intent(Temp_role_testing.this,Ui_home.class));
                }
            }
        });
        //status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.statusbarBlack));//status bar or the time bar at the top
        }


    }
}
