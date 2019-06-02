package com.example.ecommerceapp;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
    private CartAdapter cartAdapter;
    private Button continueBtn;
    private Dialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("signin_info", Context.MODE_PRIVATE);
        cartItemModelList = new ArrayList<>();
        doGetCart(sharedPreferences.getInt("id", 1));

        cartItemRecyclerView = view.findViewById(R.id.cart_item_recycler_view);
        continueBtn = view.findViewById(R.id.cart_continue_btn);

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecyclerView.setLayoutManager(layoutManager);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliveryActivity.cartItemModelList = cartItemModelList;
                Intent deliveryIntent = new Intent(getContext(), DeliveryActivity.class);
                getContext().startActivity(deliveryIntent);
            }
        });

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
                    if (response.body().getStatus().equals("SUCCESS")) {
                        for (CartList data : response.body().getData().getCartData()) {
                            cartItemModelList.add(new CartItemModel(0,
                                    data.getId(),
                                    data.getImageURL(),
                                    data.getName(),
                                    data.getPrice(),
                                    data.getCuttedPrice(),
                                    data.getQuantity(),
                                    data.getLimit()));
                        }

                        if (response.body().getData().getCartData().size() != 0) {
                            cartItemModelList.add(new CartItemModel(1,
                                    response.body().getData().getTotalItem(),
                                    response.body().getData().getTotalPrice(),
                                    response.body().getData().getItemAmount()));
                        }
                        cartAdapter = new CartAdapter(cartItemModelList, false);
                        cartItemRecyclerView.setAdapter(cartAdapter);
                        cartAdapter.notifyDataSetChanged();
                        loadingDialog.dismiss();
                    }else if (response.body().getStatus().equals("FAILED")){
                        Toast.makeText(getContext(), response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(),Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            }
        });
    }
}
