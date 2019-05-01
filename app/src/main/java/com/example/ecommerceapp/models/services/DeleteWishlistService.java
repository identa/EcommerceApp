package com.example.ecommerceapp.models.services;

import android.content.Context;

public interface DeleteWishlistService {
    void doDeleteWishlist(int pid, int uid, int position, Context context);
}
