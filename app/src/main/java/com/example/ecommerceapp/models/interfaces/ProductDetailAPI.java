package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.responses.ProductDetailResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductDetailAPI {
    @GET("/api/public/product/{id}")
    Call<ProductDetailResponse> getProductDetail(@Path("id") int id);
}
