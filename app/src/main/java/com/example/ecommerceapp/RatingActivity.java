package com.example.ecommerceapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.requests.RatingRequest;
import com.example.ecommerceapp.models.entities.responses.RatingResponse;
import com.example.ecommerceapp.models.interfaces.RatingAPI;
import com.example.ecommerceapp.models.services.RatingService;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingActivity extends AppCompatActivity implements RatingService {

    private MaterialRatingBar materialRatingBar;
    private TextView commentTextView;
    private Button addRatingBtn;
    private int code;
    private int productID;
    private SharedPreferences sharedPreferences;
    private Dialog loadingDialog;
    private float rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        materialRatingBar = findViewById(R.id.add_rating_bar);
        commentTextView = findViewById(R.id.comment);
        addRatingBtn = findViewById(R.id.add_rating_btn);

        loadingDialog = new Dialog(RatingActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        sharedPreferences = getSharedPreferences("signin_info", MODE_PRIVATE);
        productID = getIntent().getIntExtra("pid",0);
        code = getIntent().getIntExtra("code",0);

        materialRatingBar.setRating(1);
        if (code == 1){
            rating = getIntent().getFloatExtra("rating", 1);
            String cmt = getIntent().getStringExtra("cmt");
            materialRatingBar.setRating(rating);
            commentTextView.setText(cmt);
        }

        addRatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRating(productID, sharedPreferences.getInt("id", 0));
            }
        });
    }


    @Override
    public void doRating(int pid, int uid) {
        loadingDialog.show();
        RatingRequest request = new RatingRequest();
        request.setRating((int) materialRatingBar.getRating());
        request.setComment(commentTextView.getText().toString());

        RatingAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(RatingAPI.class);
        Call<RatingResponse> call = api.rating(pid, uid, request);
        if (code == 1) {
            call = api.updateRating(pid, uid, request);
        }

        call.enqueue(new Callback<RatingResponse>() {
            @Override
            public void onResponse(Call<RatingResponse> call, Response<RatingResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        loadingDialog.dismiss();
                        Intent productIntent = new Intent(RatingActivity.this, ProductDetailActivity.class);
                        productIntent.putExtra("productID",productID);
                        startActivity(productIntent);
                        finish();
                        Toast.makeText(getApplicationContext(), "Rate this product successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        loadingDialog.dismiss();
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RatingResponse> call, Throwable t) {
                loadingDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
