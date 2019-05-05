package com.example.ecommerceapp.adapters;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerceapp.R;

import java.util.List;

public class ProductImageAdapter extends PagerAdapter {
    private List<String> productImages;

    public ProductImageAdapter(List<String> productImages) {
        this.productImages = productImages;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView productImage = new ImageView(container.getContext());
//        productImage.setImageResource(productNums.get(position));
        Glide.with(container.getContext()).load(productImages.get(position)).apply(new RequestOptions().placeholder(R.mipmap.steakhouse)).into(productImage);
        container.addView(productImage, 0);
        return productImage;
    }

    @Override
    public int getCount() {
        return productImages.size();
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
