package com.example.cr7.mobieshop;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


    public class customDialog_buy extends Dialog {

        public Context c;
        public Dialog d;
        Button submitResponce;

        public customDialog_buy(@NonNull Context context) {
            super(context);
            this.c = context;
        }


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_custom_dialog);

            submitResponce = findViewById(R.id.submitResponce);
            submitResponce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(c, "Send Confirmation Message", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            });
        }
    }
