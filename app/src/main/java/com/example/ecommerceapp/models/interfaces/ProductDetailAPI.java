package com.example.ecommerceapp.models.interfaces;

import com.example.ecommerceapp.models.entities.responses.AddCartResponse;
import com.example.ecommerceapp.models.entities.responses.AddWishlistResponse;
import com.example.ecommerceapp.models.entities.responses.DeleteCartResponse;
import com.example.ecommerceapp.models.entities.responses.ProductDetailResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductDetailAPI {
    @GET("/api/public/product/{pid}/{uid}")
    Call<ProductDetailResponse> getProductDetail(@Path("pid") int pid, @Path("uid") int uid);

    @GET("/api/public/cart/add/{pid}/{uid}")
    Call<AddCartResponse> addToCart(@Path("pid") int pid, @Path("uid") int uid);

    @POST("/api/public/wishlist/{pid}/{uid}")
    Call<AddWishlistResponse> addToWishlist(@Path("pid") int pid, @Path("uid") int uid);

    @DELETE("/api/public/wishlist/{pid}/{uid}")
    Call<DeleteCartResponse> deleteWishlist(@Path("pid") int pid, @Path("uid") int uid);
}
