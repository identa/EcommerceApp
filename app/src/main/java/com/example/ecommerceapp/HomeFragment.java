package com.example.ecommerceapp;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.adapters.CategoryAdapter;
import com.example.ecommerceapp.adapters.HomePageAdapter;
import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.CategoryModel;
import com.example.ecommerceapp.models.HomePageModel;
import com.example.ecommerceapp.models.HorizontalProductScrollModel;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.responses.GetCatData;
import com.example.ecommerceapp.models.entities.responses.GetCatResponse;
import com.example.ecommerceapp.models.entities.responses.HomePageProductData;
import com.example.ecommerceapp.models.entities.responses.HomePageProductResponse;
import com.example.ecommerceapp.models.entities.responses.SliderData;
import com.example.ecommerceapp.models.entities.responses.SliderResponse;
import com.example.ecommerceapp.models.interfaces.HomePageAPI;
import com.example.ecommerceapp.models.services.HomePageService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomePageService {

    public HomeFragment() {
        // Required empty public constructor
    }

    private RecyclerView catRecyclerView;
    private CategoryAdapter categoryAdapter;
    private RecyclerView testing;
    private List<CategoryModel> categoryModelList;
    private List<SliderModel> sliderModelList;
    private List<HorizontalProductScrollModel> horizontalList, gridList;
    private ImageView noInternet;

    private HomePageAdapter homePageAdapter;

    private FirebaseFirestore firebaseFirestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        noInternet = view.findViewById(R.id.no_internet);

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){
            noInternet.setVisibility(View.GONE);

            catRecyclerView = view.findViewById(R.id.cat_recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            catRecyclerView.setLayoutManager(layoutManager);
            categoryModelList = new ArrayList<>();

            doGetCat();
            categoryAdapter = new CategoryAdapter(categoryModelList);
            catRecyclerView.setAdapter(categoryAdapter);
            categoryAdapter.notifyDataSetChanged();

            sliderModelList = new ArrayList<>();
            doGetSlider();

            horizontalList = new ArrayList<>();
            gridList = new ArrayList<>();

            doGetMostViewedProduct();

            doGetMostOrderedProduct();

            //homepage
            testing = view.findViewById(R.id.home_page_recycler_view);
            LinearLayoutManager testingLayoutManager = new LinearLayoutManager(getContext());
            testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            testing.setLayoutManager(testingLayoutManager);

            List<HomePageModel> homePageModelList = new ArrayList<>();
            homePageModelList.add(new HomePageModel(0, sliderModelList));
            homePageModelList.add(new HomePageModel(1, "Deals of the Day", horizontalList));
            homePageModelList.add(new HomePageModel(2, "Trendy", gridList));

            homePageAdapter = new HomePageAdapter(homePageModelList);
            testing.setAdapter(homePageAdapter);
            homePageAdapter.notifyDataSetChanged();
            //homepage

//            firebaseFirestore = FirebaseFirestore.getInstance();
//            firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                for (QueryDocumentSnapshot snapshot : task.getResult()) {
//                                    categoryModelList.add(new CategoryModel(snapshot.get("icon").toString(), snapshot.get("name").toString()));
//                                }
//                                categoryAdapter.notifyDataSetChanged();
//                            } else {
//                                String error = task.getException().getMessage();
//                                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });

        } else {
            Glide.with(this).load(R.mipmap.no_internet).into(noInternet);
            noInternet.setVisibility(View.VISIBLE);
        }


        return view;
    }

    @Override
    public void doGetSlider() {
        HomePageAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(HomePageAPI.class);
        Call<SliderResponse> call = api.getSlider();
        call.enqueue(new Callback<SliderResponse>() {
            @Override
            public void onResponse(Call<SliderResponse> call, Response<SliderResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        for (SliderData data : response.body().getData()){
                            sliderModelList.add(new SliderModel(data.getImageURL()));
                        }
                        homePageAdapter.notifyDataSetChanged();
                    } else if (response.body().getStatus().equals("FAILED")){
                    }
                }
            }

            @Override
            public void onFailure(Call<SliderResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    @Override
    public void doGetMostViewedProduct() {
        HomePageAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(HomePageAPI.class);
        Call<HomePageProductResponse> call = api.getMVProduct();
        call.enqueue(new Callback<HomePageProductResponse>() {
            @Override
            public void onResponse(Call<HomePageProductResponse> call, Response<HomePageProductResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        for (HomePageProductData data : response.body().getData()){
                            horizontalList.add(new HorizontalProductScrollModel(data.getId(),
                                    data.getImageURL(),
                                    data.getName(),
                                    data.getCatName(),
                                    data.getPrice()));
                        }
                        homePageAdapter.notifyDataSetChanged();
                    } else if (response.body().getStatus().equals("FAILED")){
                    }
                }
            }

            @Override
            public void onFailure(Call<HomePageProductResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    @Override
    public void doGetMostOrderedProduct() {
        HomePageAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(HomePageAPI.class);
        Call<HomePageProductResponse> call = api.getMOProduct();
        call.enqueue(new Callback<HomePageProductResponse>() {
            @Override
            public void onResponse(Call<HomePageProductResponse> call, Response<HomePageProductResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        for (HomePageProductData data : response.body().getData()){
                            gridList.add(new HorizontalProductScrollModel(data.getId(),
                                    data.getImageURL(),
                                    data.getName(),
                                    data.getCatName(),
                                    data.getPrice()));
                        }
                        homePageAdapter.notifyDataSetChanged();
                    } else if (response.body().getStatus().equals("FAILED")){
                    }
                }
            }

            @Override
            public void onFailure(Call<HomePageProductResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    @Override
    public void doGetCat() {
        HomePageAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(HomePageAPI.class);
        Call<GetCatResponse> call = api.getCat();
        call.enqueue(new Callback<GetCatResponse>() {
            @Override
            public void onResponse(Call<GetCatResponse> call, Response<GetCatResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        for (GetCatData data : response.body().getData()){
                            categoryModelList.add(new CategoryModel(data.getId(),
                                    data.getImageURL(),
                                    data.getName()));
                        }
                        categoryAdapter.notifyDataSetChanged();
                    } else if (response.body().getStatus().equals("FAILED")){
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCatResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
