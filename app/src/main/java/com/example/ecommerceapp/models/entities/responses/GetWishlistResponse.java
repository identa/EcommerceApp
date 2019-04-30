package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetWishlistResponse extends GeneralResponse{
    @SerializedName("data")
    @Expose
    private List<GetWishlistData> data;

    public List<GetWishlistData> getData() {
        return data;
    }

    public void setData(List<GetWishlistData> data) {
        this.data = data;
    }
}
