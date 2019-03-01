package com.prabandhah.prabandhah.pagerAndAdepter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.prabandhah.prabandhah.R;
import com.prabandhah.prabandhah.Ui_createEvent;
import com.prabandhah.prabandhah.Ui_createTeamProfile;
import com.prabandhah.prabandhah.dataclasses.Profile;

import java.util.ArrayList;

public class AdapterForTeammemebers extends RecyclerView.Adapter<AdapterForTeammemebers.ViewHolder>{
    Context context;
    ArrayList<Profile> profiles;
    String ActivityName;
    public AdapterForTeammemebers(Context c,ArrayList<Profile> p,String s){
         context = c;
            profiles = p;
            ActivityName = s;
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.reclyerviewforempwithradio,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(profiles.get(position).getUser_name());
        holder.email.setText(profiles.get(position).getUser_mail_id());
        if(ActivityName.equals("Ui_createEvent") || ActivityName.equals("Ui_createTeamProfile"))
        {
            holder.radioButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, email;
        RadioButton radioButton;
        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            radioButton = itemView.findViewById(R.id.ckb);

        }
    }
}
