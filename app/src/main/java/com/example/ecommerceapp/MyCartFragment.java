package com.example.ecommerceapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerceapp.adapters.CartAdapter;
import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.CartItemModel;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.responses.CartList;
import com.example.ecommerceapp.models.entities.responses.CartResponse;
import com.example.ecommerceapp.models.interfaces.CartAPI;
import com.example.ecommerceapp.models.services.CartService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment implements CartService {


    public MyCartFragment() {
        // Required empty public constructor
    }

    private RecyclerView cartItemRecyclerView;
    private List<CartItemModel> cartItemModelList;
    public static CartAdapter cartAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        cartItemModelList = new ArrayList<>();
        doGetCart(2);

        cartItemRecyclerView = view.findViewById(R.id.cart_item_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecyclerView.setLayoutManager(layoutManager);


//        cartItemModelList.add(new CartItemModel(0, R.mipmap.steakhouse, "Samsung Galaxy s10", 2000, 1000, 3));
//        cartItemModelList.add(new CartItemModel(0, R.mipmap.steakhouse, "Samsung Galaxy s10", 2000, 1000, 3));
//        cartItemModelList.add(new CartItemModel(0, R.mipmap.steakhouse, "Samsung Galaxy s10", 2000, 1000, 3));
//        cartItemModelList.add(new CartItemModel(0, R.mipmap.steakhouse, "Samsung Galaxy s10", 2000, 1000, 3));


//        cartItemModelList.add(new CartItemModel(1, "Price (4 items)", 2000, 2000));

//        cartAdapter = new CartAdapter(cartItemModelList);
//        cartItemRecyclerView.setAdapter(cartAdapter);
//        cartAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void doGetCart(int id) {
        CartAPI api = RetrofitClient.getClient(BaseURLConst.BASE_URL).create(CartAPI.class);
        Call<CartResponse> call = api.getCart(id);
        call.enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")){
                        for (CartList data : response.body().getData().getCartData()){
                            cartItemModelList.add(new CartItemModel(0,
                                    data.getId(),
                                    data.getImageURL(),
                                    data.getName(),
                                    data.getPrice(),
                                    data.getCuttedPrice(),
                                    data.getQuantity()));
                        }
                        cartItemModelList.add(new CartItemModel(1,
                                response.body().getData().getTotalItem(),
                                response.body().getData().getTotalPrice(),
                                response.body().getData().getItemAmount()));
                        cartAdapter = new CartAdapter(cartItemModelList);
                        cartItemRecyclerView.setAdapter(cartAdapter);
                        cartAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
