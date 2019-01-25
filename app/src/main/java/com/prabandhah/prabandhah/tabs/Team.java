package com.prabandhah.prabandhah.tabs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.prabandhah.prabandhah.R;
import com.prabandhah.prabandhah.Ui_home;
import com.prabandhah.prabandhah.Ui_team_in_DetaiView;

public class Team extends Fragment {
    int role;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        //getting data from activity
        SharedPreferences pref = this.getActivity().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        role = pref.getInt("role",0);
        return inflater.inflate(R.layout.activity_team, container, false);
    }
    TextView team;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        team = view.findViewById(R.id.teamListSample);
        team.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Ui_team_in_DetaiView.class));
            }
        });
    }
}
