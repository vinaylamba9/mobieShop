package com.example.cr7.mobieshop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class my_adds_activity extends AppCompatActivity implements my_adds_adapter.OnItemClickListener {


    private RecyclerView recyclerView;
    private my_adds_adapter adapter;
    private List<my_add_data> dataList;
    FirebaseAuth auth;
    my_add_data upload;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_posted_adds);

        mProgressBar = findViewById(R.id.pb);
        auth  = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerview_adds);
        dataList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        String current_user = auth.getCurrentUser().getEmail();
        Toast.makeText(this, current_user, Toast.LENGTH_SHORT).show();
        Query query = FirebaseDatabase.getInstance().getReference("my_adds_posted").orderByChild("mail").equalTo(current_user);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataList.clear();
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot i : dataSnapshot.getChildren())
                    {
                        upload = i.getValue(my_add_data.class);
                        dataList.add(upload);
                    }
                    adapter = new my_adds_adapter(my_adds_activity.this,dataList);
                    recyclerView.setAdapter(adapter);
                    mProgressBar.setVisibility(View.GONE);
                    adapter.setOnItemClickListener(my_adds_activity.this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(my_adds_activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        my_add_data clickedItem = dataList.get(position);
        String del_id = clickedItem.getUploadid();
        String del_item_id = clickedItem.getUpload_item_id();
        String category = clickedItem.getCategory();
        DatabaseReference del_add = FirebaseDatabase.getInstance().getReference("my_adds_posted").child(del_id);
        del_add.removeValue();
        DatabaseReference del_item_ref = FirebaseDatabase.getInstance().getReference(category).child(del_item_id);
        del_item_ref.removeValue();
        Toast.makeText(this, "Your add has been REMOVED.", Toast.LENGTH_SHORT).show();
    }
}
