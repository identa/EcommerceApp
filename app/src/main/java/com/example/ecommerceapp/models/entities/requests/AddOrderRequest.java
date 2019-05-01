package com.example.ecommerceapp.models.entities.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddOrderRequest {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("price")
    @Expose
    private double price;

    @SerializedName("cuttedPrice")
    @Expose
    private double cuttedPrice;

    @SerializedName("quantity")
    @Expose
    private int quantity;

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

    public double getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(double cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
