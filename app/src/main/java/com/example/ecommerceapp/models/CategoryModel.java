package com.example.ecommerceapp.models;

public class CategoryModel {
    private String iconLink;

    private String name;

    public CategoryModel(String iconLink, String name) {
        this.iconLink = iconLink;
        this.name = name;
    }

    public String getIconLink() {
        return iconLink;
    }

    public void setIconLink(String iconLink) {
        this.iconLink = iconLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
