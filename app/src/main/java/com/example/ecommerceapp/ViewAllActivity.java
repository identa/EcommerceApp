package com.example.ecommerceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.example.ecommerceapp.adapters.GridProductLayoutAdapter;
import com.example.ecommerceapp.adapters.HorizontalProductScrollAdapter;
import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.HorizontalProductScrollModel;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.responses.HomePageProductData;
import com.example.ecommerceapp.models.entities.responses.HomePageProductResponse;
import com.example.ecommerceapp.models.interfaces.HomePageAPI;
import com.example.ecommerceapp.models.services.HomePageService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllActivity extends AppCompatActivity implements HomePageService {

    private RecyclerView recyclerView;
    private GridView gridView;
    private List<HorizontalProductScrollModel> gridModelList;
    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;
    private HorizontalProductScrollAdapter horizontalProductScrollAdapter;
    private GridProductLayoutAdapter gridProductLayoutAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        gridView = findViewById(R.id.grid_view);

        int layout_code = getIntent().getIntExtra("layout_code", -1);

        if (layout_code == 0){
            horizontalProductScrollModelList = new ArrayList<>();
            doGetMostViewedProduct();
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            recyclerView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }else if (layout_code == 1){
            gridModelList = new ArrayList<>();
            doGetMostOrderedProduct();
            gridView.setVisibility(View.VISIBLE);
            gridProductLayoutAdapter = new GridProductLayoutAdapter(gridModelList);
            gridView.setAdapter(gridProductLayoutAdapter);
            gridProductLayoutAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void doGetSlider() {

    }

    @Override
    public void doGetMostViewedProduct() {
        HomePageAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(HomePageAPI.class);
        Call<HomePageProductResponse> call = api.getMVProduct();
        call.enqueue(new Callback<HomePageProductResponse>() {
            @Override
            public void onResponse(Call<HomePageProductResponse> call, Response<HomePageProductResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        for (HomePageProductData data : response.body().getData()){
                            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(data.getId(),
                                    data.getImageURL(),
                                    data.getName(),
                                    data.getCatName(),
                                    data.getPrice()));
                        }
                        horizontalProductScrollAdapter.notifyDataSetChanged();
                    } else if (response.body().getStatus().equals("FAILED")){
                    }
                }
            }

            @Override
            public void onFailure(Call<HomePageProductResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    @Override
    public void doGetMostOrderedProduct() {
        HomePageAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(HomePageAPI.class);
        Call<HomePageProductResponse> call = api.getMOProduct();
        call.enqueue(new Callback<HomePageProductResponse>() {
            @Override
            public void onResponse(Call<HomePageProductResponse> call, Response<HomePageProductResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        for (HomePageProductData data : response.body().getData()){
                            gridModelList.add(new HorizontalProductScrollModel(data.getId(),
                                    data.getImageURL(),
                                    data.getName(),
                                    data.getCatName(),
                                    data.getPrice()));
                        }
                        gridProductLayoutAdapter.notifyDataSetChanged();
                    } else if (response.body().getStatus().equals("FAILED")){
                    }
                }
            }

            @Override
            public void onFailure(Call<HomePageProductResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
