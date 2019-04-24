package com.example.ecommerceapp.models.services;

import android.content.Context;

public interface DeleteCartService {
    void doDeleteCart(int id, int position, Context context);
    void doEditCart(int id, int quantity, int position, Context context);
}
