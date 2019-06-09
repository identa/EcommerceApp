package com.example.ecommerceapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.adapters.GetRatingAdapter;
import com.example.ecommerceapp.adapters.ProductDetailsAdapter;
import com.example.ecommerceapp.adapters.ProductImageAdapter;
import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.CartItemModel;
import com.example.ecommerceapp.models.GetRatingModel;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.responses.AddCartResponse;
import com.example.ecommerceapp.models.entities.responses.AddWishlistResponse;
import com.example.ecommerceapp.models.entities.responses.DeleteCartResponse;
import com.example.ecommerceapp.models.entities.responses.GetRatingData;
import com.example.ecommerceapp.models.entities.responses.GetRatingResponse;
import com.example.ecommerceapp.models.entities.responses.ProductDetailResponse;
import com.example.ecommerceapp.models.entities.responses.ProductImageData;
import com.example.ecommerceapp.models.interfaces.ProductDetailAPI;
import com.example.ecommerceapp.models.services.ProductDetailService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
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
//    private LinearLayout rateNowContainer;
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
    private int id;
    private FloatingActionButton addToWishlistBtn;
    private List<CartItemModel> cartItemModelList;
    private LinearLayout tradeLayout;
    private TextView quantityView;
//    private RatingBar ratingBar;

    private FirebaseUser currentUser;
    private Dialog signInDialog;
    private Dialog loadingDialog;

    private SharedPreferences sharedPreferences;
    private int qty;

    private MaterialRatingBar ratingBar;
    private RecyclerView ratingRecyclerView;
    private TextView ratingTextView;
    private Button addRatingBtn;

    private List<GetRatingModel> getRatingModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        sharedPreferences = getSharedPreferences("signin_info", MODE_PRIVATE);

        productImageViewPager = findViewById(R.id.product_image_view_pager);
        viewPagerIndicator = findViewById(R.id.view_pager_indicator);
        addToWishlistBtn = findViewById(R.id.add_to_wishlist_btn);
        productDetailsViewPager = findViewById(R.id.product_details_view_pager);
        productDetailsTabLayout = findViewById(R.id.product_details_tab_layout);
        buyNowBtn = findViewById(R.id.buy_now_btn);
        addToCartBtn = findViewById(R.id.add_to_cart_btn);
        productTitle = findViewById(R.id.product_title);
//        avgRatingMiniView = findViewById(R.id.tv_product_rating_miniview);
//        totalRatingMiniView = findViewById(R.id.total_rating_miniview);
        productPrice = findViewById(R.id.product_price);
        cuttedPrice = findViewById(R.id.cutted_price);
        productDetailsTabsContainer = findViewById(R.id.product_details_tabs_container);
//        totalRatings = findViewById(R.id.total_ratings);
//        ratingsNoContainer = findViewById(R.id.ratings_number_container);
//        totalRatingsFigure = findViewById(R.id.total_ratings_figure);
//        ratingsProgressBarContainer = findViewById(R.id.ratings_progressbar_container);
//        avgRating = findViewById(R.id.avg_rating);
        addToCartTextView = findViewById(R.id.tv_add_to_cart);
        tradeLayout = findViewById(R.id.trade_layout);
        quantityView = findViewById(R.id.quantity);
//        ratingBar = findViewById(R.id.rating_now_bar);
        ratingRecyclerView = findViewById(R.id.get_rating_recycler_view);
        ratingBar = findViewById(R.id.rating_now_bar);
        ratingTextView = findViewById(R.id.rating_text_view);
        addRatingBtn = findViewById(R.id.add_rating_btn);


        loadingDialog = new Dialog(ProductDetailActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        if (sharedPreferences.getString("email", "no_email").equals("no_email")){
            tradeLayout.setVisibility(View.GONE);
            addToWishlistBtn.hide();
        }
        id = getIntent().getIntExtra("productID", 1);
        productImages = new ArrayList<>();
        cartItemModelList = new ArrayList<>();

        doGetProductDetail(id, sharedPreferences.getInt("id", 1));

        viewPagerIndicator.setupWithViewPager(productImageViewPager, true);

        getRatingModelList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ratingRecyclerView.setLayoutManager(layoutManager);

        doGetRating(id, sharedPreferences.getInt("id", 0));

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

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryActivity.cartItemModelList = cartItemModelList;
                Intent deliveryIntent = new Intent(ProductDetailActivity.this, DeliveryActivity.class);
                deliveryIntent.putExtra("buyNow", true);
                startActivity(deliveryIntent);
            }
        });
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
                        for (ProductImageData data : response.body().getData().getImages()) {
                            productImages.add(data.getImageURL());
                        }

                        productTitle.setText(response.body().getData().getName());
                        productPrice.setText(String.format("$%s", response.body().getData().getCurrentPrice()));
                        cuttedPrice.setText(String.format("$%s", response.body().getData().getOriginalPrice()));
                        productDescription = response.body().getData().getDescription();
                        productOtherDetails = response.body().getData().getDescription();
                        isAddedToCart = response.body().getData().isInCart();
                        isAddedToWishlist = response.body().getData().isInWishlist();
                        qty = response.body().getData().getQuantity();
                        quantityView.setText(String.format("Quantity: %d", qty));

                        cartItemModelList.add(new CartItemModel(0, response.body().getData().getId(),
                                response.body().getData().getImages().get(0).getImageURL(),
                                response.body().getData().getName(),
                                response.body().getData().getCurrentPrice(),
                                response.body().getData().getOriginalPrice(),
                                1));

                        cartItemModelList.add(new CartItemModel(1,
                                1,
                                response.body().getData().getCurrentPrice(),
                                1));

                        if (response.body().getData().getQuantity() <= 0){
                            buyNowBtn.setVisibility(View.GONE);
                            TextView outOfStock = (TextView) addToCartBtn.getChildAt(0);
                            outOfStock.setText("Out of stock");
                            outOfStock.setTextColor(getResources().getColor(R.color.colorPrimary));
                            outOfStock.setCompoundDrawables(null, null, null, null);
                        }else {
                            if (isAddedToCart) {
                                addToCartTextView = findViewById(R.id.tv_add_to_cart);
                                addToCartTextView.setText("ADREADY ADDED TO");
                                addToCartBtn.setOnClickListener(null);
                            } else {
                                addToCartBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        doAddToCart(id, sharedPreferences.getInt("id", 1));
                                    }
                                });
                            }
                        }

                        if (isAddedToWishlist) {
                            addToWishlistBtn.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));
                        } else {
                            addToWishlistBtn.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                        }

                        addToWishlistBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isAddedToWishlist) {
                                    doDeleteWishlist(id, sharedPreferences.getInt("id", 1));
                                } else {
                                    doAddToWishlist(id, sharedPreferences.getInt("id", 1));
                                }
                            }
                        });

                        ProductImageAdapter productImageAdapter = new ProductImageAdapter(productImages);
                        productImageViewPager.setAdapter(productImageAdapter);

                        productImageAdapter.notifyDataSetChanged();
                        productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount(), productDescription, productOtherDetails));
                        loadingDialog.dismiss();
                    } else if (response.body().getStatus().equals("FAILED")) {
                        loadingDialog.dismiss();
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                loadingDialog.dismiss();
                Toast.makeText(ProductDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
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
                        qty --;
                        quantityView.setText(String.format("Quantity: %d", qty));

                        if (qty == 0){
                            buyNowBtn.setVisibility(View.GONE);
                            TextView outOfStock = (TextView) addToCartBtn.getChildAt(0);
                            outOfStock.setText("Out of stock");
                            outOfStock.setTextColor(getResources().getColor(R.color.colorPrimary));
                            outOfStock.setCompoundDrawables(null, null, null, null);
                        }
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

    @Override
    public void doGetRating(int pid, int uid) {
        ProductDetailAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(ProductDetailAPI.class);
        Call<GetRatingResponse> call = api.getRating(pid, uid);
        call.enqueue(new Callback<GetRatingResponse>() {
            @Override
            public void onResponse(Call<GetRatingResponse> call, final Response<GetRatingResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        if (response.body().getData().getRatings().size() != 0) {
                            ratingTextView.setVisibility(View.GONE);
                            ratingRecyclerView.setVisibility(View.VISIBLE);
                            for (GetRatingData data : response.body().getData().getRatings()) {
                                getRatingModelList.add(new GetRatingModel(data.getName(),
                                        data.getRating(),
                                        data.getCmt()));
                            }
                            GetRatingAdapter getRatingAdapter = new GetRatingAdapter(getRatingModelList);
                            ratingRecyclerView.setAdapter(getRatingAdapter);

                            getRatingAdapter.notifyDataSetChanged();
                        } else {
                            ratingRecyclerView.setVisibility(View.GONE);
                            ratingTextView.setVisibility(View.VISIBLE);
                        }

                        if (response.body().getData().getRating() != null){
                            ratingBar.setIsIndicator(true);
                            ratingBar.setRating(response.body().getData().getRating());
                            addRatingBtn.setText("Update your rating");
                            addRatingBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent addRaIntent = new Intent(ProductDetailActivity.this, RatingActivity.class);
                                    addRaIntent.putExtra("code", 1);
                                    addRaIntent.putExtra("pid", id);
                                    addRaIntent.putExtra("rating", response.body().getData().getRating().floatValue());
                                    addRaIntent.putExtra("cmt", response.body().getData().getCmt());
                                    startActivity(addRaIntent);
                                }
                            });
                        }
                        else {
                            ratingBar.setVisibility(View.GONE);
                            addRatingBtn.setText("Add your rating");
                            addRatingBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent addRaIntent = new Intent(ProductDetailActivity.this, RatingActivity.class);
                                    addRaIntent.putExtra("pid", id);
                                    startActivity(addRaIntent);
                                }
                            });

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetRatingResponse> call, Throwable t) {

            }
        });
    }
}
