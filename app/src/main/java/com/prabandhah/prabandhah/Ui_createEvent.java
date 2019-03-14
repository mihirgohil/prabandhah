package com.prabandhah.prabandhah;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import android.text.format.DateFormat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.dataclasses.EventClass;
import com.prabandhah.prabandhah.dataclasses.Profile;
import com.prabandhah.prabandhah.pagerAndAdepter.AdapterForTeammemebers;
import com.prabandhah.prabandhah.pagerAndAdepter.AdepterForRecylerView;
import com.prabandhah.prabandhah.tabs.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Ui_createEvent extends AppCompatActivity implements
    DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{
    ImageView bckbtn;
    TextView startdatebtn,enddatebtn,starttime,endtime;
    DatabaseReference reference;
    EditText nameofevent,noofguest,budget,address,descriprion,custom;
    Spinner typeList;
    AdapterForTeammemebers adepterForRecylerView;
    FloatingActionButton fab;
    int day,day1,month,month1,year,year1,hour,hour1,minute,minute1;
    int day2,month2,year2,hour2,minute2;
    int dayfinal,monthfinal,hourfinal,yearfinal,minutefinal;
    int dayfinal1,monthfinal1,hourfinal1,yearfinal1,minutefinal1;
    int role,flag;
    ArrayList<Profile> list;
    RecyclerView recyclerView;
    String  eventtype="";
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
        recyclerView = findViewById(R.id.recycler);
        starttime = findViewById(R.id.starttime);
        endtime = findViewById(R.id.endtime);
        recyclerView.setLayoutManager(new LinearLayoutManager(Ui_createEvent.this));
        enddatebtn = findViewById(R.id.enddatebtn);
        nameofevent = findViewById(R.id.nameofevent);
        noofguest = findViewById(R.id.noofguest);
        custom = findViewById(R.id.eventtypecustom);
        budget = findViewById(R.id.budget);
        address = findViewById(R.id.venueaddresss);
        descriprion = findViewById(R.id.description);
        typeList = findViewById(R.id.createEvent_png_eventtypeList);
        bckbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Ui_home.class);
                startActivity(intent);
                finish();
            }
        });
        final List<String> eventtypes= new ArrayList<>();
        eventtypes.add(0,"select type");
        eventtypes.add("Birth day");
        eventtypes.add("Dj party");
        eventtypes.add("Seminar");
        eventtypes.add("Conferences");
        eventtypes.add("Sports Event");
        eventtypes.add("Product Launch Events");
        eventtypes.add("Marriage");
        eventtypes.add("Engagement");
        eventtypes.add("Other");
        final ArrayAdapter<String> dataadpt;
        dataadpt = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,eventtypes);
        dataadpt.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        typeList.setAdapter(dataadpt);
        setCurrentDateOnView();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Profile profile = dataSnapshot.getValue(Profile.class);
                reference = FirebaseDatabase.getInstance().getReference("users");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        list = new ArrayList<Profile>();
                        for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            Profile p = dataSnapshot1.getValue(Profile.class);
                            if(profile.getCompany_id().equals(p.getCompany_id()))
                            {
                                if(p.getRole().equals("2") )
                                {
                                    list.add(p);
                                }
                            }
                        }
                        adepterForRecylerView = new AdapterForTeammemebers(Ui_createEvent.this,list,Ui_createEvent.class.getSimpleName());
                        recyclerView.setAdapter(adepterForRecylerView);
//                         Toast.makeText(getContext(), "c"+String.valueOf(counter), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //    Toast.makeText(getContext(), "error"+databaseError, Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
       typeList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               if(typeList.getSelectedItem().toString().equals("Other"))
               {
                   Toast.makeText(Ui_createEvent.this, "in custom", Toast.LENGTH_SHORT).show();
                   //   typeList.setVisibility(View.INVISIBLE);
                   custom.setVisibility(View.VISIBLE);
               }
               else
               {
                   custom.setVisibility(View.INVISIBLE);
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       Profile p = dataSnapshot.getValue(Profile.class);
                       String company_id = p.company_id;
                       //pushing event
                       DatabaseReference dba = FirebaseDatabase.getInstance().getReference("EventMaster").child(company_id).push();
                       String eventid;
                       eventid = dba.getKey();
                       //getteing all the details & validation
                       if (typeList.getSelectedItem().toString().equals("Other")) {
                           eventtype = custom.getText().toString();
                       } else {
                           eventtype = typeList.getSelectedItem().toString();

                       }
                       if(nameofevent.getText().toString().isEmpty()){
                            nameofevent.setError("Enter name");
                       }
                       else{
                           if(typeList.getSelectedItem().toString().equals("Other")) {
                               if (custom.getText().toString().isEmpty()){
                                   custom.setError("Enter type");
                               }
                           }
                           else if(typeList.getSelectedItem().toString().equals("select type")){
                               Toast.makeText(Ui_createEvent.this, "Select event type", Toast.LENGTH_SHORT).show();
                           }
                           else {
                               if(noofguest.getText().toString().isEmpty()){
                                   noofguest.setError("Enter no of Guest");
                               }
                               else if(TextUtils.isDigitsOnly(budget.getText().toString())){
                                   if(budget.getText().toString().isEmpty())
                                   {
                                       budget.setError("Enter Budget");
                                   }
                                   else if(TextUtils.isDigitsOnly(budget.getText().toString())){
                                       if(address.getText().toString().isEmpty()){
                                         address.setError("Enter Address");
                                       }
                                       else
                                           {
                                               if(descriprion.getText().toString().isEmpty()){
                                             descriprion.setError("Enter Desciption");
                                               }
                                               else{
                                                   Profile profile= adepterForRecylerView.getselectedprofile();
                                                   final Calendar c = Calendar.getInstance();
                                                   year2 = c.get(Calendar.YEAR);
                                                   month2 = c.get(Calendar.MONTH);
                                                   day2 = c.get(Calendar.DAY_OF_MONTH);
                                                   StringBuffer creationdate=new StringBuffer().append(day2).append("-").append(month2 + 1).append("-")
                                                           .append(year2);
                                                   SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
                                                   String creationtime =  mdformat.format(c.getTime());
                                                   EventClass event = new EventClass(eventid, nameofevent.getText().toString(), eventtype, noofguest.getText().toString(), budget.getText().toString(), address.getText().toString(), descriprion.getText().toString(),profile.user_id,startdatebtn.getText().toString(),enddatebtn.getText().toString(),starttime.getText().toString(),endtime.getText().toString(),creationdate.toString(),creationtime,"assigned");
                                                   dba = FirebaseDatabase.getInstance().getReference("EventMaster").child(company_id).child(eventid);
                                                   //passing event
                                                   dba.setValue(event);
                                                   Intent intent=new Intent(getApplicationContext(),Ui_home.class);
                                                   startActivity(intent);
                                                   finish();
                                               }
                                     }
                                   }
                                   else {
                                       budget.setError("enter no");
                                   }
                               }
                               else {
                                   noofguest.setError("enter no");
                               }
                           }
                       }

                   }
                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });

           }
       });
    }

    // display current date
    public void setCurrentDateOnView() {
        final Calendar c = Calendar.getInstance();
        year2 = c.get(Calendar.YEAR);
        month2 = c.get(Calendar.MONTH);
        day2 = c.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String strDate = mdformat.format(c.getTime());
        // set current date into textview
        startdatebtn.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day2).append("-").append(month2 + 1).append("-")
                .append(year2).append(" "));
        starttime.setText(strDate);

        enddatebtn.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day2+1).append("-").append(month2 + 1).append("-")
                .append(year2).append(" "));
        endtime.setText(strDate);
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
                    .append(dayfinal).append("-").append(monthfinal + 1).append("-")
                    .append(yearfinal));
            starttime.setVisibility(View.VISIBLE);
            starttime.setText(new StringBuffer()
                    .append(hourfinal).append(":").append(minutefinal));
        }
        else if(flag == 2) {
            hourfinal1 = hourOfDay;
            minutefinal1 = minute;
            enddatebtn.setText(new StringBuilder()
                    // Month is 0 based, just add 1
                    .append(dayfinal1).append("-").append(monthfinal1 + 1).append("-")
                    .append(yearfinal1));
            endtime.setVisibility(View.VISIBLE);
            endtime.setText(new StringBuffer()
                    .append(hourfinal1).append(":").append(minutefinal1));
        }

    }



}
