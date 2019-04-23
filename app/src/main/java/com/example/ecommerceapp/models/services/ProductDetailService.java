package com.example.ecommerceapp.models.services;

public interface ProductDetailService {
    void doGetProductDetail(int pid, int uid);
    void doAddToCart(int pid, int uid);
}