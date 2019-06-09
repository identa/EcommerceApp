package com.example.ecommerceapp.models.services;

public interface ProductDetailService {
    void doGetProductDetail(int pid, int uid);
    void doAddToCart(int pid, int uid);
    void doAddToWishlist(int pid, int uid);
    void doDeleteWishlist(int pid, int uid);
    void doGetRating(int pid, int uid);
}