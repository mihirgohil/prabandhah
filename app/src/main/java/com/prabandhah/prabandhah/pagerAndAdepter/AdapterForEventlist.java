package com.prabandhah.prabandhah.pagerAndAdepter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prabandhah.prabandhah.R;
import com.prabandhah.prabandhah.Ui_Detail_ViewOfEvent;
import com.prabandhah.prabandhah.Ui_team_in_DetaiView;
import com.prabandhah.prabandhah.dataclasses.EventClass;
import com.prabandhah.prabandhah.dataclasses.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdapterForEventlist extends RecyclerView.Adapter<AdapterForEventlist.myViewholder> {
    ArrayList<EventClass> eventlist;
    String username;
    String colorprv=null;
    String activityname;
    Context context;
    public AdapterForEventlist(Context c, ArrayList<EventClass> e,String a){
            eventlist = e;
            context = c;
            activityname = a;
    }
    @NonNull
    @Override
    public myViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterForEventlist.myViewholder(LayoutInflater.from(context).inflate(R.layout.recycler_eventlist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final myViewholder holder, final int position) {
            holder.nameofevent.setText(eventlist.get(position).getEventname());
            holder.enddate.setText(eventlist.get(position).getEnddate());

            //color randomly
            Random random = new Random();
            //heavy method
            //final String[] letters = "0123456789ABCDEF".split("");

            /*for (int i = 0; i < 6; i++) {
            color += letters[Math.round(random.nextFloat() * 15)];
            }*/
            String color = "#";
            List <String> colorstr = new ArrayList<>();
            colorstr.add("153465");
            colorstr.add("67160e");
            colorstr.add("135714");
            colorstr.add("585858");
            colorstr.add("b45c18");
            colorstr.add("000000");
            color += colorstr.get(random.nextInt(colorstr.size()));
           // holder.card.setCardBackgroundColor(Color.parseColor(color));
            //end color randomly
            holder.endtime.setText(eventlist.get(position).getEndtime());
        DatabaseReference user = FirebaseDatabase.getInstance().getReference("users").child(eventlist.get(position).getEventmanager());
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Profile profile = dataSnapshot.getValue(Profile.class);
                username = profile.user_name;
                holder.eventmangername.setText(username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(activityname.equals("Event")){
            holder.spinner.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return eventlist.size();
    }

    public class myViewholder extends RecyclerView.ViewHolder{
        TextView nameofevent;
        TextView enddate;
        TextView endtime;
        TextView eventmangername;
        TextView txtstatus;
        Spinner spinner;
        CardView card;
        public myViewholder(View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.cardview);
            nameofevent = itemView.findViewById(R.id.nameofevent);
            enddate = itemView.findViewById(R.id.enddate);
            endtime = itemView.findViewById(R.id.endtime);
            eventmangername = itemView.findViewById(R.id.eventmanagername);
            txtstatus = itemView.findViewById(R.id.txtstatus);
            spinner = itemView.findViewById(R.id.statusspiner);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(context, Ui_Detail_ViewOfEvent.class);
                    intent.putExtra("eventid",eventlist.get(getAdapterPosition()).getEventid());
                    context.startActivity(intent);
                }
            });
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position == 1){
                        final String eventid = eventlist.get(getAdapterPosition()).eventid;
                        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Profile profile = dataSnapshot.getValue(Profile.class);
                                FirebaseDatabase.getInstance().getReference("EventMaster").child(profile.company_id).child(eventid).child("eventstatus").setValue("completed");
                                Toast.makeText(context, "Event Completed", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

}
