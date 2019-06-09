package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRatingData {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("rating")
    @Expose
    private float rating;

    @SerializedName("cmt")
    @Expose
    private String cmt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }
}
