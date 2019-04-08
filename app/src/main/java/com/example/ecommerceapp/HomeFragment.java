package com.example.ecommerceapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerceapp.adapters.CategoryAdapter;
import com.example.ecommerceapp.models.CategoryModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView catRecyclerView;
    private CategoryAdapter categoryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        catRecyclerView = view.findViewById(R.id.cat_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        catRecyclerView.setLayoutManager(layoutManager);

        List<CategoryModel> categoryModelList = new ArrayList<>();
        categoryModelList.add(new CategoryModel("a","a"));
        categoryModelList.add(new CategoryModel("a","a"));
        categoryModelList.add(new CategoryModel("a","a"));
        categoryModelList.add(new CategoryModel("a","a"));
        categoryModelList.add(new CategoryModel("a","a"));
        categoryModelList.add(new CategoryModel("a","a"));
        categoryModelList.add(new CategoryModel("a","a"));
        categoryModelList.add(new CategoryModel("a","a"));

        categoryAdapter = new CategoryAdapter(categoryModelList);
        catRecyclerView.setAdapter(categoryAdapter);
        categoryAdapter.notifyDataSetChanged();

        return view;
    }

}
