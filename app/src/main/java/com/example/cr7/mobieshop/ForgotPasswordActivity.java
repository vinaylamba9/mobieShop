package com.example.cr7.mobieshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText emailVeryfied;
    Button idSendResetLink;
    TextView idGoTOSignInPage;
    private FirebaseAuth firebaseAuthResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailVeryfied=findViewById(R.id.emailVeryfied);
        idSendResetLink=findViewById(R.id.idSendResetLink);
        idGoTOSignInPage=findViewById(R.id.idGoTOSignInPage);
        firebaseAuthResetPassword=FirebaseAuth.getInstance();




    }

    public void passwordReset(View view)
    {
        String userEmail=emailVeryfied.getText().toString().trim();
        if(userEmail.equals(""))
        {
            //Toast.makeText(ForgotPassword.this, "Enter your valid registered email", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Enter your valid registered email", Toast.LENGTH_SHORT).show();
        }else
        {
            firebaseAuthResetPassword.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful())
                    {
                        Toast.makeText(ForgotPasswordActivity.this, "Password Reset Link Send to your Email id", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(),sign_in.class));
                    }
                    else
                    {
                        Toast.makeText(ForgotPasswordActivity.this, "Error in Sending reset link ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        emailVeryfied.setText("");

    }

    public void goOnLogInPage(View v1)
    {
        finish();
        startActivity(new Intent(getApplicationContext(),sign_in.class));
    }
}

