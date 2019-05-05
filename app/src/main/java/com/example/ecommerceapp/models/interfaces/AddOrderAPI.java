package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.requests.AddOrderReq;
import com.example.ecommerceapp.models.entities.requests.AddOrderRequest;
import com.example.ecommerceapp.models.entities.responses.AddOrderResponse;
import com.example.ecommerceapp.models.entities.responses.GetAddressResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AddOrderAPI {
    @POST("/api/public/order/{id}")
    Call<AddOrderResponse> addOrder(@Path("id") int id, @Body AddOrderReq request);

    @GET("/api/public/address/{id}")
    Call<GetAddressResponse> getAddress(@Path("id") int id);
}
