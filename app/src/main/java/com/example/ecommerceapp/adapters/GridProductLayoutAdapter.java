package com.example.ecommerceapp.adapters;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerceapp.ProductDetailActivity;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.models.HorizontalProductScrollModel;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {
    List<HorizontalProductScrollModel> modelList;
    public GridProductLayoutAdapter(List<HorizontalProductScrollModel> modelList) {
        this.modelList = modelList;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;
        if (convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_item_layout, null);
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailsIntent = new Intent(parent.getContext(), ProductDetailActivity.class);
                    productDetailsIntent.putExtra("id", modelList.get(position).getId());
                    parent.getContext().startActivity(productDetailsIntent);
                }
            });

            ImageView productImage = view.findViewById(R.id.h_s_product_image);
            TextView productTitle = view.findViewById(R.id.h_s_product_title);
            TextView productDesc = view.findViewById(R.id.h_s_product_desc);
            TextView productPrice = view.findViewById(R.id.h_s_product_price);

            Glide.with(parent.getContext()).load(modelList.get(position).getImageLink()).apply(new RequestOptions().placeholder(R.mipmap.steakhouse)).into(productImage);
            productTitle.setText(modelList.get(position).getTitle());
            productDesc.setText(modelList.get(position).getDesc());
            productPrice.setText(String.format("$%s", modelList.get(position).getPrice()));
        }else {
            view = convertView;
        }
        return view;
    }

}
