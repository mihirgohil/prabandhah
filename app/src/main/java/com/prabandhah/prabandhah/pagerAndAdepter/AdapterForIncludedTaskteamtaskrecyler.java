package com.prabandhah.prabandhah.pagerAndAdepter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.prabandhah.prabandhah.R;
import com.prabandhah.prabandhah.dataclasses.Task;

import java.util.ArrayList;

public class AdapterForIncludedTaskteamtaskrecyler extends RecyclerView.Adapter<AdapterForIncludedTaskteamtaskrecyler.Viewholder> {
    Context context;
    ArrayList<Task> tasklist;
    DatabaseReference dba;
    public AdapterForIncludedTaskteamtaskrecyler(Context c,ArrayList<Task> t){
        context = c;
        tasklist = t;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(context).inflate(R.layout.recylertaskincludedteam,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.nameoftask.setText(tasklist.get(position).task);
        holder.status.setText(tasklist.get(position).taskstatus);
    }

    @Override
    public int getItemCount() {
        return tasklist.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView nameoftask,status;
        public Viewholder(View itemView) {
            super(itemView);
           nameoftask= itemView.findViewById(R.id.nameoftask);
            status = itemView.findViewById(R.id.status);
        }
    }
}
