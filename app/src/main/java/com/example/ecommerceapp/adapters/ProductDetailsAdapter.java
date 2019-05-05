package com.example.ecommerceapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ecommerceapp.ProductDescriptionFragment;

public class ProductDetailsAdapter extends FragmentPagerAdapter {
    private int totalTabs;
    private String productDesc;
    private String productOtherDetails;

    public ProductDetailsAdapter(FragmentManager fm, int totalTabs, String productDesc, String productOtherDetails) {
        super(fm);
        this.totalTabs = totalTabs;
        this.productDesc = productDesc;
        this.productOtherDetails = productOtherDetails;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                ProductDescriptionFragment productDescriptionFragment1 = new ProductDescriptionFragment();
                productDescriptionFragment1.body = productDesc;
                return productDescriptionFragment1;
            case 1:
                ProductDescriptionFragment productDescriptionFragment2 = new ProductDescriptionFragment();
                productDescriptionFragment2.body = productOtherDetails;
                return productDescriptionFragment2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
