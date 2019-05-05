package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.requests.AddressRequest;
import com.example.ecommerceapp.models.entities.responses.GetAddressResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AddAddressAPI {
    @POST("/api/public/address/{id}")
    Call<GetAddressResponse> addAddress(@Path("id") int id, @Body AddressRequest request);

    @PUT("/api/public/address/{id}")
    Call<GetAddressResponse> editAddress(@Path("id") int id, @Body AddressRequest request);
}
