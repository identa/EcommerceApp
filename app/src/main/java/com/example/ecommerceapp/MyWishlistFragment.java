package com.example.ecommerceapp;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecommerceapp.adapters.WishlistAdapter;
import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.WishlistModel;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.responses.GetWishlistData;
import com.example.ecommerceapp.models.entities.responses.GetWishlistResponse;
import com.example.ecommerceapp.models.entities.responses.HorizontalViewAllData;
import com.example.ecommerceapp.models.entities.responses.HorizontalViewAllResponse;
import com.example.ecommerceapp.models.interfaces.WishlistAPI;
import com.example.ecommerceapp.models.services.WishlistService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyWishlistFragment extends Fragment implements WishlistService {


    public MyWishlistFragment() {
        // Required empty public constructor
    }

    private RecyclerView wishlistRecyclerView;
    private WishlistAdapter wishlistAdapter;
    private List<WishlistModel> wishlistModelList;

    private SharedPreferences sharedPreferences;
    private Dialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wishlist, container, false);
        wishlistRecyclerView = view.findViewById(R.id.my_wishlist_recycler_view);

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlistRecyclerView.setLayoutManager(linearLayoutManager);

        sharedPreferences = getActivity().getSharedPreferences("signin_info", Context.MODE_PRIVATE);
        wishlistModelList = new ArrayList<>();
        doGetWishlist(sharedPreferences.getInt("id", 1));

        wishlistAdapter = new WishlistAdapter(wishlistModelList, true);
        wishlistRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void doGetWishlist(int id) {
        WishlistAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(WishlistAPI.class);
        Call<GetWishlistResponse> call = api.getWishlist(id);
        call.enqueue(new Callback<GetWishlistResponse>() {
            @Override
            public void onResponse(Call<GetWishlistResponse> call, Response<GetWishlistResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        for (GetWishlistData data : response.body().getData()) {
                            wishlistModelList.add(new WishlistModel(data.getId(),
                                    data.getImageURL(),
                                    data.getName(),
                                    data.getRating(),
                                    data.getTotalRatings(),
                                    data.getPrice(),
                                    data.getCuttedPrice()));
                        }
                        wishlistAdapter.notifyDataSetChanged();
                        loadingDialog.dismiss();
                    } else if (response.body().getStatus().equals("FAILED")) {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetWishlistResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            }
        });
    }
}
