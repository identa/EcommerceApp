package com.example.ecommerceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.AddAddressModel;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.requests.AddressRequest;
import com.example.ecommerceapp.models.entities.responses.GetAddressResponse;
import com.example.ecommerceapp.models.interfaces.AddAddressAPI;
import com.example.ecommerceapp.models.services.AddAddressService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity implements AddAddressService {

    private Button saveBtn;
    private EditText city;
    private EditText locality;
    private EditText postalCode;
    private EditText state;
    private EditText recipientName;
    private EditText mobilePhone;

    private int mode;

    public static AddAddressModel addAddressModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Add a new address");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saveBtn = findViewById(R.id.save_btn);
        city = findViewById(R.id.city);
        locality = findViewById(R.id.locality);
        postalCode = findViewById(R.id.postal_code);
        state = findViewById(R.id.state);
        recipientName = findViewById(R.id.name);
        mobilePhone = findViewById(R.id.mobile_no);

        final SharedPreferences sharedPreferences = getSharedPreferences("signin_info", MODE_PRIVATE);

        mode = getIntent().getIntExtra("mode", -1);

        if (mode == 0){
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doAddAddress(sharedPreferences.getInt("id", 1));
//                    Intent deliveryIntent = new Intent(AddAddressActivity.this, HomeActivity.class);
//                    deliveryIntent.putExtra("showAccount", true);
//                    startActivity(deliveryIntent);
//                    finish();
                }
            });

        }else if (mode == 1){
            city.setText(addAddressModel.getCity());
            locality.setText(addAddressModel.getAddress());
            postalCode.setText(String.format("%d", addAddressModel.getPostalCode()));
            state.setText(addAddressModel.getState());
            recipientName.setText(addAddressModel.getRecipientName());

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doEditAddress(sharedPreferences.getInt("id", 1));
//                    Intent deliveryIntent = new Intent(AddAddressActivity.this, HomeActivity.class);
//                    deliveryIntent.putExtra("showAccount", true);
//                    startActivity(deliveryIntent);
//                    finish();
                }
            });
        }else if (mode == 2){
            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doAddAddress(sharedPreferences.getInt("id", 1));
//                    Intent deliveryIntent = new Intent(AddAddressActivity.this, DeliveryActivity.class);
//                    startActivity(deliveryIntent);
//                    finish();
                }
            });
        }
//        saveBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent deliveryIntent = new Intent(AddAddressActivity.this, DeliveryActivity.class);
//                startActivity(deliveryIntent);
//                finish();
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void doAddAddress(int id) {
        AddAddressAPI api = RetrofitClient.getClient(BaseURLConst.BASE_URL).create(AddAddressAPI.class);
            AddressRequest request = new AddressRequest();
            request.setCity(city.getText().toString());
            request.setAddress(locality.getText().toString());
            request.setPostalCode(Integer.parseInt(postalCode.getText().toString()));
            request.setRecipientName(recipientName.getText().toString());
            request.setState(state.getText().toString());

        Call<GetAddressResponse> call = api.addAddress(id, request);
        call.enqueue(new Callback<GetAddressResponse>() {
            @Override
            public void onResponse(Call<GetAddressResponse> call, Response<GetAddressResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")){
                        if (mode == 0){
                            Intent deliveryIntent = new Intent(AddAddressActivity.this, HomeActivity.class);
                            deliveryIntent.putExtra("showAccount", true);
                            startActivity(deliveryIntent);
                            finish();
                        }else if (mode == 2){
                            Intent deliveryIntent = new Intent(AddAddressActivity.this, DeliveryActivity.class);
                            startActivity(deliveryIntent);
                            finish();
                        }
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAddressResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    @Override
    public void doEditAddress(int id) {
        AddAddressAPI api = RetrofitClient.getClient(BaseURLConst.BASE_URL).create(AddAddressAPI.class);
        AddressRequest request = new AddressRequest();
        request.setCity(city.getText().toString());
        request.setAddress(locality.getText().toString());
        request.setPostalCode(Integer.parseInt(postalCode.getText().toString()));
        request.setRecipientName(recipientName.getText().toString());
        request.setState(state.getText().toString());

        Call<GetAddressResponse> call = api.editAddress(id, request);
        call.enqueue(new Callback<GetAddressResponse>() {
            @Override
            public void onResponse(Call<GetAddressResponse> call, Response<GetAddressResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")){
                            Intent deliveryIntent = new Intent(AddAddressActivity.this, HomeActivity.class);
                            deliveryIntent.putExtra("showAccount", true);
                            startActivity(deliveryIntent);
                            finish();

                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
