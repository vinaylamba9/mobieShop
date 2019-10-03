package com.example.cr7.mobieshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.cr7.mobieshop.detailactivity.EXTRA_USER_IDD;

public class edit_my_account extends AppCompatActivity {

    upload_userdata upload;
    EditText user_name,user_address,user_email,user_phoneno;
    Button savedata;
    String email;
    FirebaseAuth auth;
    String username,pass_word,e_mail,phone_No,reg_number,profile_pic_url,address,datee,id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_my_account);

        auth = FirebaseAuth.getInstance();
        user_name = findViewById(R.id.edit_name);
        user_address = findViewById(R.id.edit_address);
        user_email = findViewById(R.id.edit_email);
        user_phoneno = findViewById(R.id.edit_phoneno);
        savedata = findViewById(R.id.edited_data_submit);

        Intent ia = getIntent();
        email = ia.getStringExtra("emailkey");

        savedata.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                saveEditedData();
                return false;
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
                        upload.setPhone_No(ds.getValue(upload_userdata.class).getPhone_No());
                        upload.setAddress(ds.getValue(upload_userdata.class).getAddress());
                    }
                }
                user_name.setText(upload.getUser_name());
                user_address.setText(upload.getAddress());
                user_email.setText(upload.getE_mail());
                user_phoneno.setText(upload.getPhone_No());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(edit_my_account.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveEditedData(){
        Query query1 = FirebaseDatabase.getInstance().getReference("account_data").orderByChild("e_mail").equalTo(email);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        upload = new upload_userdata();
                        upload.setUser_name(ds.getValue(upload_userdata.class).getUser_name());
                        upload.setPass_word(ds.getValue(upload_userdata.class).getPass_word());
                        upload.setProfile_pic_url(ds.getValue(upload_userdata.class).getProfile_pic_url());
                        upload.setAccount_date(ds.getValue(upload_userdata.class).getAccount_date());
                        upload.setReg_number(ds.getValue(upload_userdata.class).getReg_number());
                        upload.setE_mail(ds.getValue(upload_userdata.class).getE_mail());
                        upload.setPhone_No(ds.getValue(upload_userdata.class).getPhone_No());
                        upload.setAddress(ds.getValue(upload_userdata.class).getAddress());
                        upload.setUploadid(ds.getValue(upload_userdata.class).getUploadid());
                    }
                }
                username = user_name.getText().toString().trim();
                pass_word = upload.getPass_word();
                e_mail = user_email.getText().toString().trim();
                reg_number = upload.getReg_number();
                phone_No = user_phoneno.getText().toString().trim();
                profile_pic_url = upload.getProfile_pic_url();
                address = user_address.getText().toString().trim();
                datee = upload.getAccount_date();
                id = upload.getUploadid();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(edit_my_account.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        if(!(username==null))
        {
            upload_userdata upload_userdata = new upload_userdata(username,pass_word,e_mail,phone_No,reg_number,profile_pic_url,datee,address,id);
            DatabaseReference dataref  = FirebaseDatabase.getInstance().getReference("account_data").child(id);
            dataref.setValue(upload_userdata);
            Toast.makeText(this, "Successfully Saved.", Toast.LENGTH_SHORT).show();
            Intent back = new Intent(edit_my_account.this,category_class.class);
            startActivity(back);
            finish();
        }
    }
}
