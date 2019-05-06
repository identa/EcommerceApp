package com.example.ecommerceapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import static com.example.ecommerceapp.SignUpActivity.setSignUpFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 2;
    private static final int ORDER_FRAGMENT = 1;
    private static final int WISHLIST_FRAGMENT = 3;
    private static final int ACCOUNT_FRAGMENT = 4;
    public static Boolean showCart = false;

    private int currentFragment = -1;
    private NavigationView navigationView;

    private FrameLayout frameLayout;
    private Window window;
    private Toolbar toolbar;

    private MaterialSearchView searchView;

    private FirebaseUser currentUser;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        sharedPreferences = getSharedPreferences("signin_info", MODE_PRIVATE);

        searchView = findViewById(R.id.search_view);
        searchView.setCursorDrawable(R.drawable.search_view_custom_cursor);
        searchView.setHint("What do you want to search ?");
        searchView.setHintTextColor(R.color.recyclerViewBackground);
        searchView.setVoiceSearch(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        ImageView homeProfileImage = navigationView.getHeaderView(0).findViewById(R.id.home_profile_image);
        Glide.with(this).load(sharedPreferences.getString("imageURL", "a")).apply(new RequestOptions().placeholder(R.mipmap.steakhouse)).into(homeProfileImage);

        frameLayout = findViewById(R.id.home_frame_layout);
//        noInternet = findViewById(R.id.no_internet);

        if (showCart) {
            drawer.setDrawerLockMode(1);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            gotoFragment("My cart", new MyCartFragment(), -2);
        } else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            if (getIntent().getBooleanExtra("showAccount", false)) {
                navigationView.getMenu().getItem(ACCOUNT_FRAGMENT).setChecked(true);
                gotoFragment("My account", new MyAccountFragment(), ACCOUNT_FRAGMENT);
            } else setFragment(new HomeFragment(), HOME_FRAGMENT);
        }

//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//
//        if (networkInfo != null && networkInfo.isConnected()){
//            noInternet.setVisibility(View.GONE);
////        if (showCart) {
////            drawer.setDrawerLockMode(1);
////            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////            gotoFragment("My cart", new MyCartFragment(), -2);
////        } else {
////            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
////                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
////            drawer.addDrawerListener(toggle);
////            toggle.syncState();
////            setFragment(new HomeFragment(), HOME_FRAGMENT);
////        }
//    } else {
//            Glide.with(this).load(R.mipmap.no_internet).into(noInternet);
//            noInternet.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(false);
        } else {
            navigationView.getMenu().getItem(navigationView.getMenu().size() - 1).setEnabled(true);
        }
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment == HOME_FRAGMENT) {
                currentFragment = -1;
                super.onBackPressed();
            } else {
                if (showCart) {
                    showCart = false;
                    finish();
                } else {
                    invalidateOptionsMenu();
                    setFragment(new HomeFragment(), HOME_FRAGMENT);
                    navigationView.getMenu().getItem(0).setChecked(true);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (currentFragment == HOME_FRAGMENT) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.home, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home_search_icon) {
            searchView.setMenuItem(item);
            searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Intent viewAllIntent = new Intent(HomeActivity.this, ViewAllActivity.class);
                    viewAllIntent.putExtra("layout_code", 2);
                    viewAllIntent.putExtra("search_query", query);
                    viewAllIntent.putExtra("title", "Search results");
                    startActivity(viewAllIntent);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
            return true;
        } else if (id == R.id.home_notification_icon) {
            return true;
        } else if (id == R.id.home_cart_icon) {
            final Dialog signInDialog = new Dialog(HomeActivity.this);
            signInDialog.setContentView(R.layout.sign_in_dialog);
            signInDialog.setCancelable(true);
            signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            Button dialogSignInBtn = signInDialog.findViewById(R.id.cancel_btn);
            Button dialogSignUpBtn = signInDialog.findViewById(R.id.ok_btn);
            final Intent signUpIntent = new Intent(HomeActivity.this, SignUpActivity.class);

            dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signInDialog.dismiss();
                    setSignUpFragment = false;
                    startActivity(signUpIntent);
                }
            });

            dialogSignUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signInDialog.dismiss();
                    setSignUpFragment = true;
                    startActivity(signUpIntent);
                }
            });
            signInDialog.show();
//            gotoFragment("My cart", new MyCartFragment(), CART_FRAGMENT);
            return true;
        } else if (id == android.R.id.home) {
            if (showCart) {
                showCart = false;
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void gotoFragment(String title, Fragment fragment, int fragmentNo) {
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment, fragmentNo);
//        if (fragmentNo == CART_FRAGMENT){
//            navigationView.getMenu().getItem(2).setChecked(true);
//        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
            invalidateOptionsMenu();
            setFragment(new HomeFragment(), HOME_FRAGMENT);
        } else if (id == R.id.nav_orders) {
            gotoFragment("My orders", new MyOrderFragment(), ORDER_FRAGMENT);
        } else if (id == R.id.nav_cart) {
            gotoFragment("My cart", new MyCartFragment(), CART_FRAGMENT);
        } else if (id == R.id.nav_wishlist) {
            gotoFragment("My wishlist", new MyWishlistFragment(), WISHLIST_FRAGMENT);
        } else if (id == R.id.nav_account) {
            gotoFragment("My account", new MyAccountFragment(), ACCOUNT_FRAGMENT);
        } else if (id == R.id.nav_sign_out) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear().apply();
            Intent signInIntent = new Intent(this, SignUpActivity.class);
            startActivity(signInIntent);
            Toast.makeText(getApplicationContext(), "Sign out successfully", Toast.LENGTH_LONG).show();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setFragment(Fragment fragment, int fragmentNo) {
        if (fragmentNo != currentFragment) {
            currentFragment = fragmentNo;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            transaction.replace(frameLayout.getId(), fragment);
            transaction.commit();
        }
    }
}
