package com.example.ecommerceapp.adapters;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class ProductImageAdapter extends PagerAdapter {
    private List<Integer> productNums;

    public ProductImageAdapter(List<Integer> productNum) {
        this.productNums = productNum;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView productImage = new ImageView(container.getContext());
        productImage.setImageResource(productNums.get(position));
        container.addView(productImage,0);
        return productImage;
    }

    @Override
    public int getCount() {
        return productNums.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }


}
