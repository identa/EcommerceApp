package com.example.ecommerceapp;

import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SystemClock.sleep(3000);
        Intent signInIntent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(signInIntent);
        finish();
    }
}
