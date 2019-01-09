package com.prabandhah.prabandhah;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class are_you_owner_or_admin extends AppCompatActivity {
    RadioGroup rg1;
    Button b1;
    RadioButton rb1,rb2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_are_you_owner_or_admin);
        rg1 = findViewById(R.id.rg1);
        b1 = findViewById(R.id.btn_chose_role);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb1 = findViewById(R.id.Rb1);
                rb2=findViewById(R.id.Rb2);
                if(rb1.isChecked())
                {
                    Intent intent=new Intent(getApplicationContext(),Ui_home.class);
                    intent.putExtra("selected",1);
                    startActivity(intent);
                    finish();

                }
                else if (rb2.isChecked())
                {
                    Intent intent=new Intent(getApplicationContext(),Ui_home.class);
                    intent.putExtra("selected",2);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }
}
