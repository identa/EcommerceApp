package com.example.ecommerceapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.models.WishlistModel;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {
    private List<WishlistModel> wishlistModelList;

    public WishlistAdapter(List<WishlistModel> wishlistModelList) {
        this.wishlistModelList = wishlistModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String resource = wishlistModelList.get(i).getProductImage();
        String title = wishlistModelList.get(i).getProductTitle();
        double price = wishlistModelList.get(i).getProductPrice();
        double cuttedPrice = wishlistModelList.get(i).getCuttedPrice();
        double rating = wishlistModelList.get(i).getRating();
        int totalRatings = wishlistModelList.get(i).getTotalRatings();
        viewHolder.setData(resource, title, rating, totalRatings, price, cuttedPrice);
    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView producImage;
        private TextView productTitle;
        private TextView rating;
        private TextView totalRatings;
        private View priceCut;
        private TextView productPrice;
        private TextView cuttedPrice;
        private ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            producImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            rating = itemView.findViewById(R.id.tv_product_rating_miniview);
            totalRatings = itemView.findViewById(R.id.total_ratings);
            priceCut = itemView.findViewById(R.id.price_cut);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            deleteBtn = itemView.findViewById(R.id.delete_btn);
        }

        private void setData(String resource, String title, double avgRating, int totalRatingNo, double price, double cuttedPriceText){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.steakhouse)).into(producImage);
            productTitle.setText(title);
            rating.setText(String.format("%s", avgRating));
            totalRatings.setText(String.format("(%d) ratings", totalRatingNo));
            productPrice.setText(String.format("$%s", price));
            cuttedPrice.setText(String.format("$%s", cuttedPriceText));

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), "Delete item successfully", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
