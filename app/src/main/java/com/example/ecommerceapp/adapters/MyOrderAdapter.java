package com.example.ecommerceapp.adapters;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ecommerceapp.OrderDetailsActivity;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.models.MyOrderItemModel;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {
    private List<MyOrderItemModel> myOrderItemModelList;

    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_order_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder viewHolder, int i) {
        int id = myOrderItemModelList.get(i).getId();
        int resource = myOrderItemModelList.get(i).getProductImage();
        int rating = myOrderItemModelList.get(i).getRating();
        String title = myOrderItemModelList.get(i).getProductTitle();
        String deliveryDate = myOrderItemModelList.get(i).getDeliveryStatus();
        viewHolder.setData(id, resource, title, deliveryDate, rating);
    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private ImageView orderIndicator;
        private TextView productTitle;
        private TextView deliveryStatus;

        private LinearLayout rateNowContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            orderIndicator = itemView.findViewById(R.id.order_indicator);
            deliveryStatus = itemView.findViewById(R.id.order_delivered_date);
//            rateNowContainer = itemView.findViewById(R.id.rating_now_container);
        }

        private void setData(final int id, int resource, String title, String deliveredDate, int rating) {
            productImage.setImageResource(resource);
            productTitle.setText(title);
            if (deliveredDate.equals("Cancelled")) {
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.colorPrimary)));
            } else {
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.successGreen)));
            }
            deliveryStatus.setText(deliveredDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orderDetailsIntent = new Intent(itemView.getContext(), OrderDetailsActivity.class);
                    orderDetailsIntent.putExtra("orderID", id);
                    itemView.getContext().startActivity(orderDetailsIntent);
                }
            });

//            setRating(rating);
//            for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
//                final int starPosition = x;
//                rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        setRating(starPosition);
//                    }
//                });
//            }
        }

//        private void setRating(int starPosition) {
//            for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
//                ImageView starBtn = (ImageView) rateNowContainer.getChildAt(x);
//                starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
//                if (x <= starPosition) {
//                    starBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
//                }
//            }
//        }
    }
}
