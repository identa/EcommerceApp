package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HorizontalViewAllData {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("productImage")
    @Expose
    private String productImage;

    @SerializedName("productTitle")
    @Expose
    private String productTitle;

    @SerializedName("rating")
    @Expose
    private double rating;

    @SerializedName("totalRatings")
    @Expose
    private int totalRatings;

    @SerializedName("productPrice")
    @Expose
    private double productPrice;

    @SerializedName("cuttedPrice")
    @Expose
    private double cuttedPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(int totalRatings) {
        this.totalRatings = totalRatings;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(double cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }
}
