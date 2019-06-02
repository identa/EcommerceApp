package com.example.ecommerceapp.models.services;

import com.paypal.android.sdk.payments.PaymentConfirmation;

public interface AddOrderService {
    void doAddOrder(int id);
    void getAddress(int id);
    void buyNow(int id);
}
