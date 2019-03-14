package com.prabandhah.prabandhah.pagerAndAdepter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.R;
import com.prabandhah.prabandhah.Ui_Detail_ViewOfEvent;
import com.prabandhah.prabandhah.Ui_subtaskForteammembers;
import com.prabandhah.prabandhah.dataclasses.Task;
import com.prabandhah.prabandhah.dataclasses.Teams;

import java.util.ArrayList;

public class AdpaterForTasklistteamhead extends RecyclerView.Adapter<AdpaterForTasklistteamhead.viewHolder>{
    ArrayList<Task> tasklist;
    Context context;
    String companyid;
    String eventid;
    public AdpaterForTasklistteamhead(Context c,ArrayList<Task> s,String cid,String evnt){
        tasklist = s;
        context = c;
        companyid = cid;
        eventid = evnt;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdpaterForTasklistteamhead.viewHolder(LayoutInflater.from(context).inflate(R.layout.recylerttaskforteamhead,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {
         holder.nameoftask.setText(tasklist.get(position).task);
        Toast.makeText(context, "cid"+companyid, Toast.LENGTH_SHORT).show();
        FirebaseDatabase.getInstance().getReference("Teams").child(companyid).child(tasklist.get(position).teamid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Teams teams =dataSnapshot.getValue(Teams.class);
                holder.nameofteam.setText(teams.team_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(eventid).child("teamid").child(tasklist.get(position).teamid).child("task").child(tasklist.get(position).taskid).child("subtask").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 int counter = 0;
                 for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                     counter++;
                 }
                 if(counter == 0){
                     holder.nooftask.setText("0");
                 }
                 else {
                     holder.nooftask.setText(String.valueOf(counter));
                 }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(tasklist.get(position).taskstatus.equals("completed")){
            holder.spinner.setVisibility(View.INVISIBLE);
            holder.txtstatus.setVisibility(View.VISIBLE);
        }
        if(tasklist.get(position).taskstatus.equals("assigned")){
            holder.spinner.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return tasklist.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{
      TextView nameofteam,nameoftask,txtstatus,nooftask;
        Spinner spinner;
      public viewHolder(View itemView) {
          super(itemView);
          spinner = itemView.findViewById(R.id.statusspiner);
          nameofteam=itemView.findViewById(R.id.nameofteam);
          nameoftask=itemView.findViewById(R.id.nameoftask);
          txtstatus = itemView.findViewById(R.id.statustxt);
          nooftask = itemView.findViewById(R.id.noofsubtask);
          itemView.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent= new Intent(context, Ui_subtaskForteammembers.class);
                  intent.putExtra("taskid",tasklist.get(getAdapterPosition()).taskid);
                  intent.putExtra("eventid",eventid);
                  intent.putExtra("teamid",tasklist.get(getAdapterPosition()).teamid);
                  context.startActivity(intent);
                  //finish activity from adpter
                  ((Activity)context).finish();
              }
          });
          spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  if(position ==1){
                      FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(eventid).child("teamid").child(tasklist.get(getAdapterPosition()).teamid).child("task").child(tasklist.get(getAdapterPosition()).taskid).child("taskstatus").setValue("completed");

                  }
              }

              @Override
              public void onNothingSelected(AdapterView<?> parent) {

              }
          });
      }
  }
}
