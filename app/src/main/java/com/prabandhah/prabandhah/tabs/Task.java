package com.prabandhah.prabandhah.tabs;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.R;
import com.prabandhah.prabandhah.dataclasses.SubTask;
import com.prabandhah.prabandhah.dataclasses.SubTaskemp;
import com.prabandhah.prabandhah.dataclasses.Teams;
import com.prabandhah.prabandhah.pagerAndAdepter.AdapterForEmployeesubTask;

import java.util.ArrayList;

public class Task extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes
        return inflater.inflate(R.layout.activity_task, container, false);
    }
    RecyclerView recyclerView;
    AdapterForEmployeesubTask adapterForEmployeesubTask;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences pref = getContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        final SharedPreferences.Editor editor = pref.edit();
        String companyid = pref.getString("companyid","");
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
      //  Toast.makeText(getContext(), "at task", Toast.LENGTH_SHORT).show();
        final DatabaseReference dba= FirebaseDatabase.getInstance().getReference("Teams").child(companyid);
        dba.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<Teams> teams = new ArrayList<Teams>();
                final ArrayList<SubTaskemp> subTaskemps = new ArrayList<SubTaskemp>();
                final String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                for(final DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    final Teams teamsclass = dataSnapshot1.getValue(Teams.class);
                    dba.child(dataSnapshot1.getKey()).child("employee_list").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(final DataSnapshot dataSnapshot2empid:dataSnapshot.getChildren()){
                                if(userid.equals(dataSnapshot2empid.getKey())){
                                    final DatabaseReference dbadata = FirebaseDatabase.getInstance().getReference("TaskMaster").child("eventid");
                                    dbadata.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                           for(DataSnapshot dataSnapshot3:dataSnapshot.getChildren()){
                                               final DatabaseReference dbadata1 =dbadata.child(dataSnapshot3.getKey()).child("teamid").child(teamsclass.team_id).child("task");
                                               dbadata1.addListenerForSingleValueEvent(new ValueEventListener() {
                                                   @Override
                                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                       for(final DataSnapshot dataSnapshot4task:dataSnapshot.getChildren()){

                                                           dbadata1.child(dataSnapshot4task.getKey()).child("subtask").addListenerForSingleValueEvent(new ValueEventListener() {
                                                               @Override
                                                               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                                   for(final DataSnapshot dataSnapshot5:dataSnapshot.getChildren()){
                                                                       final SubTask subTask=dataSnapshot5.getValue(SubTask.class);
                                                                       String subtaskstatus = dataSnapshot5.child("subtaskstatus").getValue().toString();
                                                                    //   Toast.makeText(getContext(), "j\n"+subtaskstatus, Toast.LENGTH_SHORT).show();
                                                                       if(subtaskstatus.equals("assigned")){
                                                                           dbadata1.child(dataSnapshot4task.getKey()).child("subtask").child(dataSnapshot5.getKey()).child("employeefortask").addListenerForSingleValueEvent(new ValueEventListener() {
                                                                           @Override
                                                                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                               for(DataSnapshot dataSnapshot6:dataSnapshot.getChildren()){

                                                                                   if(dataSnapshot6.getKey().equals(userid)){
                                                                                       SubTaskemp subTask=dataSnapshot6.getValue(SubTaskemp.class);
                                                                                           subTaskemps.add(subTask);

                                                                                   }
                                                                               }
                                                                               StringBuffer stringBuffer = new StringBuffer();
                                                                               ArrayList<SubTaskemp> subTaskemps1 = new ArrayList<SubTaskemp>();
                                                                               for(int i=0;i<subTaskemps.size();i++){
                                                                                   stringBuffer.append(subTaskemps.get(i).subtaskid).append("\n");
                                                                                 //  if(subTaskemps.get(i).status.equals("assigned")){
                                                                                   //    subTaskemps1.add(subTaskemps.get(i));
                                                                                   //}
                                                                               }
                                                                             //  Toast.makeText(getActivity(), "task"+stringBuffer.toString(), Toast.LENGTH_SHORT).show();
                                                                               for(int j=0 ; j<subTaskemps.size();j++){
                                                                                   for(int i1=j+1;i1<subTaskemps.size();i1++){
                                                                                       if(subTaskemps.get(j).subtaskid.equals(subTaskemps.get(i1).subtaskid)){
                                                                                           subTaskemps.remove(i1);
                                                                                       }
                                                                                   }
                                                                               }
                                                                               for(int i=0;i<subTaskemps.size();i++){
                                                                                   if(subTask.subtaskstatus.equals("completed")){
                                                                                       if(subTask.subtaskid.equals(subTaskemps.get(i).subtaskid)){
                                                                                           subTaskemps.remove(i);
                                                                                       }
                                                                                   }
                                                                               }
                                                                               adapterForEmployeesubTask = new AdapterForEmployeesubTask(getContext(),subTaskemps);
                                                                               recyclerView.setAdapter(adapterForEmployeesubTask);
                                                                           }

                                                                           @Override
                                                                           public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                           }
                                                                       });
                                                                       }
                                                                   }
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
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }

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
}
