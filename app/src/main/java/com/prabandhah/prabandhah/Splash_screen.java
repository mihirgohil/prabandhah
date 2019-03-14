package com.prabandhah.prabandhah;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash_screen extends AppCompatActivity {
    int SPLASH_DISPLAY_LENGTH=2000;
    Intent mainIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Login-Activity. */
                mainIntent = new Intent(Splash_screen.this,LoginPage.class);
                checkconnection();

            }
        }, SPLASH_DISPLAY_LENGTH);
        //if user logged in

        //status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);//status bar or the time bar at the top
        }
    }
    public void checkconnection(){
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
          //  Toast.makeText(Splash_screen.this, "online", Toast.LENGTH_SHORT).show();
            userlogin();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(Splash_screen.this);
            builder.setTitle("Network Error");
            builder.setMessage("No Internet Connection");
            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  //  Toast.makeText(Splash_screen.this, "retry", Toast.LENGTH_SHORT).show();
                    checkconnection();
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
    }
    public void userlogin(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        FirebaseAuth fba = FirebaseAuth.getInstance();
        FirebaseUser user = fba.getCurrentUser();
        if(user != null)
        {   DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            dataref.keepSynced(true);
            dataref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String ro = dataSnapshot.child("role").getValue().toString();
                    String companyid= dataSnapshot.child("company_id").getValue().toString();
                    Toast.makeText(Splash_screen.this, "at splash role:" + ro, Toast.LENGTH_SHORT).show();
                    if (ro.equals("1") || ro.equals("2") || ro.equals("3") || ro.equals("4")) {
                        int retrole;
                        Toast.makeText(Splash_screen.this, "at retrole", Toast.LENGTH_SHORT).show();
                        retrole = Integer.parseInt(ro);
                        editor.putInt("role", retrole);
                        editor.putString("companyid",companyid);
                        editor.commit();
                        finish();
                        mainIntent = new Intent(Splash_screen.this,Ui_home.class);
                        Splash_screen.this.startActivity(mainIntent);
                        Splash_screen.this.finish();
                    }
                    else{
                        Splash_screen.this.startActivity(mainIntent);
                        Splash_screen.this.finish();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Splash_screen.this, "in cancel :"+databaseError.toString(), Toast.LENGTH_LONG).show();
                    mainIntent = new Intent(Splash_screen.this,LoginPage.class);
                    Splash_screen.this.startActivity(mainIntent);
                    Splash_screen.this.finish();
                }
            });

        }
        else{
            Splash_screen.this.startActivity(mainIntent);
            Splash_screen.this.finish();
        }
    }
}
