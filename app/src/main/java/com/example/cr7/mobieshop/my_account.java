package com.example.cr7.mobieshop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.cr7.mobieshop.detailactivity.EXTRA_USER_IDD;

public class my_account extends AppCompatActivity {

    upload_userdata upload;
    TextView user_name,user_address,user_email,user_date,user_reg_no,user_phoneno;
    CircleImageView user_picture;
    ImageButton edit_activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

        user_name = findViewById(R.id.name_profile);
        user_address = findViewById(R.id.address_profile);
        user_email = findViewById(R.id.user_email);
        user_date = findViewById(R.id.user_date);
        user_reg_no = findViewById(R.id.user_regno);
        user_picture = findViewById(R.id.user_picture);
        user_phoneno = findViewById(R.id.account_phoneno);
        edit_activity = findViewById(R.id.edit_activity);

        Intent ia = getIntent();
        final String email = ia.getStringExtra("currentuser");

        edit_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editgo = new Intent(my_account.this,edit_my_account.class);
                editgo.putExtra("emailkey",email);
                startActivity(editgo);
                finish();
            }
        });
        Query query = FirebaseDatabase.getInstance().getReference("account_data").orderByChild("e_mail").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        upload = new upload_userdata();
                        upload.setUser_name(ds.getValue(upload_userdata.class).getUser_name());
                        upload.setE_mail(ds.getValue(upload_userdata.class).getE_mail());
                        upload.setReg_number(ds.getValue(upload_userdata.class).getReg_number());
                        upload.setAccount_date(ds.getValue(upload_userdata.class).getAccount_date());
                        upload.setProfile_pic_url(ds.getValue(upload_userdata.class).getProfile_pic_url());
                        upload.setAddress(ds.getValue(upload_userdata.class).getAddress());
                        upload.setPhone_No(ds.getValue(upload_userdata.class).getPhone_No());
                    }
                }
                user_name.setText(upload.getUser_name());
                user_date.setText(upload.getAccount_date());
                user_address.setText(upload.getAddress());
                user_email.setText(upload.getE_mail());
                user_reg_no.setText(upload.getReg_number());
                user_phoneno.setText(upload.getPhone_No());
                Picasso.get().load(upload.getProfile_pic_url()).into(user_picture);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(my_account.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}
