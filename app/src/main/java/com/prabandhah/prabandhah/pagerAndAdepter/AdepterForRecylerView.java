package com.prabandhah.prabandhah.pagerAndAdepter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.PluralsRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.LoginPage;
import com.prabandhah.prabandhah.R;
import com.prabandhah.prabandhah.Ui_AssignNewEventmanger;
import com.prabandhah.prabandhah.Ui_createTeam;
import com.prabandhah.prabandhah.Ui_employeeList;
import com.prabandhah.prabandhah.Ui_home;
import com.prabandhah.prabandhah.dataclasses.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mihir on 2/22/2019.
 */

public class AdepterForRecylerView extends RecyclerView.Adapter<AdepterForRecylerView.Viewhodler>{
    Context context;
    ArrayList <Profile> profiles;
    ArrayList<Profile> selectedProfile = new ArrayList<Profile>();
    ArrayList<String> selected = new ArrayList<String>();
    String ActivityName;
    String role;
    public AdepterForRecylerView(Context c,ArrayList<Profile> p ,String s )
    {   ActivityName = s;
        context = c;
        profiles = p;
    }
    @NonNull
    @Override
    public Viewhodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewhodler(LayoutInflater.from(context).inflate(R.layout.reclyerviewforemplist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhodler holder, int position) {
            holder.name.setText(profiles.get(position).getUser_name());
            holder.email.setText(profiles.get(position).getUser_mail_id());
          if(ActivityName.equals("UI_AssignNewAdmin")||ActivityName.equals("Ui_AssignNewEventmanger") || ActivityName.equals("Ui_createTeam")){
              holder.checkBox.setVisibility(View.VISIBLE);
          }

    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class Viewhodler extends RecyclerView.ViewHolder {
        TextView name, email;
        CheckBox checkBox;

        public Viewhodler(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            checkBox = itemView.findViewById(R.id.ckb);
            DatabaseReference user = FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            user.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Profile profile = dataSnapshot.getValue(Profile.class);
                    role = profile.getRole();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (role.equals("1")) {
                        if (ActivityName.equals("EmpList")) {
                            String username = profiles.get(getAdapterPosition()).getUser_name();
                            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
                            builder.setTitle("Remove Employee!")
                                    .setMessage("Are you sure you want to Remove " + username + " From Company?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String userid;
                                            DatabaseReference dba = FirebaseDatabase.getInstance().getReference("users").child(profiles.get(getAdapterPosition()).getUser_id()).child("company_id");
                                            dba.setValue("");
                                            Toast.makeText(context, "remove user from team remaining", Toast.LENGTH_SHORT).show();
                                        }

                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                        }
                        if (ActivityName.equals("EventManagerList")) {
                            String username = profiles.get(getAdapterPosition()).getUser_name();
                            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
                            builder.setTitle("Remove EventManger!")
                                    .setMessage("Are you sure you want to Remove " + username + " From EventManger Post?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String userid;
                                            userid = profiles.get(getAdapterPosition()).getUser_id();
                                            DatabaseReference dba = FirebaseDatabase.getInstance().getReference("users").child(userid).child("role");
                                            String rol = "4";
                                            dba.setValue(rol);
                                        }

                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                        }
                        if (ActivityName.equals("AdminList")) {
                            String username = profiles.get(getAdapterPosition()).getUser_name();
                            String uid = profiles.get(getAdapterPosition()).getUser_id();
                            String cuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            if(!uid.equals(cuser)){
                            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
                            builder.setTitle("Remove Admin!")
                                    .setMessage("Are you sure you want to Remove " + username + " From Admin Post?" )
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            DatabaseReference dba = FirebaseDatabase.getInstance().getReference("users").child(profiles.get(getAdapterPosition()).getUser_id()).child("role");
                                            String rol = "4";
                                            dba.setValue(rol);
                                        }

                                    })
                                    .setNegativeButton("No", null)
                                    .show();
                            }
                        }
                    }

                    return true;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityName.equals("UI_AssignNewAdmin")) {
                        String tmp = profiles.get(getAdapterPosition()).getUser_id();
                        if (checkBox.isChecked()) {
                            checkBox.setChecked(false);
                            //selected.add(profiles.get(getAdapterPosition()).getUser_name());
                            selected.remove(tmp);
                        } else if (!checkBox.isChecked()) {
                            checkBox.setChecked(true);
                            selected.add(tmp);
                        }

                    }
                    if (ActivityName.equals("Ui_AssignNewEventmanger")) {
                        String tmp = profiles.get(getAdapterPosition()).getUser_id();
                        if (checkBox.isChecked()) {
                            checkBox.setChecked(false);
                            selected.remove(tmp);
                        } else if (!checkBox.isChecked()) {
                            checkBox.setChecked(true);
                            selected.add(tmp);
                        }
                    }
                    if (ActivityName.equals("Ui_createTeam")) {
                        String tmp = profiles.get(getAdapterPosition()).getUser_id();
                        if (checkBox.isChecked()) {
                            checkBox.setChecked(false);
                            selected.remove(tmp);
                        } else if (!checkBox.isChecked()) {
                            checkBox.setChecked(true);
                            selected.add(tmp);
                        }
                    }

                }
            });
        }
    }
    public void selectedlistadmin()
    {
        for(int i=0; i<selected.size();i++){
            DatabaseReference dba = FirebaseDatabase.getInstance().getReference("users").child(selected.get(i)).child("role");
            dba.setValue("1");
        }
    }
    public void selectedlisteventManger()
    {
        for(int i=0; i<selected.size();i++){
            DatabaseReference dba = FirebaseDatabase.getInstance().getReference("users").child(selected.get(i)).child("role");
            dba.setValue("2");
        }
    }
    public ArrayList<String> selectedListForTeam(){
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<selected.size();i++){
            stringBuffer.append(selected.get(i));
        }
        Toast.makeText(context, "Selected member from adpter"+stringBuffer.toString(), Toast.LENGTH_SHORT).show();
        return selected;
    }

}
