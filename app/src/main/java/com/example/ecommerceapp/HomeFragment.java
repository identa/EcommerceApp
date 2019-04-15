package com.example.ecommerceapp;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.adapters.CategoryAdapter;
import com.example.ecommerceapp.adapters.GridProductLayoutAdapter;
import com.example.ecommerceapp.adapters.HomePageAdapter;
import com.example.ecommerceapp.adapters.HorizontalProductScrollAdapter;
import com.example.ecommerceapp.models.CategoryModel;
import com.example.ecommerceapp.models.HomePageModel;
import com.example.ecommerceapp.models.HorizontalProductScrollModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView catRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView testing;
    private List<CategoryModel> categoryModelList;

    private FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        catRecyclerView = view.findViewById(R.id.cat_recycler_view);

        List<SliderModel> sliderModelList = new ArrayList<>();
        sliderModelList.add(new SliderModel(R.mipmap.cancel));
        sliderModelList.add(new SliderModel(R.mipmap.email));
        sliderModelList.add(new SliderModel(R.mipmap.caution));
        sliderModelList.add(new SliderModel(R.mipmap.cancel));
        sliderModelList.add(new SliderModel(R.mipmap.email));
        sliderModelList.add(new SliderModel(R.mipmap.error));
        sliderModelList.add(new SliderModel(R.mipmap.plus));
        sliderModelList.add(new SliderModel(R.mipmap.email));
        sliderModelList.add(new SliderModel(R.mipmap.steakhouse));

        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));

        //homepage
        testing = view.findViewById(R.id.home_page_recycler_view);
        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        testing.setLayoutManager(testingLayoutManager);

        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add(new HomePageModel(0, sliderModelList));
        homePageModelList.add(new HomePageModel(1, "Deals of the Day", horizontalProductScrollModelList));
        homePageModelList.add(new HomePageModel(2, "Trendy", horizontalProductScrollModelList));

        HomePageAdapter homePageAdapter = new HomePageAdapter(homePageModelList);
        testing.setAdapter(homePageAdapter);
        homePageAdapter.notifyDataSetChanged();
        //homepage

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        catRecyclerView.setLayoutManager(layoutManager);
        categoryModelList = new ArrayList<>();

        categoryAdapter = new CategoryAdapter(categoryModelList);
        catRecyclerView.setAdapter(categoryAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot snapshot : task.getResult()) {
                                categoryModelList.add(new CategoryModel(snapshot.get("icon").toString(), snapshot.get("name").toString()));
                            }
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return view;
    }
}
