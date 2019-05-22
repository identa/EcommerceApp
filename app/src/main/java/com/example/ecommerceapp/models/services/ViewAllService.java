package com.example.ecommerceapp.models.services;

import com.example.ecommerceapp.models.entities.requests.SortRequest;


public interface ViewAllService {
    void doGetMostViewedProductAll();
    void doGetMostOrderedProductAll();
    void doSearch(String query);
    void doShowCat(int id);
    void doGetCat();
    void doSort(SortRequest request);
}
