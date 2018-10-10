package com.example.srushti.abode.AccountActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignOutActivity extends AppCompatActivity {
    private TextView sure,email;
    private Button but_out;
    private FirebaseAuth auth;
    ProgressBar abc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_out);
        sure=findViewById(R.id.textView);
        but_out=findViewById(R.id.button3);
        auth = FirebaseAuth.getInstance();
        email = (TextView) findViewById(R.id.useremail);
        abc=findViewById(R.id.progressBar);
        but_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignOut();
                startActivity(new Intent(SignOutActivity.this,LoginActivity.class));
                finish();
            }
        });

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email.setText("User Email: " + user.getEmail());


        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(SignOutActivity.this, com.example.srushti.abode.AccountActivity.LoginActivity.class));
                    finish();
                }
            }
        };


    }
    // this listener will be called when there is change in firebase user session
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(SignOutActivity.this, com.example.srushti.abode.AccountActivity.LoginActivity.class));
                finish();
            } else {
                email.setText("User Email: " + user.getEmail());

            }
        }


    };

    private void SignOut()
    {
        abc.setVisibility(View.VISIBLE);
        auth.signOut();
        // this listener will be called when there is change in firebase user session
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(SignOutActivity.this, com.example.srushti.abode.AccountActivity.LoginActivity.class));
                    finish();
                } else {
                    email.setText("User Email: " + user.getEmail());

                }
            }


        };
    }
}


