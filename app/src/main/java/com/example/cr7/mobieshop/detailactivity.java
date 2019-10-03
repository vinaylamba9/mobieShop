package com.example.cr7.mobieshop;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import static com.example.cr7.mobieshop.MainActivity.EXTRA_BRANDNAME;
import static com.example.cr7.mobieshop.MainActivity.EXTRA_DESC;
import static com.example.cr7.mobieshop.MainActivity.EXTRA_POSTTIME;
import static com.example.cr7.mobieshop.MainActivity.EXTRA_PRICE;
import static com.example.cr7.mobieshop.MainActivity.EXTRA_REALPRICE;
import static com.example.cr7.mobieshop.MainActivity.EXTRA_TITLE;
import static com.example.cr7.mobieshop.MainActivity.EXTRA_URL;
import static com.example.cr7.mobieshop.MainActivity.EXTRA_USER_ID;

public class detailactivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    customDialog_buy customDialog;
    public static final String EXTRA_USER_IDD = "u_id";
    String user_id;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detailedactivity);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        final ImageView img = findViewById(R.id.image_item);
        TextView title_text = findViewById(R.id.title_item);
        TextView price_text = findViewById(R.id.price_item);
        TextView brand_text = findViewById(R.id.brand_item);
        TextView desc_text = findViewById(R.id.description_item);
        TextView real_price_text = findViewById(R.id.original_price);
        TextView posted_time_text = findViewById(R.id.posted_time_item);
        Button seller_profile = findViewById(R.id.view_seller_profile);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String title = intent.getStringExtra(EXTRA_TITLE);
        String brandname = intent.getStringExtra(EXTRA_BRANDNAME);
        String description = intent.getStringExtra(EXTRA_DESC);
        int real_price = intent.getIntExtra(EXTRA_REALPRICE,100);
        int price = intent.getIntExtra(EXTRA_PRICE,100);
        String posted_time = intent.getStringExtra(EXTRA_POSTTIME);
        user_id = intent.getStringExtra(EXTRA_USER_ID);

        Glide.with(this).load(imageUrl).into(img);
        title_text.setText(title);
        brand_text.setText(brandname);
        desc_text.setText(description);
        real_price_text.setText(real_price+"");
        price_text.setText(price + "");
        posted_time_text.setText(posted_time);

        seller_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_seller_profile_activity = new Intent(detailactivity.this,profile_class.class);
                intent_seller_profile_activity.putExtra(EXTRA_USER_IDD,user_id);
                startActivity(intent_seller_profile_activity);
            }
        });

        bottomNavMethod();
    }
    public void bottomNavMethod()
    {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //return false;
                switch (menuItem.getItemId())
                {
                    case R.id.buyItem:
                        Intent buy = new Intent(getApplicationContext(),buy_product.class);
                        buy.putExtra("useremailid",user_id);
                        startActivity(buy);
                        break;
                    case  R.id.chatItem:
                        //Toast.makeText(MainActivity.this, "Chat Item", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(getApplicationContext(),ChatActivity.class);
                        i.putExtra(EXTRA_USER_IDD,user_id);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
    }
}
