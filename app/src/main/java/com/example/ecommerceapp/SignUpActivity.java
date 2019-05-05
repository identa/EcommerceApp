package com.example.ecommerceapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

public class SignUpActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    public static boolean onResetPassword = false;
    public static boolean setSignUpFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        frameLayout = findViewById(R.id.sign_up_frame_layout);

        if (setSignUpFragment) {
            setSignUpFragment = false;
            setDefaultFragment(new SignUpFragment());
        } else {
            setDefaultFragment(new SignInFragment());
        }
//        setFragment(new SignInFragment());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (onResetPassword) {
                onResetPassword = false;
                setFragment(new SignInFragment());
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setDefaultFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(frameLayout.getId(), fragment);
        transaction.commit();
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_left, R.anim.slideout_right);
        transaction.replace(frameLayout.getId(), fragment);
        transaction.commit();
    }
}
