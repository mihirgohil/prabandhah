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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.R;
import com.prabandhah.prabandhah.Ui_Detail_ViewOfEvent;
import com.prabandhah.prabandhah.Ui_Includedteam_taskDetail;
import com.prabandhah.prabandhah.dataclasses.Profile;
import com.prabandhah.prabandhah.dataclasses.Task;
import com.prabandhah.prabandhah.dataclasses.Teams;

import java.util.ArrayList;

public class AdapterForIncludedteam extends RecyclerView.Adapter<AdapterForIncludedteam.Viewholder> {
    Context context;
    String companyid,eventid;
    ArrayList<String> teamslist;
    DatabaseReference dba;
    public AdapterForIncludedteam(Context c,ArrayList<String> t,String cid,String e){
        context =c;
        teamslist = t;
        companyid = cid;
        eventid = e;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterForIncludedteam.Viewholder(LayoutInflater.from(context).inflate(R.layout.reclyerforincludedteam,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {
       dba = FirebaseDatabase.getInstance().getReference("Teams").child(companyid).child(teamslist.get(position));
       dba.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               holder.nameoftheteam.setText(dataSnapshot.child("team_name").getValue().toString());
               dba = FirebaseDatabase.getInstance().getReference("Teams").child(companyid).child(teamslist.get(position)).child("team_head");
               dba.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String teamheadid;
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                            teamheadid = dataSnapshot1.getKey();
                            dba = FirebaseDatabase.getInstance().getReference("users").child(teamheadid);
                                    dba.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    holder.nameoftheteamhead.setText(dataSnapshot.child("user_name").getValue().toString());
                                    dba = FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid").child(eventid).child("teamid").child(teamslist.get(position)).child("task");
                                    dba.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            int count = 0;
                                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                                count++;
                                            }
                                            holder.nooftask.setText(String.valueOf(count));
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }

    @Override
    public int getItemCount() {
        return teamslist.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView nameoftheteam,nameoftheteamhead,nooftask;
        public Viewholder(View itemView) {
            super(itemView);
            nameoftheteam = itemView.findViewById(R.id.nameofteam);
            nameoftheteamhead = itemView.findViewById(R.id.nameofteamhead);
            nooftask = itemView.findViewById(R.id.nooftask);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(context, Ui_Includedteam_taskDetail.class);
                    intent.putExtra("selectedteam",teamslist.get(getAdapterPosition()));
                    intent.putExtra("eventid",eventid);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
            });
        }
    }
}
