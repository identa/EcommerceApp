package com.example.ecommerceapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.responses.DeleteCartResponse;
import com.example.ecommerceapp.models.interfaces.SessionAPI;
import com.example.ecommerceapp.models.services.SessionService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SessionService {
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("signin_info", MODE_PRIVATE);
        SystemClock.sleep(3000);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (sharedPreferences.getInt("id", -1) == -1) {
            Intent signUpIntent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(signUpIntent);
            finish();
        } else {
            doCheckSession(sharedPreferences.getString("token",""));
//            Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
//            startActivity(homeIntent);
//            finish();
        }
    }

    @Override
    public void doCheckSession(String token) {
        SessionAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(SessionAPI.class);
        Call<DeleteCartResponse> call = api.session(token);
        call.enqueue(new Callback<DeleteCartResponse>() {
            @Override
            public void onResponse(Call<DeleteCartResponse> call, Response<DeleteCartResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                        finish();
                    } else {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear().apply();
                        Intent homeIntent = new Intent(MainActivity.this, SignUpActivity.class);
                        startActivity(homeIntent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteCartResponse> call, Throwable t) {
                String error = t.getMessage();
                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                Intent homeIntent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }
}
