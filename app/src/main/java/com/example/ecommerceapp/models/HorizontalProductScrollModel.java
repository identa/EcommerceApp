package com.example.ecommerceapp.models;

public class HorizontalProductScrollModel {
    private int num;

    private String title;

    private String desc;

    private String price;

    public HorizontalProductScrollModel(int num, String title, String desc, String price) {
        this.num = num;
        this.title = title;
        this.desc = desc;
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
