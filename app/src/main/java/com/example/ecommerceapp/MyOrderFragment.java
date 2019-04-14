package com.example.ecommerceapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerceapp.adapters.MyOrderAdapter;
import com.example.ecommerceapp.models.MyOrderItemModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderFragment extends Fragment {


    public MyOrderFragment() {
        // Required empty public constructor
    }

    private RecyclerView myOrderRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);
        myOrderRecyclerView = view.findViewById(R.id.my_order_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrderRecyclerView.setLayoutManager(layoutManager);

        List<MyOrderItemModel> myOrderItemModelList = new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.mipmap.steakhouse, 2,"Samsung Galaxy S10", "Delivered on Mon, 15th Jan 2018"));
        myOrderItemModelList.add(new MyOrderItemModel(R.mipmap.steakhouse, 2,"Samsung Galaxy S10", "Delivered on Mon, 15th Jan 2018"));
        myOrderItemModelList.add(new MyOrderItemModel(R.mipmap.steakhouse, 2,"Samsung Galaxy S10", "Delivered on Mon, 15th Jan 2018"));
        myOrderItemModelList.add(new MyOrderItemModel(R.mipmap.steakhouse, 2,"Samsung Galaxy S10", "Cancelled"));
        myOrderItemModelList.add(new MyOrderItemModel(R.mipmap.steakhouse, 2,"Samsung Galaxy S10", "Delivered on Mon, 15th Jan 2018"));

        MyOrderAdapter myOrderAdapter = new MyOrderAdapter(myOrderItemModelList);
        myOrderRecyclerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();

        return view;
    }

}
