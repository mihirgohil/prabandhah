package com.prabandhah.prabandhah.pagerAndAdepter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prabandhah.prabandhah.R;
import com.prabandhah.prabandhah.dataclasses.Teams;

import java.util.ArrayList;

public class AdapterForIncludedteam extends RecyclerView.Adapter<AdapterForIncludedteam.Viewholder> {
    Context context;
    String nameofactivity;
    ArrayList<Teams> teams;
    public AdapterForIncludedteam(Context c,ArrayList<Teams> t){
        context =c;
        teams = t;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterForIncludedteam.Viewholder(LayoutInflater.from(context).inflate(R.layout.reclyerforteamlist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        public Viewholder(View itemView) {
            super(itemView);
        }
    }
}
