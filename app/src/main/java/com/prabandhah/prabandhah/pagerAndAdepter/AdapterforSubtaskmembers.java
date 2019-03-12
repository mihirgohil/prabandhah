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
import com.prabandhah.prabandhah.dataclasses.Profile;

import java.util.ArrayList;

public class AdapterforSubtaskmembers extends RecyclerView.Adapter<AdapterforSubtaskmembers.viewHolder> {
    Context context;
    ArrayList<Profile> profiles;
    ArrayList<String> selectprofile = new ArrayList<String>();
    public  AdapterforSubtaskmembers(Context c,ArrayList<Profile> p){
        context = c;
        profiles = p;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterforSubtaskmembers.viewHolder(LayoutInflater.from(context).inflate(R.layout.reclyerviewforemplist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.name.setText(profiles.get(position).getUser_name());
        holder.email.setText(profiles.get(position).getUser_mail_id());
        holder.radioButton.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView name, email;
        CheckBox radioButton;
        public viewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            radioButton = itemView.findViewById(R.id.ckb);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tmp = profiles.get(getAdapterPosition()).user_id;
                    if(radioButton.isChecked()){
                        radioButton.setChecked(false);
                        notifyDataSetChanged();
                        selectprofile.remove(tmp);
                    }
                    else{
                        radioButton.setChecked(true);
                        notifyDataSetChanged();
                        selectprofile.add(tmp);
                    }
                }
            });
        }
    }
    public ArrayList<String> getselected(){
        return selectprofile;
    }
}
