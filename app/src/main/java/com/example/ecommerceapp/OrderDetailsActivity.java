package com.example.ecommerceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.ecommerceapp.adapters.OrderDetailsAdapter;
import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.OrderDetailsModel;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.responses.OrderDetailsData;
import com.example.ecommerceapp.models.entities.responses.OrderDetailsResponse;
import com.example.ecommerceapp.models.interfaces.OrderDetailsAPI;
import com.example.ecommerceapp.models.services.OrderDetailsService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailsActivity extends AppCompatActivity implements OrderDetailsService {
    private List<OrderDetailsModel> orderDetailsModelList;

    private RecyclerView orderDetailsRecyclerView;
    private OrderDetailsAdapter orderDetailsAdapter;

    private int orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Order details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        orderDetailsRecyclerView = findViewById(R.id.order_details_recycler_view);
        orderID = getIntent().getIntExtra("orderID", 1);

        orderDetailsModelList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        orderDetailsRecyclerView.setLayoutManager(layoutManager);

        doGetOrderDetails(orderID);
//        orderDetailsAdapter = new OrderDetailsAdapter(orderDetailsModelList);
//        orderDetailsRecyclerView.setAdapter(orderDetailsAdapter);
//        orderDetailsAdapter.notifyDataSetChanged();
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
    public void doGetOrderDetails(int id) {
        OrderDetailsAPI api = RetrofitClient.getClient(BaseURLConst.BASE_URL).create(OrderDetailsAPI.class);
        Call<OrderDetailsResponse> call = api.getOrderDetails(id);
        call.enqueue(new Callback<OrderDetailsResponse>() {
            @Override
            public void onResponse(Call<OrderDetailsResponse> call, Response<OrderDetailsResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        for (OrderDetailsData data : response.body().getData()) {
                            orderDetailsModelList.add(new OrderDetailsModel(data.getId(),
                                    data.getName(),
                                    data.getImage(),
                                    data.getPrice(),
                                    data.getQuantity()));
                        }
                        orderDetailsAdapter = new OrderDetailsAdapter(orderDetailsModelList);
                        orderDetailsRecyclerView.setAdapter(orderDetailsAdapter);
                        orderDetailsAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
