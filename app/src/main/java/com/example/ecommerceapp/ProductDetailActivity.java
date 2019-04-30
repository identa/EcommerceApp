package com.example.ecommerceapp;

import android.app.Dialog;
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
import android.widget.Toast;

import com.example.ecommerceapp.adapters.ProductDetailsAdapter;
import com.example.ecommerceapp.adapters.ProductImageAdapter;
import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.CartItemModel;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.responses.AddCartResponse;
import com.example.ecommerceapp.models.entities.responses.AddWishlistResponse;
import com.example.ecommerceapp.models.entities.responses.DeleteCartResponse;
import com.example.ecommerceapp.models.entities.responses.ProductDetailData;
import com.example.ecommerceapp.models.entities.responses.ProductDetailResponse;
import com.example.ecommerceapp.models.entities.responses.ProductImageData;
import com.example.ecommerceapp.models.interfaces.ProductDetailAPI;
import com.example.ecommerceapp.models.services.ProductDetailService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private TextView addToCartTextView;
    //rating

    private Button buyNowBtn;
    private LinearLayout addToCartBtn;
    private List<String> productImages;
    private static boolean isAddedToWishlist = false;
    private static boolean isAddedToCart = false;
    public static int productID;
    private FloatingActionButton addToWishlistBtn;
    private List<CartItemModel> cartItemModelList;

    private FirebaseUser currentUser;
    private Dialog signInDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        productImageViewPager = findViewById(R.id.product_image_view_pager);
        viewPagerIndicator = findViewById(R.id.view_pager_indicator);
        addToWishlistBtn = findViewById(R.id.add_to_wishlist_btn);
        productDetailsViewPager = findViewById(R.id.product_details_view_pager);
        productDetailsTabLayout = findViewById(R.id.product_details_tab_layout);
        buyNowBtn = findViewById(R.id.buy_now_btn);
        addToCartBtn = findViewById(R.id.add_to_cart_btn);
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
        addToCartTextView = findViewById(R.id.tv_add_to_cart);

        productImages = new ArrayList<>();
        cartItemModelList = new ArrayList<>();
//        productImages.add(R.mipmap.steakhouse);
//        productImages.add(R.mipmap.steakhouse);
//        productImages.add(R.mipmap.steakhouse);
//        productImages.add(R.mipmap.steakhouse);
//        productImages.add(R.mipmap.steakhouse);

//        ProductImageAdapter productImageAdapter = new ProductImageAdapter(productImages);
//        productImageViewPager.setAdapter(productImageAdapter);
//        productImageAdapter.notifyDataSetChanged();
        doGetProductDetail(productID, 2);

        viewPagerIndicator.setupWithViewPager(productImageViewPager, true);
        addToWishlistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAddedToWishlist) {
                    doDeleteWishlist(productID, 2);
//                    isAddedToWishlist = false;
//                    addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                } else {
                    doAddToWishlist(productID, 2);
//                    isAddedToWishlist = true;
//                    addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
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

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryActivity.cartItemModelList = cartItemModelList;
                Intent deliveryIntent = new Intent(ProductDetailActivity.this, DeliveryActivity.class);
                startActivity(deliveryIntent);
            }
        });
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
    public void doGetProductDetail(int pid, int uid) {
        ProductDetailAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(ProductDetailAPI.class);
        Call<ProductDetailResponse> call = api.getProductDetail(pid, uid);
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
                        isAddedToCart = response.body().getData().isInCart();

                        cartItemModelList.add(new CartItemModel(0, response.body().getData().getId(),
                                response.body().getData().getImages().get(0).getImageURL(),
                                response.body().getData().getName(),
                                response.body().getData().getOriginalPrice() * (1 - response.body().getData().getDiscount()/100),
                                response.body().getData().getOriginalPrice(),
                                1));

                        cartItemModelList.add(new CartItemModel(1,
                                1,
                                response.body().getData().getOriginalPrice() * (1 - response.body().getData().getDiscount()/100),
                                1));

                        if (isAddedToCart){
                            addToCartTextView = findViewById(R.id.tv_add_to_cart);
                            addToCartTextView.setText("ADREADY ADDED TO");
                            addToCartBtn.setOnClickListener(null);
                        }else {
                            addToCartBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (currentUser == null){
//                        signInDialog.show();
                                    }
                                    else {
                                        doAddToCart(productID, 2);
                                    }
                                }
                            });
                        }
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

    @Override
    public void doAddToCart(int pid, int uid) {
        ProductDetailAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(ProductDetailAPI.class);
        Call<AddCartResponse> call = api.addToCart(pid, uid);
        call.enqueue(new Callback<AddCartResponse>() {
            @Override
            public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        isAddedToCart = true;
                        addToCartTextView = findViewById(R.id.tv_add_to_cart);
                        addToCartTextView.setText("ADREADY ADDED TO");
                        Toast.makeText(ProductDetailActivity.this, "Add to cart successfully!", Toast.LENGTH_SHORT).show();
                        addToCartBtn.setOnClickListener(null);
                    }
                }
            }

            @Override
            public void onFailure(Call<AddCartResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void doAddToWishlist(int pid, int uid) {
        ProductDetailAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(ProductDetailAPI.class);
        Call<AddWishlistResponse> call = api.addToWishlist(pid, uid);
        call.enqueue(new Callback<AddWishlistResponse>() {
            @Override
            public void onResponse(Call<AddWishlistResponse> call, Response<AddWishlistResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        isAddedToWishlist = true;
                        addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                        Toast.makeText(ProductDetailActivity.this, "Add to wishlist successfully!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddWishlistResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void doDeleteWishlist(int pid, int uid) {
        ProductDetailAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(ProductDetailAPI.class);
        Call<DeleteCartResponse> call = api.deleteWishlist(pid, uid);
        call.enqueue(new Callback<DeleteCartResponse>() {
            @Override
            public void onResponse(Call<DeleteCartResponse> call, Response<DeleteCartResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        isAddedToWishlist = false;
                        addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        Toast.makeText(ProductDetailActivity.this, "Delete wishlist successfully!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteCartResponse> call, Throwable t) {

            }
        });
    }
}
