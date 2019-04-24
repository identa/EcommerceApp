package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditCartData {
    @SerializedName("totalPrice")
    @Expose
    private double totalPrice;

    @SerializedName("totalItem")
    @Expose
    private int totalItem;

    @SerializedName("itemAmount")
    @Expose
    private int itemAmount;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("price")
    @Expose
    private double price;

    @SerializedName("discount")
    @Expose
    private int discount;

    @SerializedName("quantity")
    @Expose
    private int quantity;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
