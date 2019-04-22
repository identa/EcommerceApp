package com.example.ecommerceapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ecommerceapp.adapters.ProductDetailsAdapter;
import com.example.ecommerceapp.adapters.ProductImageAdapter;
import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.responses.ProductDetailData;
import com.example.ecommerceapp.models.entities.responses.ProductDetailResponse;
import com.example.ecommerceapp.models.entities.responses.ProductImageData;
import com.example.ecommerceapp.models.interfaces.ProductDetailAPI;
import com.example.ecommerceapp.models.services.ProductDetailService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.ecommerceapp.HomeActivity.showCart;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetailService {

    private ViewPager productImageViewPager;
    private TabLayout viewPagerIndicator;
    private TextView productTitle;
    private TextView avgRatingMiniView;
    private TextView totalRatingMiniView;
    private TextView productPrice;
    private TextView cuttedPrice;

    //product desc
    private ConstraintLayout productDetailsTabsContainer;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;
    private String productDescription;
    private String productOtherDetails;
//    public static int tabsPosition = -1;
    //product desc

    //rating
    private LinearLayout rateNowContainer;
    private TextView totalRatings;
    private LinearLayout ratingsNoContainer;
    private TextView totalRatingsFigure;
    private LinearLayout ratingsProgressBarContainer;
    private TextView avgRating;
    //rating

    private Button buyNowBtn;
    private List<String> productImages;
    private static boolean isAddedToWishlist = false;
    private FloatingActionButton addToWishlistBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImageViewPager = findViewById(R.id.product_image_view_pager);
        viewPagerIndicator = findViewById(R.id.view_pager_indicator);
        addToWishlistBtn = findViewById(R.id.add_to_wishlist_btn);
        productDetailsViewPager = findViewById(R.id.product_details_view_pager);
        productDetailsTabLayout = findViewById(R.id.product_details_tab_layout);
        buyNowBtn = findViewById(R.id.buy_now_btn);
        productTitle = findViewById(R.id.product_title);
        avgRatingMiniView = findViewById(R.id.tv_product_rating_miniview);
        totalRatingMiniView = findViewById(R.id.total_rating_miniview);
        productPrice = findViewById(R.id.product_price);
        cuttedPrice = findViewById(R.id.cutted_price);
        productDetailsTabsContainer = findViewById(R.id.product_details_tabs_container);
        totalRatings = findViewById(R.id.total_ratings);
        ratingsNoContainer = findViewById(R.id.ratings_number_container);
        totalRatingsFigure = findViewById(R.id.total_ratings_figure);
        ratingsProgressBarContainer = findViewById(R.id.ratings_progressbar_container);
        avgRating = findViewById(R.id.avg_rating);
        productImages = new ArrayList<>();

//        productImages.add(R.mipmap.steakhouse);
//        productImages.add(R.mipmap.steakhouse);
//        productImages.add(R.mipmap.steakhouse);
//        productImages.add(R.mipmap.steakhouse);
//        productImages.add(R.mipmap.steakhouse);

//        ProductImageAdapter productImageAdapter = new ProductImageAdapter(productImages);
//        productImageViewPager.setAdapter(productImageAdapter);
//        productImageAdapter.notifyDataSetChanged();
        doGetProductDetail(getIntent().getIntExtra("id", 1));

        viewPagerIndicator.setupWithViewPager(productImageViewPager, true);
        addToWishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAddedToWishlist) {
                    isAddedToWishlist = false;
                    addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                } else {
                    isAddedToWishlist = true;
                    addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                }
            }
        });

//        productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount()));
        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));
        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                tabsPosition = tab.getPosition();
                productDetailsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        rateNowContainer = findViewById(R.id.rating_now_container);
        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
            final int starPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(starPosition);
                }
            });
        }
    }

    private void setRating(int starPosition) {
        for (int x = 0; x < rateNowContainer.getChildCount(); x ++){
            ImageView starBtn = (ImageView) rateNowContainer.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if (x <= starPosition){
                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.product_detail_search_icon) {
            return true;
        } else if (id == R.id.product_detail_cart_icon) {
            Intent cartIntent = new Intent(ProductDetailActivity.this, HomeActivity.class);
            showCart = true;
            startActivity(cartIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void doGetProductDetail(int id) {
        ProductDetailAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(ProductDetailAPI.class);
        Call<ProductDetailResponse> call = api.getProductDetail(id);
        call.enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        for (ProductImageData data : response.body().getData().getImages()){
                            productImages.add(data.getImageURL());
                        }
                        productTitle.setText(response.body().getData().getName());
                        productPrice.setText(String.format("$%s", response.body().getData().getOriginalPrice() * (1 - response.body().getData().getDiscount()/100)));
                        cuttedPrice.setText(String.format("$%s", response.body().getData().getOriginalPrice()));
                        productDescription = response.body().getData().getDescription();
                        productOtherDetails = response.body().getData().getDescription();

                        ProductImageAdapter productImageAdapter = new ProductImageAdapter(productImages);
                        productImageViewPager.setAdapter(productImageAdapter);

                        productImageAdapter.notifyDataSetChanged();
                        productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount(), productDescription, productOtherDetails));
                    } else if (response.body().getStatus().equals("FAILED")){
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
