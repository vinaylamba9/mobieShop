package com.example.cr7.mobieshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationList extends AppCompatActivity {

    private RecyclerView rvNotification;
    private LinearLayoutManager layoutManager;
    public Pref pref;
    private ArrayList<NotificationModel> arrayList = new ArrayList<>();
    private LinearLayout llLoader,llNothing;
    private NotificationAdapter notificationAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_list);


        pref = new Pref(this);

        rvNotification = (RecyclerView)findViewById(R.id.rvNotification);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvNotification.setLayoutManager(layoutManager);

        llLoader = (LinearLayout)findViewById(R.id.llLoader);
        llNothing = (LinearLayout)findViewById(R.id.llNothing);
        getNotificationList(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }


    private void getNotificationList(String sendTo) {
        llNothing.setVisibility(View.GONE);
        llLoader.setVisibility(View.VISIBLE);
        rvNotification.setVisibility(View.GONE);
        Query query = FirebaseDatabase.getInstance().getReference("notification").orderByChild("send_to")
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    arrayList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        NotificationModel notificationModel = dataSnapshot1.getValue(NotificationModel.class);
                        notificationModel.setTitle(notificationModel.getTitle());
                        notificationModel.setBody(notificationModel.getBody());
                        //notificationModel.setPhone_num(notificationModel.getPhone_num());
                        notificationModel.setSend_to(notificationModel.getSend_to());
                        notificationModel.setSender(notificationModel.getSender());
                        arrayList.add(notificationModel);
                    }
                    if (arrayList.size() > 0) {
                        llLoader.setVisibility(View.GONE);
                        llNothing.setVisibility(View.GONE);
                        setUpRecycler();
                    } else {
                        llLoader.setVisibility(View.GONE);
                        rvNotification.setVisibility(View.GONE);
                        llNothing.setVisibility(View.VISIBLE);
                    }
                }else {
                    rvNotification.setVisibility(View.GONE);
                    llLoader.setVisibility(View.GONE);
                    llNothing.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Error", databaseError.getMessage().toString());
                rvNotification.setVisibility(View.GONE);
                llLoader.setVisibility(View.GONE);
                llNothing.setVisibility(View.VISIBLE);


            }
        });
    }

    private void setUpRecycler(){
        rvNotification.setVisibility(View.VISIBLE);
        notificationAdapter = new NotificationAdapter(NotificationList.this, arrayList);
        rvNotification.setAdapter(notificationAdapter);
    }
}
