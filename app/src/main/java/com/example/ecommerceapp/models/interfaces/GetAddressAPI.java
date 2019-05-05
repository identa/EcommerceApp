package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.responses.GetAddressResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetAddressAPI {
    @GET("/api/public/address/{id}")
    Call<GetAddressResponse> getAddress(@Path("id") int id);
}
