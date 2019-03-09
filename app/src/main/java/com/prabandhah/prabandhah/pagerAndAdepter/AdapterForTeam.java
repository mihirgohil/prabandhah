package com.prabandhah.prabandhah.pagerAndAdepter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.prabandhah.prabandhah.R;
import com.prabandhah.prabandhah.Ui_addTeamToEvent;
import com.prabandhah.prabandhah.Ui_team_in_DetaiView;
import com.prabandhah.prabandhah.dataclasses.Profile;
import com.prabandhah.prabandhah.dataclasses.Teams;

import java.util.ArrayList;

public class AdapterForTeam extends RecyclerView.Adapter<AdapterForTeam.Viewhodler> {
    Context context;
    Teams selectedTeam;
    String nameofactivity;
    int preselectedindex = -1;
    CheckBox checkBox;
    ArrayList <Teams> teams;
    public AdapterForTeam(Context c,ArrayList<Teams> t,String e){
        context = c;
        teams = t;
        nameofactivity = e;
    }
    @NonNull
    @Override
    public Viewhodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewhodler(LayoutInflater.from(context).inflate(R.layout.reclyerforteamlist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhodler holder, int position) {
        holder.nameofteam.setText(teams.get(position).getTeam_name());
        if(nameofactivity.equals("Ui_addTeamToEvent"))
        {
            holder.checkBox.setVisibility(View.VISIBLE);
        }
        holder.checkBox.setChecked(position == preselectedindex);
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public class Viewhodler extends RecyclerView.ViewHolder{
        TextView nameofteam;
        CheckBox checkBox;
        public Viewhodler(View itemView) {
            super(itemView);
            nameofteam = itemView.findViewById(R.id.name);
            checkBox = itemView.findViewById(R.id.chkbox);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(nameofactivity.equals("Ui_addTeamToEvent")){
                        selectedTeam = new Teams();
                        preselectedindex = getAdapterPosition();
                        selectedTeam = teams.get(getAdapterPosition());
                        notifyDataSetChanged();
                    }
                    else {
                        Intent intent= new Intent(context,Ui_team_in_DetaiView.class);
                        intent.putExtra("teamid",teams.get(getAdapterPosition()).getTeam_id());
                        context.startActivity(intent);
                        ((Activity)context).finish();
                    }
                }
            });
        }
    }
    public String getselectedteamid(){
        return selectedTeam.team_id;
    }
}
