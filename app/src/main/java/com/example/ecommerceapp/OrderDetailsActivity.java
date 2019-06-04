package com.example.ecommerceapp;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView shippingName;
    private TextView shippingAddress;
    private TextView shippingCity;
    private TextView shippingState;
    private TextView shippingPostalCode;
    private TextView shippingPhone;
    private Dialog loadingDialog;

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
        shippingName = findViewById(R.id.shipping_name);
        shippingAddress = findViewById(R.id.shipping_address);
        shippingCity = findViewById(R.id.shipping_city);
        shippingState = findViewById(R.id.shipping_state);
        shippingPostalCode = findViewById(R.id.shipping_postal_code);
        shippingPhone = findViewById(R.id.shipping_phone);

        loadingDialog = new Dialog(OrderDetailsActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        orderID = getIntent().getIntExtra("orderID", 1);

        orderDetailsModelList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        orderDetailsRecyclerView.setLayoutManager(layoutManager);

        doGetOrderDetails(orderID);
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
                        for (OrderDetailsData data : response.body().getData().getOrderDetails()) {
                            orderDetailsModelList.add(new OrderDetailsModel(data.getId(),
                                    data.getName(),
                                    data.getImage(),
                                    data.getPrice(),
                                    data.getQuantity()));
                        }

                        shippingName.setText(response.body().getData().getRecipientName());
                        shippingCity.setText(response.body().getData().getCity());
                        shippingState.setText(response.body().getData().getState());
                        shippingAddress.setText(response.body().getData().getAddress());
                        shippingPostalCode.setText(String.format("%d", response.body().getData().getPostalCode()));
                        shippingPhone.setText(response.body().getData().getPhone());

                        orderDetailsAdapter = new OrderDetailsAdapter(orderDetailsModelList);
                        orderDetailsRecyclerView.setAdapter(orderDetailsAdapter);
                        orderDetailsAdapter.notifyDataSetChanged();
                        loadingDialog.dismiss();
                    } else {
                        loadingDialog.dismiss();
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                loadingDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
