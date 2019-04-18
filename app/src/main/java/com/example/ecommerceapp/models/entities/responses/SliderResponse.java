package com.example.ecommerceapp.models.entities.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SliderResponse extends GeneralResponse {
    @SerializedName("data")
    @Expose
    private List<SliderData> data;

    public List<SliderData> getData() {
        return data;
    }

    public void setData(List<SliderData> data) {
        this.data = data;
    }
}
