package com.example.cr7.mobieshop;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class category_class extends AppCompatActivity implements category_card_adapter.OnItemClickListener {


    public static final String EXTRA_CATEGORY = "category";

    String mail;
    RecyclerView rv;
    category_card_adapter adapter;
    private Toolbar toolbar;
    FloatingActionButton floatingActionButton;
    ArrayList<category_data> category_data_list;
    BottomAppBar bottomAppBar;
    ImageButton home_button;
    ImageButton feedback_activity_button,myadds_activity,my_account_activity;
    FirebaseAuth auth;
    upload_userdata upload;
    String namee;
    String img_str;

    private DrawerLayout dlMain;
    public LinearLayout llHome, llposted_adds, llmanage_account,llcontactus ,llLogout,llfeeback;
    private TextView tvName, tvEmail;
    private ImageView imgProfile;
    private ImageButton notification;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_category);
        bottomAppBar = findViewById(R.id.bottom_app_bar);
        toolbar = findViewById(R.id.app_bar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        setSupportActionBar(toolbar);

        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        imgProfile = findViewById(R.id.imgProfile);

        notification = findViewById(R.id.notification_imgbutton);
        auth  = FirebaseAuth.getInstance();
        category_data_list = new ArrayList();
        mail = auth.getCurrentUser().getEmail();

        category_data data = new category_data();
        data.setImage(R.drawable.micro);
        data.setTitle("Electronics");
        category_data_list.add(data);

        data = new category_data();
        data.setImage(R.drawable.fashionn);
        data.setTitle("Fashion");
        category_data_list.add(data);

        data = new category_data();
        data.setImage(R.drawable.spy);
        data.setTitle("Books & Study Material");
        category_data_list.add(data);

        data = new category_data();
        data.setImage(R.drawable.pho);
        data.setTitle("Mobile Accessories");
        category_data_list.add(data);

        data = new category_data();
        data.setImage(R.drawable.cosmeti);
        data.setTitle("Beauty & Personal Care");
        category_data_list.add(data);

        data = new category_data();
        data.setImage(R.drawable.sportss);
        data.setTitle("Sports");
        category_data_list.add(data);

        data = new category_data();
        data.setImage(R.drawable.happ);
        data.setTitle("Home appliances");
        category_data_list.add(data);

        data = new category_data();
        data.setImage(R.drawable.fur);
        data.setTitle("Furniture");
        category_data_list.add(data);

        data = new category_data();
        data.setImage(R.drawable.homeedecor);
        data.setTitle("Home decoration");
        category_data_list.add(data);

        data = new category_data();
        data.setImage(R.drawable.other);
        data.setTitle("Other");
        category_data_list.add(data);


        home_button= findViewById(R.id.home_button);
        myadds_activity = findViewById(R.id.myadds_activity);
        my_account_activity = findViewById(R.id.my_account_activity);
        feedback_activity_button = findViewById(R.id.feedback_activity);

        rv = findViewById(R.id.category_recyclerview);
        rv.setHasFixedSize(true);
        rv.setNestedScrollingEnabled(true);
        rv.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        adapter = new category_card_adapter(category_class.this,category_data_list);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(category_class.this);

        Query query = FirebaseDatabase.getInstance().getReference("account_data").orderByChild("e_mail").equalTo(auth.getCurrentUser().getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        upload = new upload_userdata();
                        upload.setUser_name(ds.getValue(upload_userdata.class).getUser_name());
                        upload.setProfile_pic_url(ds.getValue(upload_userdata.class).getProfile_pic_url());
                        namee = upload.getUser_name();
                        img_str = upload.getProfile_pic_url();
                    }
                }

                tvName.setText(namee);
                Picasso.get().load(img_str).into(imgProfile);

                final String token = FirebaseInstanceId.getInstance().getToken();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendRegistrationToServer(token,namee);
                    }
                }, 10000);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(category_class.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        tvEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(category_class.this, "You are on Home Page.", Toast.LENGTH_SHORT).show();
            }
        });

        myadds_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(category_class.this,my_adds_activity.class));
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noti = new Intent(category_class.this,NotificationList.class);
                startActivity(noti);
            }
        });
        feedback_activity_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_user = auth.getCurrentUser().getEmail();
                Intent feedback_go = new Intent(category_class.this,feedback_Activity.class);
                feedback_go.putExtra("currentuser",current_user);
                startActivity(feedback_go);
            }
        });

        my_account_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current_user = auth.getCurrentUser().getEmail();
                Intent my_accountgo = new Intent(category_class.this,my_account.class);
                my_accountgo.putExtra("currentuser",current_user);
                startActivity(my_accountgo);
            }
        });

        dlMain = findViewById(R.id.dlMain);

        llfeeback = findViewById(R.id.llfeedback);
        llHome = (LinearLayout) findViewById(R.id.llHome);
        llmanage_account =  findViewById(R.id.llmanage_account);
        llcontactus =  findViewById(R.id.llcontactus);
        llposted_adds =  findViewById(R.id.llpostedadds);
        llLogout = findViewById(R.id.llLogout);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!dlMain.isDrawerOpen(Gravity.START)) {
                    dlMain.openDrawer(Gravity.START);
                } else {
                    dlMain.closeDrawer(Gravity.START);
                }
            }
        });

        llHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlMain.closeDrawer(Gravity.START);

                llfeeback.setBackgroundColor(Color.parseColor("#ffffff"));
                llHome.setBackgroundColor(Color.parseColor("#EAE6E5"));
                llmanage_account.setBackgroundColor(Color.parseColor("#ffffff"));
                llposted_adds.setBackgroundColor(Color.parseColor("#ffffff"));
                llcontactus.setBackgroundColor(Color.parseColor("#ffffff"));
                llLogout.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });
        llposted_adds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlMain.closeDrawer(Gravity.START);

                llfeeback.setBackgroundColor(Color.parseColor("#ffffff"));
                llHome.setBackgroundColor(Color.parseColor("#ffffff"));
                llmanage_account.setBackgroundColor(Color.parseColor("#ffffff"));
                llposted_adds.setBackgroundColor(Color.parseColor("#EAE6E5"));
                llcontactus.setBackgroundColor(Color.parseColor("#ffffff"));
                llLogout.setBackgroundColor(Color.parseColor("#ffffff"));

                startActivity(new Intent(category_class.this,my_adds_activity.class));
            }
        });
        llmanage_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlMain.closeDrawer(Gravity.START);

                llfeeback.setBackgroundColor(Color.parseColor("#ffffff"));
                llHome.setBackgroundColor(Color.parseColor("#ffffff"));
                llmanage_account.setBackgroundColor(Color.parseColor("#EAE6E5"));
                llposted_adds.setBackgroundColor(Color.parseColor("#ffffff"));
                llcontactus.setBackgroundColor(Color.parseColor("#ffffff"));
                llLogout.setBackgroundColor(Color.parseColor("#ffffff"));

                String current_user = auth.getCurrentUser().getEmail();
                Intent my_accountgo = new Intent(category_class.this,my_account.class);
                my_accountgo.putExtra("currentuser",current_user);
                startActivity(my_accountgo);

            }
        });
        llcontactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlMain.closeDrawer(Gravity.START);

                llfeeback.setBackgroundColor(Color.parseColor("#ffffff"));
                llHome.setBackgroundColor(Color.parseColor("#ffffff"));
                llmanage_account.setBackgroundColor(Color.parseColor("#ffffff"));
                llposted_adds.setBackgroundColor(Color.parseColor("#ffffff"));
                llcontactus.setBackgroundColor(Color.parseColor("#EAE6E5"));
                llLogout.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });
        llfeeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlMain.closeDrawer(Gravity.START);

                llfeeback.setBackgroundColor(Color.parseColor("#EAE6E5"));
                llHome.setBackgroundColor(Color.parseColor("#ffffff"));
                llmanage_account.setBackgroundColor(Color.parseColor("#ffffff"));
                llposted_adds.setBackgroundColor(Color.parseColor("#ffffff"));
                llcontactus.setBackgroundColor(Color.parseColor("#ffffff"));
                llLogout.setBackgroundColor(Color.parseColor("#ffffff"));

                String current_user = auth.getCurrentUser().getEmail();
                Intent feedback_go = new Intent(category_class.this,feedback_Activity.class);
                feedback_go.putExtra("currentuser",current_user);
                startActivity(feedback_go);
            }
        });
        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlMain.closeDrawer(Gravity.START);

                llfeeback.setBackgroundColor(Color.parseColor("#ffffff"));
                llHome.setBackgroundColor(Color.parseColor("#ffffff"));
                llmanage_account.setBackgroundColor(Color.parseColor("#ffffff"));
                llposted_adds.setBackgroundColor(Color.parseColor("#ffffff"));
                llcontactus.setBackgroundColor(Color.parseColor("#ffffff"));
                llLogout.setBackgroundColor(Color.parseColor("#EAE6E5"));

                if(auth != null)
                {
                    auth.signOut();
                    Toast.makeText(category_class.this, "Successfully LogOut", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(category_class.this,sign_in.class));
                    finish();
                }
            }
        });

        floatingActionButton = findViewById(R.id.float_button);
        floatingActionButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(category_class.this, "POST ADD", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(category_class.this,upload_image.class);
                startActivity(intent1);
                //finish();
            }
        });
    }

    public void sendRegistrationToServer(String token,String name) {
        final String t =token;
        final String n = name;
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("send_to");
        Query query1 = FirebaseDatabase.getInstance().getReference("send_to").orderByChild("e_mail").equalTo(mail);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Log.d("a","a");
                }
                else{
                    token_data token_data = new token_data(FirebaseAuth.getInstance().getCurrentUser().getEmail().trim(),
                            t,n,FirebaseAuth.getInstance().getCurrentUser().getUid());
                    String key = reference.push().getKey();
                    reference.child(key).setValue(token_data);
                    /*reference.child(key)
                            .child("user_uid")
                            .setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    reference.child(key)
                            .child("messaging_token")
                            .setValue(t);
                    reference.child(key)
                            .child("name")
                            .setValue(n);
                    reference.child(key)
                            .child("e_mail")
                            .setValue(FirebaseAuth.getInstance().getCurrentUser().getEmail());*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void initFCM(){
    }

    @Override
    public void onItemClick(int position) {
        category_data clickedItem = category_data_list.get(position);
        String temp = null;
        temp = clickedItem.getTitle();
        if(temp.equals("Sports"))
        {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(EXTRA_CATEGORY,clickedItem.getTitle());
            startActivity(intent);
        }
        if(temp.equals("Electronics"))
        {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(EXTRA_CATEGORY,clickedItem.getTitle());
            startActivity(intent);
        }
        if(temp.equals("Fashion"))
        {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(EXTRA_CATEGORY,clickedItem.getTitle());
            startActivity(intent);
        }
        if(temp.equals("Mobile Accessories"))
        {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(EXTRA_CATEGORY,clickedItem.getTitle());
            startActivity(intent);
        }
        if(temp.equals("Beauty & Personal Care"))
        {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(EXTRA_CATEGORY,clickedItem.getTitle());
            startActivity(intent);
        }
        if(temp.equals("Books & Study Material"))
        {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(EXTRA_CATEGORY,clickedItem.getTitle());
            startActivity(intent);
        }
        if(temp.equals("Furniture"))
        {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(EXTRA_CATEGORY,clickedItem.getTitle());
            startActivity(intent);
        }
        if(temp.equals("Home decoration"))
        {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(EXTRA_CATEGORY,clickedItem.getTitle());
            startActivity(intent);
        }
        if(temp.equals("Other"))
        {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(EXTRA_CATEGORY,clickedItem.getTitle());
            startActivity(intent);
        }

        if(temp.equals("Home appliances"))
        {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra(EXTRA_CATEGORY,clickedItem.getTitle());
            startActivity(intent);
        }
    }
}
