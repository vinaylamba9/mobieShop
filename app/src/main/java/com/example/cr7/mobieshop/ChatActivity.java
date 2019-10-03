package com.example.cr7.mobieshop;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.example.cr7.mobieshop.detailactivity.EXTRA_USER_IDD;

public class ChatActivity extends AppCompatActivity {
    EditText edtPhoneNo,edtMessage;
    Button send;
    private static final int requestCode1=0;
    upload_userdata upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        edtPhoneNo=findViewById(R.id.idPhoneforChat);
        edtMessage=findViewById(R.id.my_Message);
        send=findViewById(R.id.send);

        Intent ia = getIntent();
        String email = ia.getStringExtra(EXTRA_USER_IDD);
        Query query = FirebaseDatabase.getInstance().getReference("account_data").orderByChild("e_mail").equalTo(email);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for(DataSnapshot ds : dataSnapshot.getChildren())
                    {
                        upload = new upload_userdata();
                        upload.setPhone_No(ds.getValue(upload_userdata.class).getPhone_No());
                    }
                }
                edtPhoneNo.setText(upload.getPhone_No());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendSms(View view) {
        int permissionCheck= ContextCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS);
        if(permissionCheck== PackageManager.PERMISSION_GRANTED)
        {
            myMessage();
        }else
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},requestCode1);
        }
    }

    void myMessage()
    {
        String textNumber=edtPhoneNo.getText().toString().trim();
        String textMessage=edtMessage.getText().toString().trim();

        if(textNumber==null||textNumber.equals("")||textMessage==null||textMessage.equals(""))
        {
            Toast.makeText(this, "Field can't be empty ", Toast.LENGTH_SHORT).show();
        }else
        {
            if(TextUtils.isDigitsOnly(textNumber)){
                SmsManager smsManager=SmsManager.getDefault();
                smsManager.sendTextMessage(textNumber,null,textMessage,null,null);
                Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show();
            }else
            {
                Toast.makeText(this, "Please Enter Integer Only", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case requestCode1:
                if(grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    myMessage();
                }else
                {
                    Toast.makeText(this, "You do not have required Permission", Toast.LENGTH_SHORT).show();
                }
        }
    }
}