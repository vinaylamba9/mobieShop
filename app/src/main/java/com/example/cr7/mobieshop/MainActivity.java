package com.example.cr7.mobieshop;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.cr7.mobieshop.category_class.EXTRA_CATEGORY;


public class MainActivity extends AppCompatActivity implements ProductAdapter.OnItemClickListener {

    public static final String EXTRA_URL = "imageurl";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_BRANDNAME = "brandname";
    public static final String EXTRA_DESC = "desc";
    public static final String EXTRA_PRICE = "price";
    public static final String EXTRA_REALPRICE = "real_price";
    public static final String EXTRA_POSTTIME = "post_time";
    public static final String EXTRA_USER_ID = "user_id";


    String category;
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<upload_data> mUploadList;
    private ProgressBar mProgressBar;
    private ProgressBar pb;
    private FirebaseDatabase mfirebaseDatabase;
    private DatabaseReference mdatabaseReference;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        mProgressBar = findViewById(R.id.progress_bar);
        //pb = findViewById(R.id.spin_kit);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mUploadList.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //pb = findViewById(R.id.spin_kit);
        mProgressBar = findViewById(R.id.progress_bar);

        mUploadList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

        mfirebaseDatabase = FirebaseDatabase.getInstance();
       // mdatabaseReference = mfirebaseDatabase.getReference("uploads_data");

        Intent intent_sports = getIntent();
        category = intent_sports.getStringExtra(EXTRA_CATEGORY);
        Toast.makeText(this, category, Toast.LENGTH_SHORT).show();

        Query query = FirebaseDatabase.getInstance().getReference(category);

         
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot i : dataSnapshot.getChildren())
                {
                    upload_data upload = i.getValue(upload_data.class);
                    mUploadList.add(upload);
                }
                adapter = new ProductAdapter(MainActivity.this,mUploadList);
                recyclerView.setAdapter(adapter);
                mProgressBar.setVisibility(View.INVISIBLE);
                adapter.setOnItemClickListener(MainActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void FirebaseSearch(String searchText)
    {
        Toast.makeText(this, searchText, Toast.LENGTH_SHORT).show();
        Query firebaseSearchQuery = FirebaseDatabase.getInstance().getReference(category).orderByChild("mName").startAt(searchText).endAt(searchText+"\uf8ff");

         firebaseSearchQuery.addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 mUploadList.clear();
                 if(dataSnapshot.exists())
                 {
                     //  Toast.makeText(MainActivity.this, "EXISTSSSSSS", Toast.LENGTH_SHORT).show();
                     for(DataSnapshot snapshot : dataSnapshot.getChildren())
                     {
                         upload_data upload = snapshot.getValue(upload_data.class);
                         mUploadList.add(upload);
                     }
                     adapter.notifyDataSetChanged();
                     recyclerView.setAdapter(adapter);
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
                 Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                 mProgressBar.setVisibility(View.INVISIBLE);
             }
         });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("Search...");
        searchView.clearFocus();
        SearchView.SearchAutoComplete searchAutoComplete =
                (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.BLACK);
        searchAutoComplete.setTextColor(Color.BLACK);
        ImageView searchIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchIcon.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                FirebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                FirebaseSearch(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int res_id = item.getItemId();
        if(res_id==R.id.options)
        {
            Toast.makeText(this, "You Selected options.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onItemClick(int position) {
        Intent detailedIntent = new Intent(this,detailactivity.class);
        upload_data clickedItem = mUploadList.get(position);
        detailedIntent.putExtra(EXTRA_URL,clickedItem.getmImageUrl());
        detailedIntent.putExtra(EXTRA_TITLE,clickedItem.getmName());
        detailedIntent.putExtra(EXTRA_PRICE,clickedItem.getmPrice());
        detailedIntent.putExtra(EXTRA_DESC,clickedItem.getmDesc());
        detailedIntent.putExtra(EXTRA_BRANDNAME,clickedItem.getmBrandName());
        detailedIntent.putExtra(EXTRA_REALPRICE,clickedItem.getmOriginalPrice());
        detailedIntent.putExtra(EXTRA_POSTTIME,clickedItem.getPost_time());
        detailedIntent.putExtra(EXTRA_USER_ID,clickedItem.getUser_id());
        startActivity(detailedIntent);
    }
}
