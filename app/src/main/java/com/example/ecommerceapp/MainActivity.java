package com.example.ecommerceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("signin_info", MODE_PRIVATE);

        SystemClock.sleep(3000);
//        Intent signInIntent = new Intent(MainActivity.this, SignUpActivity.class);
//        startActivity(signInIntent);
//        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

//        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (sharedPreferences.getInt("id", -1) == -1) {
            Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(signUpIntent);
            finish();
        } else {
            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        }
    }
}
