package com.example.cr7.mobieshop;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.PathUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.request.RequestOptions;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class upload_image extends AppCompatActivity
{

    private byte[] image_data = null;
    private StorageTask mUploadTask;
    private NumberProgressBar progressBar;
    private EditText title;
    private EditText price;
    private EditText description;
    private Button muploadbutton;
    private Uri mImageUri;
    public Bitmap b = null;
    public File file;
    FirebaseAuth firebaseAuth;
    public Spinner spinner;
    public ArrayAdapter<String> spinnerAdapter;
    public ImageView imageView;
    private static final int PHOTO_PICKER_REQUEST =  2;

    public EditText mBrandName;
    public EditText mDuration;
    public EditText mOriginalPrice;
    String item;

    private StorageReference mStorageref;
    DatabaseReference mDatabaseref;
    DatabaseReference dataref;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        imageView = findViewById(R.id.image_upload);
        title = findViewById(R.id.edittext_itemname);
        price = findViewById(R.id.edittext_price);
        description = findViewById(R.id.edittext_desc);
        muploadbutton = findViewById(R.id.button_upload);
        progressBar = findViewById(R.id.progress_bar);
        mBrandName = findViewById(R.id.edittext_brand_name);
        mDuration = findViewById(R.id.edittext_duration);
        mOriginalPrice = findViewById(R.id.edittext_originalprice);

        spinner = findViewById(R.id.spinner_category);
        List<String> categories = new ArrayList<>();
        categories.add(0,"Choose Category of your Item.");
        categories.add("Electronics");
        categories.add("Mobile Accessories");
        categories.add("Books & Study Material");
        categories.add("Sports");
        categories.add("Beauty & Personal Care");
        categories.add("Fashion");
        categories.add("Home appliances");
        categories.add("Furniture");
        categories.add("Home decoration");
        categories.add("Other");

        spinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Choose Category of your Item.")){
                    //do nothing
                }
                else{
                    item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(upload_image.this, "Category Selected: "+item, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        /*else if(item.equals("Electronics"))
        {
            mDatabaseref = FirebaseDatabase.getInstance().getReference("Electronics");
        }
        else if(item.equals("Sports"))
        {
            mDatabaseref = FirebaseDatabase.getInstance().getReference("Sports");

        }
        else if(item.equals("Mobile Accessories"))
        {
            mDatabaseref = FirebaseDatabase.getInstance().getReference("Mobile Accessories");
        }*/

        Intent intent2 = new Intent();
        intent2.setType("image/*");
        intent2.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent2, "Select Picture"),PHOTO_PICKER_REQUEST);

        muploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(upload_image.this, "Upload in Progress.", Toast.LENGTH_SHORT).show();
                } else {
                    if(!(title.getText().toString().length() > 0))
                    {
                        title.setError("Mention the Name of the product to sell.");
                        return;
                    }
                    if(!(mOriginalPrice.getText().toString().length() > 0))
                    {
                        mOriginalPrice.setError("Mention the Original Price at which you bought the item.");
                        return;
                    }
                    if(!(mDuration.getText().toString().length() > 0))
                    {
                        mDuration.setError("Mention the time duration you used the product.");
                        return;
                    }
                    int op,sp;
                    op = Integer.parseInt(mOriginalPrice.getText().toString());
                    sp = Integer.parseInt(price.getText().toString());
                    if( sp < op*(0.15) || price.getText().toString().length() <= 0)
                    {
                        price.setError("Too Low Price to Enter.");
                        return;
                    }
                    muploadbutton.setText("Posting....");
                    uploadFile();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, " Please Wait....", Toast.LENGTH_SHORT).show();
        if(requestCode == PHOTO_PICKER_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            mImageUri = data.getData();

            try {
                b = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            GlideApp.with(this)
                    .load(b)
                    .into(imageView);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.WEBP, 20, baos);
            image_data = baos.toByteArray();
        }
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadFile()
    {
        mStorageref = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        dataref = FirebaseDatabase.getInstance().getReference("my_adds_posted");
        final String email_id = firebaseAuth.getCurrentUser().getEmail();
        Calendar calendar = Calendar.getInstance();
        final String post_time = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
        if(item.equals("Fashion"))
        {
            mDatabaseref = FirebaseDatabase.getInstance().getReference("Fashion");
        }
        if(item.equals("Electronics"))
        {
            mDatabaseref = FirebaseDatabase.getInstance().getReference("Electronics");
        }
        if(item.equals("Mobile Accessories"))
        {
            mDatabaseref = FirebaseDatabase.getInstance().getReference("Mobile Accessories");
        }
        if(item.equals("Books & Study Material"))
        {
            mDatabaseref = FirebaseDatabase.getInstance().getReference("Books & Study Material");
        }
        if(item.equals("Home appliances"))
        {
            mDatabaseref = FirebaseDatabase.getInstance().getReference("Home appliances");
        }
        if(item.equals("Furniture"))
        {
            mDatabaseref = FirebaseDatabase.getInstance().getReference("Furniture");
        }
        if(item.equals("Beauty & Personal Care"))
        {
            mDatabaseref = FirebaseDatabase.getInstance().getReference("Beauty & Personal Care");
        }
        if(item.equals("Sports"))
        {
            mDatabaseref = FirebaseDatabase.getInstance().getReference("Sports");
        }
        if(item.equals("Home decoration"))
        {
            mDatabaseref = FirebaseDatabase.getInstance().getReference("Home decoration");
        }
        if(item.equals("Other"))
        {
            mDatabaseref = FirebaseDatabase.getInstance().getReference("Other");
        }

        if(mImageUri!=null) {
            final StorageReference fileReference = mStorageref.child("uploads_data/" + System.currentTimeMillis() + "." + getFileExtension(mImageUri));
            mUploadTask = fileReference.putBytes(image_data);

            mUploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getMetadata();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    },1000);
                    Toast.makeText(upload_image.this, "Upload Successful.", Toast.LENGTH_SHORT).show();
                    muploadbutton.setText("ADD Posted.");
                    Intent back = new Intent(upload_image.this,category_class.class);
                    startActivity(back);
                    finish();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressBar.setProgress((int)progress);
                    System.out.println("Upload is " + progress + "% done");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(upload_image.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        //for addds posted in database.
                        String uploadId = mDatabaseref.push().getKey();
                        upload_data upload_data = new upload_data(title.getText().toString().trim(),
                                downloadurl.trim(),
                                Integer.parseInt(price.getText().toString().trim()),
                                description.getText().toString().trim(),
                                mBrandName.getText().toString().trim(),
                                Integer.parseInt(mOriginalPrice.getText().toString().trim()),
                                mDuration.getText().toString().trim(),
                                item,
                                email_id,
                                post_time,
                                uploadId
                                );
                        mDatabaseref.child(uploadId).setValue(upload_data);

                        //for my adds
                        String upload_id = dataref.push().getKey();
                        my_add_data my_add_data = new my_add_data(
                                downloadurl.trim(),
                                title.getText().toString().trim(),
                                price.getText().toString().trim(),
                                post_time,
                                email_id,
                                upload_id,
                                item,
                                uploadId
                                );
                        dataref.child(upload_id).setValue(my_add_data);
                    } else {
                        Toast.makeText(upload_image.this, "Failed to Upload", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(upload_image.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }
    }
}
