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
import com.example.ecommerceapp.CategoryActivity;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.ViewAllActivity;
import com.example.ecommerceapp.models.CategoryModel;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<CategoryModel> categoryModelList;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cat_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder viewHolder, int position) {
        int id = categoryModelList.get(position).getId();
        String icon = categoryModelList.get(position).getIconLink();
        String name = categoryModelList.get(position).getName();
        viewHolder.setCat(name, id);
        viewHolder.setCatIcon(icon);
    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView catIcon;
        private TextView catName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catIcon = itemView.findViewById(R.id.cat_icon);
            catName = itemView.findViewById(R.id.cat_name);
        }

        private void setCatIcon(String icon) {
            if (!icon.equals("null")) {
                Glide.with(itemView.getContext()).load(icon).apply(new RequestOptions().placeholder(R.mipmap.steakhouse)).into(catIcon);
            }
        }

        private void setCat(final String name, final int id){
            catName.setText(name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (position != 0) {
                        Intent catIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        catIntent.putExtra("title", name);
                        catIntent.putExtra("layout_code", 3);
                        catIntent.putExtra("cat_id", id);
                        itemView.getContext().startActivity(catIntent);
//                    }
                }
            });
        }
    }
}
