package com.example.ecommerceapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.models.GetRatingModel;

import java.util.List;

public class GetRatingAdapter extends RecyclerView.Adapter<GetRatingAdapter.ViewHolder> {
    private List<GetRatingModel> getRatingModelList;

    public GetRatingAdapter(List<GetRatingModel> getRatingModelList) {
        this.getRatingModelList = getRatingModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.get_rating_item_layout, viewGroup, false);
        return new GetRatingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String name = getRatingModelList.get(i).getName();
        float rating = getRatingModelList.get(i).getRating();
        String cmt = getRatingModelList.get(i).getCmt();
        viewHolder.setGetRating(name, rating, cmt);
    }

    @Override
    public int getItemCount() {
        return getRatingModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView ratingName;
        private TextView rate;
        private TextView ratingComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ratingName = itemView.findViewById(R.id.rating_item_name);
            ratingComment = itemView.findViewById(R.id.rating_item_cmt);
            rate = itemView.findViewById(R.id.rate_item);
        }

        private void setGetRating(String name, float rating, String cmt){
            ratingName.setText(name);
            ratingComment.setText(cmt);
            rate.setText(String.format("%s", rating));
        }
    }
}
