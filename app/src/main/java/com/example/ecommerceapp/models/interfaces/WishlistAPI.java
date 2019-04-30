package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.responses.GetWishlistResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface WishlistAPI {
    @GET("/api/public/wishlist/{id}")
    Call<GetWishlistResponse> getWishlist(@Path("id")int id);
}
