package com.example.ecommerceapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.models.OrderDetailsModel;

import java.util.List;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder> {
    private List<OrderDetailsModel> orderDetailsModelList;

    public OrderDetailsAdapter(List<OrderDetailsModel> orderDetailsModelList) {
        this.orderDetailsModelList = orderDetailsModelList;
    }

    @NonNull
    @Override
    public OrderDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_details_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsAdapter.ViewHolder viewHolder, int i) {
        int id = orderDetailsModelList.get(i).getId();
        String name = orderDetailsModelList.get(i).getName();
        String image = orderDetailsModelList.get(i).getImage();
        double price = orderDetailsModelList.get(i).getPrice();
        int quantity = orderDetailsModelList.get(i).getQuantity();
        viewHolder.setData(id, name, image, price, quantity);
    }

    @Override
    public int getItemCount() {
        return orderDetailsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView image;
        private TextView price;
        private TextView quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_title);
            image = itemView.findViewById(R.id.product_image);
            price = itemView.findViewById(R.id.product_price);
            quantity = itemView.findViewById(R.id.product_quantity);
        }

        private void setData(int id, String nameText, String imageText, double priceText, int quantityText){
            name.setText(nameText);
            Glide.with(itemView.getContext()).load(imageText).apply(new RequestOptions().placeholder(R.mipmap.steakhouse)).into(image);
            price.setText(String.format("$%s", priceText));
            quantity.setText("Qty: " + quantityText);
        }
    }
}
