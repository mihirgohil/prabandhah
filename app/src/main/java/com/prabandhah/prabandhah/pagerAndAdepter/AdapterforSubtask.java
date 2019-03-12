package com.prabandhah.prabandhah.pagerAndAdepter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.R;
import com.prabandhah.prabandhah.Ui_Includedteam_taskDetail;
import com.prabandhah.prabandhah.Ui_subtaskDetailview;
import com.prabandhah.prabandhah.dataclasses.SubTask;

import java.util.ArrayList;

public class AdapterforSubtask extends RecyclerView.Adapter<AdapterforSubtask.viewHolder> {
    ArrayList<SubTask> tasks;
    Context context;
    String eventid;
    public AdapterforSubtask(Context c,ArrayList<SubTask> j,String event){
        tasks = j;
        context = c;
        eventid = event;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterforSubtask.viewHolder(LayoutInflater.from(context).inflate(R.layout.recylerforsubtask,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        holder.nameoftask.setText(tasks.get(position).subtask);
        holder.status.setText(tasks.get(position).subtaskstatus);
        FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(eventid).child("teamid").child(tasks.get(position).teamid).child("task").child(tasks.get(position).maintaskid).child("subtask").child(tasks.get(position).subtaskid).child("employeefortask").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              int counter=0;
              for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                  counter++;
              }
              if(counter == 0){

              }
              else {
                  holder.noofemployee.setText(String.valueOf(counter));
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView nameoftask,noofemployee,status;
        public viewHolder(View itemView) {
            super(itemView);
            nameoftask =itemView.findViewById(R.id.nameoftask);
            noofemployee =itemView.findViewById(R.id.noofemployee);
            status = itemView.findViewById(R.id.statustxt);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(context, Ui_subtaskDetailview.class);
                    intent.putExtra("eventid",eventid);
                    intent.putExtra("teamid",tasks.get(getAdapterPosition()).teamid);
                    intent.putExtra("subtaskid",tasks.get(getAdapterPosition()).subtaskid);
                    intent.putExtra("taskid",tasks.get(getAdapterPosition()).maintaskid);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
            });
        }
    }
}
