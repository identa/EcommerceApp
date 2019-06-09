package com.example.ecommerceapp.models.entities.requests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RatingRequest {
    @SerializedName("rating")
    @Expose
    private int rating;

    @SerializedName("comment")
    @Expose
    private String comment;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}