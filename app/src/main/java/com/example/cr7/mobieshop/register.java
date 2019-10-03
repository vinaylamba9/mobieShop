package com.example.cr7.mobieshop;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.util.Calendar;

public class register extends AppCompatActivity{

    byte[] image_data = null;
    EditText userName,Password,ConfirmPassword,email,phoneNo,registrationNumber,address;
    RelativeLayout relativeLayoutRegistrationNumber;
    CheckBox checkboxForLovely;
    Button idSignUp;
    TextView idAllreadyRegister;
    FirebaseAuth auth;
    private ProgressDialog progressDialog;
    Button select_photo;


    private StorageTask mUploadTask;
    public File file;

    Uri imageuri;
    Bitmap bm;
    ImageView img;
    int PROFILE_PHOTO_PICKER_REQUEST = 7;
    StorageReference storageref;
    DatabaseReference databaseref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        auth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        userName=findViewById(R.id.userName);
        Password=findViewById(R.id.Password);
        ConfirmPassword=findViewById(R.id.ConfirmPassword);
        email=findViewById(R.id.email);
        phoneNo=findViewById(R.id.phoneNo);
        registrationNumber=findViewById(R.id.registrationNumber);
        select_photo = findViewById(R.id.id_profile_pic);
        img = findViewById(R.id.img_profile_pic);
        address = findViewById(R.id.Address);

        idSignUp=findViewById(R.id.idSignUp);
        idAllreadyRegister=findViewById(R.id.idAllreadyRegister);
        checkboxForLovely=findViewById(R.id.checkboxForLovely);
        relativeLayoutRegistrationNumber=findViewById(R.id.relativeLayoutRegistrationNumber);

        select_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                intent2.setType("image/*");
                intent2.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent2, "Select Picture"),PROFILE_PHOTO_PICKER_REQUEST);
            }
        });



        idSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRegister();
            }
        });

        idAllreadyRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogIn();
            }
        });

        checkboxForLovely.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true)
                    idSignUp.setEnabled(true);
                else
                idSignUp.setEnabled(false);
            }
        });


      //   idSignUp.setOnClickListener(this);
        // idAllreadyRegister.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PROFILE_PHOTO_PICKER_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imageuri = data.getData();

            try {
                bm = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageuri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            GlideApp.with(this)
                    .load(bm)
                    .into(img);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.WEBP, 20, baos);
            image_data = baos.toByteArray();
        }
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void onRegister()
    {
        String user=userName.getText().toString().trim();
        String conPassstr=ConfirmPassword.getText().toString().trim();
        String phonstr=phoneNo.getText().toString().trim();
        String addressStr = address.getText().toString().trim();
        final String registrationstr=registrationNumber.getText().toString().trim();
        final String EmailAdd=email.getText().toString().trim();
        final String passCode=Password.getText().toString().trim();
        if(TextUtils.isEmpty(EmailAdd))
        {
            email.setError("Email Required");
            email.requestFocus();
            // Toast.makeText(this, "Please Enter the Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(passCode))
        {
            Password.setError("Password Required");
            Password.requestFocus();
            //Toast.makeText(this, "Please Enter the Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if(passCode.length()<8)
        {
            Password.setError("Minimum 8 characters required");
            Password.requestFocus();
            return;
        }
        if(!(passCode.equals(conPassstr))) {
            ConfirmPassword.setError("Both password should be equal");
            ConfirmPassword.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(user))
        {
            userName.setError("Enter the user Name");
            Toast.makeText(this, "Please Enter the User Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(addressStr))
        {
            address.setError("Specify your university living location");
            Toast.makeText(this, "Please Enter Your Accommodation detail.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(phonstr))
        {
            phoneNo.setError("Enter the phone number");
            Toast.makeText(this, "Please Enter your Phone Number", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(registrationstr))
        {
            registrationNumber.setError("Enter your registration number.");
            Toast.makeText(this, "Please Enter the your Registration Number", Toast.LENGTH_SHORT).show();
            return;
        }

        uploadFile();

                auth.createUserWithEmailAndPassword(EmailAdd,passCode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            progressDialog.dismiss();
                            Toast.makeText(register.this,"User creation success",Toast.LENGTH_SHORT).show();
                            Intent abc = new Intent(register.this,category_class.class);
                            startActivity(abc);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(register.this,"not success" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void goToLogIn()
    {
        finish();
        Intent intent=new Intent(getApplicationContext(),sign_in.class);
        startActivity(intent);
    }

    private void uploadFile()
    {
        storageref = FirebaseStorage.getInstance().getReference();
        databaseref = FirebaseDatabase.getInstance().getReference("account_data");
        auth = FirebaseAuth.getInstance();
        Calendar calendar = Calendar.getInstance();
        final String post_time = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());

        if(imageuri!=null) {

            final StorageReference fileReference = storageref.child("uploads_data/" + System.currentTimeMillis() + "." + getFileExtension(imageuri));
            mUploadTask = fileReference.putBytes(image_data);

            mUploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getMetadata();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                        }
                    },1000);
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                         }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


            Task<Uri> uriTask = mUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String downloadurl = downloadUri.toString();
                        String uploadId = databaseref.push().getKey();
                        upload_userdata upload_userdata = new upload_userdata(
                          userName.getText().toString().trim(),
                          Password.getText().toString().trim(),
                                email.getText().toString().trim(),
                                phoneNo.getText().toString().trim(),
                                registrationNumber.getText().toString().trim(),
                                downloadurl.trim(),
                                post_time,
                                address.getText().toString().trim(),uploadId
                        );

                        databaseref.child(uploadId).setValue(upload_userdata);
                    } else {
                        Toast.makeText(register.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }
}
