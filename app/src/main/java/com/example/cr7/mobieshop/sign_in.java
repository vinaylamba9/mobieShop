package com.example.cr7.mobieshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class sign_in extends AppCompatActivity {

    EditText EdtEmail, EdtPassword;
    Button logInButton;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog1;
    TextView idgoToSignUpPage,idForgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        EdtEmail = findViewById(R.id.emailLogIn);
        EdtPassword = findViewById(R.id.PasswordLogIn);

        logInButton = findViewById(R.id.idLogIn);
        firebaseAuth = FirebaseAuth.getInstance();
        idgoToSignUpPage=findViewById(R.id.idNotRegister);
        idForgotPassword=findViewById(R.id.idforgotPassword);
        progressDialog1 = new ProgressDialog(this);

        if(firebaseAuth.getCurrentUser()!=null)
        {
            Intent i1=new Intent(getApplicationContext(),category_class.class);
            startActivity(i1);
            finish();
        }

        idgoToSignUpPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(sign_in.this,register.class));
                finish();
            }
        });

        idForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
                finish();
            }
        });

    }

    public void logInFunction(View v)
    {
        String email_1=EdtEmail.getText().toString().trim();
        String pass_1=EdtPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email_1))
        {
            EdtEmail.setError("Email Required");
            EdtEmail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(pass_1))
        {
            EdtPassword.setError("Password Required");
            EdtPassword.requestFocus();
            return;
        }
        if(pass_1.length()<8)
        {
            EdtPassword.setError("min 8 characters required");
            EdtPassword.requestFocus();
            return;
        }
        progressDialog1.setTitle("Logging In....");
        progressDialog1.show();


        /*firebaseAuth.signInWithEmailAndPassword(email_1,pass_1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog1.dismiss();
                if(task.isSuccessful())
                {
                    Toast.makeText(SignIn.this, "Sucessful Log-in", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SignIn.this, "Login Falied", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        firebaseAuth.signInWithEmailAndPassword(email_1, pass_1)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog1.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Toast.makeText(sign_in.this, "Successful LogIn", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),category_class.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                            //      Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                            Toast.makeText(sign_in.this, "Email or Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
        EdtEmail.setText("");
        EdtPassword.setText("");
    }
}