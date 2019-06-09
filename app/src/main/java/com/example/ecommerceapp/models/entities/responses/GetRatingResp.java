package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetRatingResp {
    @SerializedName("avg")
    @Expose
    private Float avg;

    @SerializedName("rating")
    @Expose
    private Float rating;

    @SerializedName("cmt")
    @Expose
    private String cmt;

    @SerializedName("ratings")
    @Expose
    private List<GetRatingData> ratings;

    public Float getAvg() {
        return avg;
    }

    public void setAvg(Float avg) {
        this.avg = avg;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public List<GetRatingData> getRatings() {
        return ratings;
    }

    public void setRatings(List<GetRatingData> ratings) {
        this.ratings = ratings;
    }
}
