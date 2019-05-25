package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.requests.UpdateInfoRequest;
import com.example.ecommerceapp.models.entities.requests.UpdatePassRequest;
import com.example.ecommerceapp.models.entities.responses.UpdateInfoResponse;
import com.example.ecommerceapp.models.entities.responses.UpdatePassResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UpdateInfoAPI {
    @POST("/api/public/info/{id}")
    Call<UpdateInfoResponse> updateInfo(@Path ("id") int id, @Body UpdateInfoRequest request);

    @POST("/api/public/pass/{id}")
    Call<UpdatePassResponse> updatePass(@Path ("id") int id, @Body UpdatePassRequest request);
}
