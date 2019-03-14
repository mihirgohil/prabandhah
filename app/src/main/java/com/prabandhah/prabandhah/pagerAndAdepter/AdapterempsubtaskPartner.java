package com.prabandhah.prabandhah.pagerAndAdepter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.R;
import com.prabandhah.prabandhah.dataclasses.Profile;
import com.prabandhah.prabandhah.dataclasses.SubTaskemp;

import java.util.ArrayList;

public class AdapterempsubtaskPartner extends RecyclerView.Adapter<AdapterempsubtaskPartner.Viewholder> {
    ArrayList<SubTaskemp> subTaskemps;
    Context context;
    public AdapterempsubtaskPartner(Context c,ArrayList<SubTaskemp> s){
        subTaskemps =s;
        context =c;
    }
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterempsubtaskPartner.Viewholder(LayoutInflater.from(context).inflate(R.layout.recylerfortaskpartner,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, int position) {
        holder.status.setText(subTaskemps.get(position).status);
        FirebaseDatabase.getInstance().getReference("users").child(subTaskemps.get(position).userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile p = dataSnapshot.getValue(Profile.class);
                holder.nameofpartner.setText(p.user_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return subTaskemps.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{
        TextView nameofpartner,status;
        public Viewholder(View itemView) {
            super(itemView);
            nameofpartner = itemView.findViewById(R.id.nameofpartner);
            status = itemView.findViewById(R.id.statustxt);
        }
    }
}
