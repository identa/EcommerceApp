package com.example.ecommerceapp.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.ecommerceapp.ProductDetailActivity;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.WishlistModel;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.responses.DeleteCartResponse;
import com.example.ecommerceapp.models.interfaces.DeleteWishlistAPI;
import com.example.ecommerceapp.models.services.DeleteWishlistService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> implements DeleteWishlistService {
    private List<WishlistModel> wishlistModelList;
    private boolean wishlist;

    public WishlistAdapter(List<WishlistModel> wishlistModelList, boolean wishlist) {
        this.wishlistModelList = wishlistModelList;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.wishlist_item_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        int id = wishlistModelList.get(i).getId();
        String resource = wishlistModelList.get(i).getProductImage();
        String title = wishlistModelList.get(i).getProductTitle();
        double price = wishlistModelList.get(i).getProductPrice();
        double cuttedPrice = wishlistModelList.get(i).getCuttedPrice();
        double rating = wishlistModelList.get(i).getRating();
        int totalRatings = wishlistModelList.get(i).getTotalRatings();
        viewHolder.setData(id, resource, title, rating, totalRatings, price, cuttedPrice, i);
    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }

    @Override
    public void doDeleteWishlist(int pid, int uid, final int position, final Context context) {
        DeleteWishlistAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(DeleteWishlistAPI.class);
        Call<DeleteCartResponse> call = api.deleteWishlist(pid, uid);
        call.enqueue(new Callback<DeleteCartResponse>() {
            @Override
            public void onResponse(Call<DeleteCartResponse> call, Response<DeleteCartResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        wishlistModelList.remove(position);
                        Toast.makeText(context, "Delete wishlist successfully!", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<DeleteCartResponse> call, Throwable t) {

            }
        });
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
        private int productID;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            producImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            rating = itemView.findViewById(R.id.tv_product_rating_miniview);
            totalRatings = itemView.findViewById(R.id.total_ratings);
            priceCut = itemView.findViewById(R.id.price_cut);
            productPrice = itemView.findViewById(R.id.product_price);
            cuttedPrice = itemView.findViewById(R.id.cutted_price);
            deleteBtn = itemView.findViewById(R.id.delete_btn);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent productDetailIntent = new Intent(itemView.getContext(), ProductDetailActivity.class);
                    ProductDetailActivity.productID = productID;
                    itemView.getContext().startActivity(productDetailIntent);
                }
            });
        }

        private void setData(int id, String resource, String title, double avgRating, int totalRatingNo, double price, double cuttedPriceText, final int position){
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.mipmap.steakhouse)).into(producImage);
            productTitle.setText(title);
            rating.setText(String.format("%s", avgRating));
            totalRatings.setText(String.format("(%d) ratings", totalRatingNo));
            productPrice.setText(String.format("$%s", price));
            cuttedPrice.setText(String.format("$%s", cuttedPriceText));
            productID = id;

            if (wishlist){
                deleteBtn.setVisibility(View.VISIBLE);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doDeleteWishlist(productID,2, position, itemView.getContext());
                    }
                });
            }else {
                deleteBtn.setVisibility(View.GONE);
            }
        }
    }
}
