package com.example.ecommerceapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class SignUpActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    public static boolean setSignUpFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        frameLayout = findViewById(R.id.sign_up_frame_layout);

        if (setSignUpFragment){
            setSignUpFragment = false;
            setFragment(new SignUpFragment());
        } else{
            setFragment(new SignInFragment());
        }
//        setFragment(new SignInFragment());
    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayout.getId(), fragment);
        transaction.commit();
    }
}
