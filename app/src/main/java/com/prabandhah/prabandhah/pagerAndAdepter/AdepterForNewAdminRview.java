package com.prabandhah.prabandhah.pagerAndAdepter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prabandhah.prabandhah.R;
import com.prabandhah.prabandhah.dataclasses.Profile;

import java.util.ArrayList;

/**
 * Created by mihir on 2/23/2019.
 */

public class AdepterForNewAdminRview extends RecyclerView.Adapter<AdepterForNewAdminRview.Viewhodler> {
    Context context;
    ArrayList<Profile> profiles;
    public AdepterForNewAdminRview(Context c, ArrayList<Profile> p)
    {
        context = c;
        profiles = p;
    }
    @NonNull
    @Override
    public Viewhodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdepterForNewAdminRview.Viewhodler(LayoutInflater.from(context).inflate(R.layout.reclyerviewforemplist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhodler holder, int position) {
        holder.name.setText(profiles.get(position).getUser_name());
        holder.email.setText(profiles.get(position).getUser_mail_id());
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class Viewhodler extends RecyclerView.ViewHolder{
        TextView name,email;
        public Viewhodler(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
        }
    }
}
