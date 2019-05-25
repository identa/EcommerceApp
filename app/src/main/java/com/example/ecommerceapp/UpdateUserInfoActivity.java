package com.example.ecommerceapp;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class UpdateUserInfoActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private FrameLayout frameLayout;
    private UpdateInfoFragment updateInfoFragment;
    private UpdatePasswordFragment updatePasswordFragment;
    private String firstName, lastName, photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);

        tabLayout = findViewById(R.id.tab_layout);
        frameLayout = findViewById(R.id.frame_layout);

        updateInfoFragment = new UpdateInfoFragment();
        updatePasswordFragment = new UpdatePasswordFragment();

        firstName = getIntent().getStringExtra("firstName");
        lastName = getIntent().getStringExtra("lastName");
        photo = getIntent().getStringExtra("photo");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    setFragment(updateInfoFragment, true);
                } else if (tab.getPosition() == 1){
                    setFragment(updatePasswordFragment, false);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.getTabAt(0).select();
        setFragment(updateInfoFragment, true);
    }

    private void setFragment(Fragment fragment, boolean setBundle){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (setBundle) {
            Bundle bundle = new Bundle();
            bundle.putString("firstName", firstName);
            bundle.putString("lastName", lastName);
            bundle.putString("photo", photo);
            fragment.setArguments(bundle);
        }
        transaction.replace(frameLayout.getId(), fragment);
        transaction.commit();
    }
}
