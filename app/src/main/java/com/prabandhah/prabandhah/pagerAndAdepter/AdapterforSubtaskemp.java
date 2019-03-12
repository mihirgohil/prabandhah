package com.prabandhah.prabandhah.pagerAndAdepter;

import android.content.Context;
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
import com.prabandhah.prabandhah.dataclasses.Profile;
import com.prabandhah.prabandhah.dataclasses.SubTaskemp;

import java.util.ArrayList;

public class AdapterforSubtaskemp extends RecyclerView.Adapter<AdapterforSubtaskemp.viewHolder>{
    ArrayList<SubTaskemp> subTaskemps;
    Context context;
    public AdapterforSubtaskemp(Context c,ArrayList<SubTaskemp> s){
        context =c;
        subTaskemps = s;
    }
    @NonNull
    @Override
    public AdapterforSubtaskemp.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterforSubtaskemp.viewHolder(LayoutInflater.from(context).inflate(R.layout.recyleviewforsubtaskdetail,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterforSubtaskemp.viewHolder holder, int position) {
        FirebaseDatabase.getInstance().getReference("users").child(subTaskemps.get(position).userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile p=dataSnapshot.getValue(Profile.class);
                holder.nameofemp.setText(p.user_name);
             }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.status.setText(subTaskemps.get(position).status);
    }

    @Override
    public int getItemCount() {
        return subTaskemps.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView nameofemp,status;
        public viewHolder(View itemView) {
            super(itemView);
            nameofemp = itemView.findViewById(R.id.nameofemployee);
            status = itemView.findViewById(R.id.statustxt);
        }
    }
}
