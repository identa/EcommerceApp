package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartResponse extends GeneralResponse{
    @SerializedName("totalPrice")
    @Expose
    private double totalPrice;

    @SerializedName("totalItem")
    @Expose
    private int totalItem;

    @SerializedName("itemAmount")
    @Expose
    private int itemAmount;

    @SerializedName("data")
    @Expose
    private List<CartData> data;

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

    public List<CartData> getData() {
        return data;
    }

    public void setData(List<CartData> data) {
        this.data = data;
    }
}
