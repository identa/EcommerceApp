package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartData {
    @SerializedName("totalPrice")
    @Expose
    private double totalPrice;

    @SerializedName("totalItem")
    @Expose
    private int totalItem;

    @SerializedName("itemAmount")
    @Expose
    private int itemAmount;

    @SerializedName("cartData")
    @Expose
    private List<CartList> cartData;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }

    public List<CartList> getCartData() {
        return cartData;
    }

    public void setCartData(List<CartList> cartData) {
        this.cartData = cartData;
    }
}
