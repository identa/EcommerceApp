package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetWishlistData {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("imageURL")
    @Expose
    private String imageURL;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("rating")
    @Expose
    private double rating;

    @SerializedName("totalRatings")
    @Expose
    private int totalRatings;

    @SerializedName("price")
    @Expose
    private double price;

    @SerializedName("cuttedPrice")
    @Expose
    private double cuttedPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
