package com.example.ecommerceapp.models.services;

import android.util.Log;

import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.requests.SignInRequest;
import com.example.ecommerceapp.models.entities.responses.SignInResponse;
import com.example.ecommerceapp.models.interfaces.SignInAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInServiceImpl implements SignInService {
    @Override
    public void doSignIn(SignInRequest request) {
        SignInAPI api = RetrofitClient.getClient(BaseURLConst.BASE_URL).create(SignInAPI.class);
        Call<SignInResponse> call = api.signIn(request);
        call.enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                    if (response.code() == 200)
                        Log.d("Id: ", response.body().getFirstName());
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {

            }
        });
    }
}
