package com.prabandhah.prabandhah;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Ui_createEvent extends AppCompatActivity implements
    DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{
    ImageView bckbtn;
    TextView startdatebtn,enddatebtn;
    Spinner typeList;
    FloatingActionButton fab;
    int day,day1,month,month1,year,year1,hour,hour1,minute,minute1;
    int day2,month2,year2,hour2,minute2;
    int dayfinal,monthfinal,hourfinal,yearfinal,minutefinal;
    int dayfinal1,monthfinal1,hourfinal1,yearfinal1,minutefinal1;
    int role,flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_create_event);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);
        bckbtn = findViewById(R.id.createEvent_png_backbtn);
        fab = findViewById(R.id.fabtn_create_event);
        startdatebtn = findViewById(R.id.strdatebtn);
        enddatebtn = findViewById(R.id.enddatebtn);
        typeList = findViewById(R.id.createEvent_png_eventtypeList);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Ui_home.class);
                intent.putExtra("selected",role);
                startActivity(intent);
                finish();
            }
        });
        final List<String> eventtypes= new ArrayList<>();
        eventtypes.add(0,"select type");
        eventtypes.add("bday");
        eventtypes.add("dj party");
        eventtypes.add("seminar");
        ArrayAdapter<String> dataadpt;
        dataadpt = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,eventtypes);
       dataadpt.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
       typeList.setAdapter(dataadpt);
        setCurrentDateOnView();
       startdatebtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Calendar calendar = Calendar.getInstance();
               year = calendar.get(Calendar.YEAR);
               month = calendar.get(Calendar.MONTH);
               day = calendar.get(Calendar.DAY_OF_MONTH);
               DatePickerDialog datePickerDialog = new DatePickerDialog(Ui_createEvent.this,Ui_createEvent.this,year,month,day);
               datePickerDialog.show();
                flag = 1;
           }
       });
       enddatebtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Calendar calendar = Calendar.getInstance();
               year1 = calendar.get(Calendar.YEAR);
               month1 = calendar.get(Calendar.MONTH);
               day1 = calendar.get(Calendar.DAY_OF_MONTH);
               DatePickerDialog datePickerDialog = new DatePickerDialog(Ui_createEvent.this,Ui_createEvent.this,year1,month1,day1);
               datePickerDialog.show();
               flag = 2;
           }
       });
       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(getApplicationContext(),UI_addEventManagerInEvent.class);

               startActivity(intent);
               finish();
           }
       });
    }

    // display current date
    public void setCurrentDateOnView() {
        final Calendar c = Calendar.getInstance();
        year2 = c.get(Calendar.YEAR);
        month2 = c.get(Calendar.MONTH);
        day2 = c.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strDate = "Time : " + mdformat.format(c.getTime());
        // set current date into textview
        startdatebtn.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append("Date :").append(day2).append("-").append(month2 + 1).append("-")
                .append(year2).append(" ").append(strDate));

        enddatebtn.setText(startdatebtn.getText().toString());
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
       if(flag == 1)
       {  yearfinal = year;
        monthfinal = month;

        dayfinal = dayOfMonth;
        Calendar calendar = Calendar.getInstance();
        hour = calendar.get(Calendar.HOUR_OF_DAY) ;
        minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(Ui_createEvent.this,Ui_createEvent.this,hour,minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
       }
        else if(flag == 2)
        {  yearfinal1 = year;
            monthfinal1 = month;
            dayfinal1 = dayOfMonth;
            Calendar calendar = Calendar.getInstance();
            hour1 = calendar.get(Calendar.HOUR_OF_DAY) ;
            minute1 = calendar.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(Ui_createEvent.this,Ui_createEvent.this,hour1,minute1, DateFormat.is24HourFormat(this));
            timePickerDialog.show();
        }

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(flag == 1) {
            hourfinal = hourOfDay;
            minutefinal = minute;
            startdatebtn.setText(new StringBuilder()
                    // Month is 0 based, just add 1
                    .append("Date :").append(dayfinal).append("-").append(monthfinal + 1).append("-")
                    .append(yearfinal).append(" ").append("Time :").append(hourfinal).append(":").append(minutefinal));
        }
        else if(flag == 2) {
            hourfinal1 = hourOfDay;
            minutefinal1 = minute;
            enddatebtn.setText(new StringBuilder()
                    // Month is 0 based, just add 1
                    .append("Date :").append(dayfinal1).append("-").append(monthfinal1 + 1).append("-")
                    .append(yearfinal1).append(" ").append("Time :").append(hourfinal1).append(":").append(minutefinal1));
        }

    }



}
