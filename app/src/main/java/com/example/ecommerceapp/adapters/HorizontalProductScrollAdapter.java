package com.example.ecommerceapp.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerceapp.ProductDetailActivity;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.models.HorizontalProductScrollModel;

import java.util.List;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollAdapter.ViewHolder> {
    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public HorizontalProductScrollAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @NonNull
    @Override
    public HorizontalProductScrollAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollAdapter.ViewHolder viewHolder, int position) {
        String resource = horizontalProductScrollModelList.get(position).getImageLink();
        String title = horizontalProductScrollModelList.get(position).getTitle();
        String desc = horizontalProductScrollModelList.get(position).getDesc();
        double price = horizontalProductScrollModelList.get(position).getPrice();
        int id = horizontalProductScrollModelList.get(position).getId();

        viewHolder.setProductImage(resource);
        viewHolder.setProductTitle(title);
        viewHolder.setProductDesc(desc);
        viewHolder.setProductPrice(price);
        viewHolder.setID(id);
    }

    @Override
    public int getItemCount() {
        return horizontalProductScrollModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView productDesc;
        private TextView productPrice;
        private int productID;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.h_s_product_image);
            productTitle = itemView.findViewById(R.id.h_s_product_title);
            productDesc = itemView.findViewById(R.id.h_s_product_desc);
            productPrice = itemView.findViewById(R.id.h_s_product_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailIntent = new Intent(itemView.getContext(), ProductDetailActivity.class);
                    productDetailIntent.putExtra("id", productID);
                    itemView.getContext().startActivity(productDetailIntent);
                }
            });
        }

        private void setProductImage(String resource){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.steakhouse)).into(productImage);
        }

        private void setProductTitle(String title){
            productTitle.setText(title);
        }

        private void setProductDesc(String desc){
            productDesc.setText(desc);
        }

        private void setProductPrice(double price){
            productPrice.setText(String.format("$%s", price));
        }

        private void setID(int id){
            productID = id;
        }
    }
}
