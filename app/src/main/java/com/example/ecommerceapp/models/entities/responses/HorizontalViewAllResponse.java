package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HorizontalViewAllResponse extends GeneralResponse {
    @SerializedName("data")
    @Expose
    private List<HorizontalViewAllData> data;

    public List<HorizontalViewAllData> getData() {
        return data;
    }

    public void setData(List<HorizontalViewAllData> data) {
        this.data = data;
    }
}
