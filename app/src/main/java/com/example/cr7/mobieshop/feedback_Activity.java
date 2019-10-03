package com.example.cr7.mobieshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class feedback_Activity extends AppCompatActivity {

    EditText feedback_edittext;
    Button submit;
    RecyclerView recyclerView;
    private feedback_adapter adapter;
    private List<feedback_data> feedback_dataList;


    Toolbar toolbar;
    DatabaseReference databaseref;
    upload_userdata upload;

    public String name;
    public String uid;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_activity);
        toolbar = findViewById(R.id.app_bar2);
        setSupportActionBar(toolbar);

        mProgressBar = findViewById(R.id.number_progress_bar);
        feedback_dataList = new ArrayList<>();
        recyclerView = findViewById(R.id.feedback_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Query query1 = FirebaseDatabase.getInstance().getReference("feedback_data");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                feedback_dataList.clear();
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot i : dataSnapshot.getChildren())
                    {
                        feedback_data upload = i.getValue(feedback_data.class);
                        feedback_dataList.add(upload);
                    }
                    adapter = new feedback_adapter(feedback_Activity.this,feedback_dataList);
                    recyclerView.setAdapter(adapter);
                    mProgressBar.setVisibility(View.GONE);
                }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(feedback_Activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);
            }
        });



        feedback_edittext = findViewById(R.id.feedback);
        submit = findViewById(R.id.feedback_submit);

        Intent intent2 = getIntent();
        String mail = intent2.getStringExtra("currentuser");
        Query query = FirebaseDatabase.getInstance().getReference("account_data").orderByChild("e_mail").equalTo(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        upload = new upload_userdata();
                        upload.setUser_name(ds.getValue(upload_userdata.class).getUser_name());
                        upload.setReg_number(ds.getValue(upload_userdata.class).getReg_number());
                    }
                }
                name = upload.getUser_name();
                uid = upload.getReg_number();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(feedback_Activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(feedback_edittext.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(feedback_Activity.this, "Cannot Save Null Feedback.", Toast.LENGTH_SHORT).show();
                }
                else{
                    uploadFile();
                }
            }
        });
    }

    public void uploadFile(){
        databaseref = FirebaseDatabase.getInstance().getReference("feedback_data");
        Calendar calendar = Calendar.getInstance();
        final String post_time = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());

        //feedback data uploading....

        feedback_data feedback_data = new feedback_data(name,uid,feedback_edittext.getText().toString().trim(),post_time.trim());
        String uploadId = databaseref.push().getKey();
        databaseref.child(uploadId).setValue(feedback_data);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
