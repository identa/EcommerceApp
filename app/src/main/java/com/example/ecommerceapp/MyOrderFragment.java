package com.example.ecommerceapp;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ecommerceapp.adapters.MyOrderAdapter;
import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.MyOrderItemModel;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.responses.GetOrderData;
import com.example.ecommerceapp.models.entities.responses.GetOrderResponse;
import com.example.ecommerceapp.models.interfaces.OrderAPI;
import com.example.ecommerceapp.models.services.OrderService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderFragment extends Fragment implements OrderService {


    public MyOrderFragment() {
        // Required empty public constructor
    }

    private RecyclerView myOrderRecyclerView;
    private List<MyOrderItemModel> myOrderItemModelList;
    private MyOrderAdapter myOrderAdapter;

    private SharedPreferences sharedPreferences;
    private Dialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);
        myOrderRecyclerView = view.findViewById(R.id.my_order_recycler_view);

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        sharedPreferences = getActivity().getSharedPreferences("signin_info", Context.MODE_PRIVATE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrderRecyclerView.setLayoutManager(layoutManager);

        myOrderItemModelList = new ArrayList<>();
        doGetOrder(sharedPreferences.getInt("id", 1));
        myOrderAdapter = new MyOrderAdapter(myOrderItemModelList);
        myOrderRecyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void doGetOrder(int id) {
        OrderAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(OrderAPI.class);
        Call<GetOrderResponse> call = api.getOrder(id);
        call.enqueue(new Callback<GetOrderResponse>() {
            @Override
            public void onResponse(Call<GetOrderResponse> call, Response<GetOrderResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        for (GetOrderData data : response.body().getData()) {
                            myOrderItemModelList.add(new MyOrderItemModel(data.getId(),R.mipmap.steakhouse, 2, "Order no " + data.getId(), "Order on " + data.getCreateAt(), data.getMethod()));
                        }
                        myOrderAdapter.notifyDataSetChanged();
                        loadingDialog.dismiss();
                    } else if (response.body().getStatus().equals("FAILED")) {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                }
                }
            }

            @Override
            public void onFailure(Call<GetOrderResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            }
        });
    }
}
