package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.responses.DeleteCartResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Path;

public interface DeleteCartAPI {
    @DELETE("/api/public/cart/delete/{id}")
    Call<DeleteCartResponse> deleteCart(@Path("id") int id);
}
