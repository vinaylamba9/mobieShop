package com.example.cr7.mobieshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class buy_product extends AppCompatActivity {

    String send_to="";
    String fcm_token;
    Pref pref;
    EditText etofferedprice,etmessage;
    token_data tokendata;
    Button buttonsendnotification;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_product);

        etmessage = findViewById(R.id.etmessage);
        etofferedprice = findViewById(R.id.etOfferedPrice);
        buttonsendnotification = findViewById(R.id.button_sendnotification);


        pref = new Pref(this);
        Intent getintent = getIntent();
        send_to = getintent.getStringExtra("useremailid");

        Query getProfiledetails = FirebaseDatabase.getInstance().getReference("send_to").orderByChild("e_mail").equalTo(send_to);
        getProfiledetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        tokendata = new token_data();
                        tokendata.setMessaging_token(ds.getValue(token_data.class).getMessaging_token());
                        fcm_token = tokendata.getMessaging_token();
                    }
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonsendnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etmessage.getText().toString().length()>0)
                {
                    if(etofferedprice.getText().toString().length()>0)
                    {
                        if(!send_to.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail()))
                        {
                            new SendNotification().sendNotification(fcm_token,etmessage.getText().toString().trim(),etofferedprice.getText().toString().trim());
                            insertNotificationTable(etmessage.getText().toString().trim(),etofferedprice.getText().toString().trim(),send_to);
                        }
                        else {
                            Toast.makeText(buy_product.this, "You can't buy your own product.", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        etofferedprice.setError("Enter Price to negotiate.");
                        etofferedprice.requestFocus();
                    }
                }else{
                    etmessage.setError("Enter about Product you want to buy.");
                    etmessage.requestFocus();
                }
            }
        });
    }

    private void insertNotificationTable(String title, String body, String sendTo) {
        NotificationModel notificationModel = new NotificationModel();
        notificationModel.setTitle(title);
        notificationModel.setBody(body);
        notificationModel.setSend_to(sendTo);
        notificationModel.setSender(FirebaseAuth.getInstance().getCurrentUser().getEmail().trim());
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("notification");
        mDatabase.push().setValue(notificationModel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {


                } else {
                    Toast.makeText(getApplicationContext(),"Successfully Request Raise",Toast.LENGTH_SHORT).show();
                    onBackPressed();

                }
            }
        });

    }


}
