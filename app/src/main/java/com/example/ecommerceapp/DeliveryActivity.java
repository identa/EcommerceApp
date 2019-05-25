package com.example.ecommerceapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.adapters.CartAdapter;
import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.CartItemModel;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.requests.AddOrderReq;
import com.example.ecommerceapp.models.entities.requests.AddOrderRequest;
import com.example.ecommerceapp.models.entities.responses.AddOrderResponse;
import com.example.ecommerceapp.models.entities.responses.GetAddressResponse;
import com.example.ecommerceapp.models.interfaces.AddOrderAPI;
import com.example.ecommerceapp.models.services.AddOrderService;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.android.sdk.payments.ShippingAddress;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryActivity extends AppCompatActivity implements AddOrderService {

    private RecyclerView deliveryRecyclerView;
    public static List<CartItemModel> cartItemModelList;
    private TextView totalAmount;
    private TextView shippingName;
    private TextView shippingAddress;
    private TextView shippingCity;
    private TextView shippingState;
    private TextView shippingPostalCode;

    private Button continuewBtn;
    private Dialog loadingDialog;
    private Dialog paymentMethodDialog;
    private ImageButton paytm;
    private View include;

    private ConstraintLayout orderConfirmationLayout;
    private ImageButton continueShoppingBtn;
    private TextView orderID;
    private int orderNoID;

    private static final int REQUEST_CODE = 0;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");
        sharedPreferences = getSharedPreferences("signin_info", MODE_PRIVATE);

        deliveryRecyclerView = findViewById(R.id.delivery_recycler_view);
        totalAmount = findViewById(R.id.total_cart_amount);
        include = findViewById(R.id.include);
        shippingName = findViewById(R.id.shipping_name);
        shippingAddress = findViewById(R.id.shipping_address);
        shippingCity = findViewById(R.id.shipping_city);
        shippingState = findViewById(R.id.shipping_state);
        shippingPostalCode = findViewById(R.id.shipping_postal_code);

        continuewBtn = findViewById(R.id.cart_continue_btn);
        orderConfirmationLayout = findViewById(R.id.order_confirm_layout);
        continueShoppingBtn = findViewById(R.id.continue_shopping_btn);
        orderID = findViewById(R.id.order_id);

        getAddress(sharedPreferences.getInt("id", 1));
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        startService(intent);
        //loading 76 - 12:04

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(layoutManager);

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(DeliveryActivity.this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirmation != null) {
                try {
                    doAddOrder(sharedPreferences.getInt("id", 1));
                    String paymentDetails = confirmation.toJSONObject().toString(4);
                    Log.i("payment", paymentDetails);
                    Log.i("payment", confirmation.getPayment().toJSONObject().toString(4));
                    orderConfirmationLayout.setVisibility(View.VISIBLE);
                    orderID.setText("Order ID " + orderNoID);
                    continueShoppingBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HomeActivity.showCart = false;
                            Intent homeIntent = new Intent(DeliveryActivity.this, HomeActivity.class);
                            startActivity(homeIntent);
                        }
                    });
                    Toast.makeText(this, "Payment is successful", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Payment is cancelled", Toast.LENGTH_SHORT).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Toast.makeText(this, "Invalid payment", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private static PayPalConfiguration configuration = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("ATn5fXIAjF-ITaCoG5AnlDL2B4NqwzVgJElPov-HzAlqBNRrQy2LEOPQWjgCVlXla7cp-_GCO1esALmv");

    @Override
    public void doAddOrder(int id) {
        AddOrderAPI api = RetrofitClient.getClient(BaseURLConst.BASE_URL).create(AddOrderAPI.class);
        List<AddOrderRequest> requests = new ArrayList<>();
        for (int i = 0; i < cartItemModelList.size() - 1; i++) {
            AddOrderRequest request = new AddOrderRequest();
            request.setId(cartItemModelList.get(i).getProductID());
            request.setPrice(cartItemModelList.get(i).getProductPrice());
            request.setCuttedPrice(cartItemModelList.get(i).getCuttedPrice());
            request.setQuantity(cartItemModelList.get(i).getProductQuantity());
            requests.add(request);
        }

        AddOrderReq req = new AddOrderReq();
        req.setOrders(requests);
        Call<AddOrderResponse> call = api.addOrder(id, req);
        call.enqueue(new Callback<AddOrderResponse>() {
            @Override
            public void onResponse(Call<AddOrderResponse> call, Response<AddOrderResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        orderNoID = response.body().getData().getId();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddOrderResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    @Override
    public void getAddress(int id) {
        AddOrderAPI api = RetrofitClient.getClient(BaseURLConst.BASE_URL).create(AddOrderAPI.class);
        Call<GetAddressResponse> call = api.getAddress(id);
        call.enqueue(new Callback<GetAddressResponse>() {
            @Override
            public void onResponse(Call<GetAddressResponse> call, Response<GetAddressResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        if (response.body().getData() == null) {
                            include.setVisibility(View.GONE);
                            continuewBtn.setText("ADD ADDRESS");
                            continuewBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent addAddressIntent = new Intent(getApplicationContext(), AddAddressActivity.class);
                                    addAddressIntent.putExtra("mode", 2);
                                    startActivity(addAddressIntent);
                                }
                            });
                        } else {
                            include.setVisibility(View.VISIBLE);
                            shippingName.setText(response.body().getData().getRecipientName());
                            shippingCity.setText(response.body().getData().getCity());
                            shippingState.setText(response.body().getData().getState());
                            shippingAddress.setText(response.body().getData().getAddress());
                            shippingPostalCode.setText(String.format("%d", response.body().getData().getPostalCode()));

                            //payment
                            paymentMethodDialog = new Dialog(DeliveryActivity.this);
                            paymentMethodDialog.setContentView(R.layout.payment_method);
                            paymentMethodDialog.setCancelable(true);
                            paymentMethodDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
                            paymentMethodDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            paytm = paymentMethodDialog.findViewById(R.id.paytm);
                            //payment

                            continuewBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    paymentMethodDialog.show();

                                    paytm.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            paymentMethodDialog.dismiss();
                                            PayPalPayment payment = new PayPalPayment(new BigDecimal(cartItemModelList.get(cartItemModelList.size() - 1).getTotalItemPrice()), "USD", "Anh", PayPalPayment.PAYMENT_INTENT_SALE);
                                            ShippingAddress address = new ShippingAddress().recipientName(shippingName.getText().toString())
                                                    .city(shippingCity.getText().toString())
                                                    .line1(shippingAddress.getText().toString())
                                                    .state(shippingState.getText().toString())
                                                    .postalCode(shippingPostalCode.getText().toString())
                                                    .countryCode("VN");
                                            payment.providedShippingAddress(address);
                                            Intent paypalIntent = new Intent(DeliveryActivity.this, PaymentActivity.class);
                                            paypalIntent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
                                            paypalIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
                                            startActivityForResult(paypalIntent, REQUEST_CODE);
                                        }
                                    });
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAddressResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
