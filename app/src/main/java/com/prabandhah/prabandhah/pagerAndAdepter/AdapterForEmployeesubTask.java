package com.prabandhah.prabandhah.pagerAndAdepter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.R;
import com.prabandhah.prabandhah.Ui_TaskpartnersDetail_for_emp;
import com.prabandhah.prabandhah.dataclasses.SubTask;
import com.prabandhah.prabandhah.dataclasses.SubTaskemp;
import com.prabandhah.prabandhah.dataclasses.Teams;

import java.util.ArrayList;

public class AdapterForEmployeesubTask extends RecyclerView.Adapter<AdapterForEmployeesubTask.viewHolder>{
    ArrayList<SubTaskemp> subTaskemps;
    Context context;
    public AdapterForEmployeesubTask(Context c,ArrayList<SubTaskemp> s){
        context = c;
        subTaskemps = s;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new AdapterForEmployeesubTask.viewHolder(LayoutInflater.from(context).inflate(R.layout.recylerviewforsubtaskemphomepage,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {
        holder.nameoftask.setText(subTaskemps.get(position).subtask);
        SharedPreferences pref = context.getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        String companyid = pref.getString("companyid","");
        FirebaseDatabase.getInstance().getReference("Teams").child(companyid).child(subTaskemps.get(position).teamid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Teams teams = dataSnapshot.getValue(Teams.class);
                holder.nameofteam.setText(teams.team_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(subTaskemps.get(position).eventid).child("teamid").child(subTaskemps.get(position).teamid).child("task").child(subTaskemps.get(position).maintaskid).child("subtask").child(subTaskemps.get(position).subtaskid).child("employeefortask").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int counter=0;
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                    counter++;
                }
                counter =counter-1;
                holder.noofemployee.setText(String.valueOf(counter));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(subTaskemps.get(position).eventid).child("teamid").child(subTaskemps.get(position).teamid).child("task").child(subTaskemps.get(position).maintaskid).child("subtask").child(subTaskemps.get(position).subtaskid).child("employeefortask").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    SubTaskemp subTaskemp = dataSnapshot1.getValue(SubTaskemp.class);
                    if(subTaskemp.userid.equals(userid)){
                        if(subTaskemp.status.equals("completed")){
                        holder.status.setEnabled(false);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.pos.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return subTaskemps.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView nameoftask,nameofteam,noofemployee,pos;
        Spinner status;
        public viewHolder(View itemView) {
            super(itemView);
            nameoftask = itemView.findViewById(R.id.nameoftask);
            nameofteam = itemView.findViewById(R.id.nameofteam);
            status = itemView.findViewById(R.id.statusspiner);
            noofemployee = itemView.findViewById(R.id.noofemployee);
            pos = itemView.findViewById(R.id.position);
            status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                    if(status.getSelectedItemPosition()==1){
                       final DatabaseReference dba= FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(subTaskemps.get(getAdapterPosition()).eventid).child("teamid").child(subTaskemps.get(getAdapterPosition()).teamid).child("task").child(subTaskemps.get(getAdapterPosition()).maintaskid).child("subtask").child(subTaskemps.get(getAdapterPosition()).subtaskid);
                       dba.child("employeefortask").child(subTaskemps.get(getAdapterPosition()).userid).child("status").setValue("completed").addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               dba.child("employeefortask").addValueEventListener(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                       int noofemployee=0;
                                       for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                           SubTaskemp subTaskemp=dataSnapshot1.getValue(SubTaskemp.class);
                                           if(subTaskemp.status.equals("assigned")){
                                               noofemployee++;
                                           }
                                       }
                                       if(noofemployee==0){
                                           dba.child("subtaskstatus").setValue("completed");
                                       }
                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                   }
                               });
                           }
                       });
                       Toast.makeText(context, "subtask"+subTaskemps.get(getAdapterPosition()).subtaskid, Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(subTaskemps.get(getAdapterPosition()).eventid).child("teamid").child(subTaskemps.get(getAdapterPosition()).teamid).child("task").child(subTaskemps.get(getAdapterPosition()).maintaskid).child("subtask").child(subTaskemps.get(getAdapterPosition()).subtaskid).child("employeefortask").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            int counter=0;
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){

                                counter++;
                            }
                            counter =counter-1;
                            if(counter > 0){
                                Intent intent=new Intent(context,Ui_TaskpartnersDetail_for_emp.class);
                            //    intent.putExtra("eventid",subTaskemps.get(getAdapterPosition()).eventid);
                              //  intent.putExtra("maintask",subTaskemps.get(getAdapterPosition()).maintaskid);
                                //intent.putExtra("subtaskid",subTaskemps.get(getAdapterPosition()).subtaskid);
                                //intent.putExtra("teamid",subTaskemps.get(getAdapterPosition()).teamid);

                                context.startActivity(intent);
                                ((Activity)context).finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            });
        }

    }
}
