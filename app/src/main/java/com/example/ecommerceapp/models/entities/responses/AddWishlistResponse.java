package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddWishlistResponse extends GeneralResponse{
    @SerializedName("data")
    @Expose
    private AddWishlistData data;

    public AddWishlistData getData() {
        return data;
    }

    public void setData(AddWishlistData data) {
        this.data = data;
    }
}
