package com.example.ecommerceapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerceapp.adapters.CartAdapter;
import com.example.ecommerceapp.models.CartItemModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment {


    public MyCartFragment() {
        // Required empty public constructor
    }

    private RecyclerView cartItemRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        cartItemRecyclerView = view.findViewById(R.id.cart_item_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecyclerView.setLayoutManager(layoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0, R.mipmap.steakhouse, "Samsung Galaxy s10", 2000, 1000, 3));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.steakhouse, "Samsung Galaxy s10", 2000, 1000, 3));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.steakhouse, "Samsung Galaxy s10", 2000, 1000, 3));
        cartItemModelList.add(new CartItemModel(0, R.mipmap.steakhouse, "Samsung Galaxy s10", 2000, 1000, 3));

        cartItemModelList.add(new CartItemModel(1, "Price (4 items)", 2000, 2000));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        cartItemRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        return view;
    }

}
