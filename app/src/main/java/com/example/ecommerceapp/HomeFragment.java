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

    private List<CategoryModel> categoryModelList;
    private List<SliderModel> sliderModelList;

    private int currentPage = 0;
    private Timer timer;
    private ViewPager bannerSliderViewPager;

    private TextView horizontalLayoutTitle;
    private Button horizontalViewAllBtn;
    private RecyclerView horizontalRecyclerView;

    private TextView gridLayoutTitle;
    private Button gridLayoutViewAllBtn;
    private GridView gridView;

    private FirebaseFirestore firebaseFirestore;

    private final long DELAY_TIME = 3000;
    private final long PERIOD_TIME = 3000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        catRecyclerView = view.findViewById(R.id.cat_recycler_view);
        bannerSliderViewPager = view.findViewById(R.id.banner_slider_view_pager);

        sliderModelList = new ArrayList<>();
        sliderModelList.add(new SliderModel(R.mipmap.cancel));
        sliderModelList.add(new SliderModel(R.mipmap.email));
        sliderModelList.add(new SliderModel(R.mipmap.caution));
        sliderModelList.add(new SliderModel(R.mipmap.cancel));
        sliderModelList.add(new SliderModel(R.mipmap.email));
        sliderModelList.add(new SliderModel(R.mipmap.error));
        sliderModelList.add(new SliderModel(R.mipmap.plus));
        sliderModelList.add(new SliderModel(R.mipmap.email));
        sliderModelList.add(new SliderModel(R.mipmap.steakhouse));


        SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
        bannerSliderViewPager.setAdapter(sliderAdapter);
        bannerSliderViewPager.setClipToPadding(false);
        bannerSliderViewPager.setPageMargin(20);

        bannerSliderViewPager.setCurrentItem(currentPage);

        ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                currentPage = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (i == ViewPager.SCROLL_STATE_IDLE){
//                    pageLooper();
                }
            }
        };
        bannerSliderViewPager.addOnPageChangeListener(pageChangeListener);

        startBannerSlide();
        bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                pageLooper();
                stopBannerSlide();
                if (event.getAction() == MotionEvent.ACTION_UP){
                    startBannerSlide();
                }
                return false;
            }
        });

        horizontalLayoutTitle = view.findViewById(R.id.horizontal_scroll_layout_title);
        horizontalViewAllBtn = view.findViewById(R.id.horizontal_scroll_layout_view_all);
        horizontalRecyclerView = view.findViewById(R.id.horizontal_scroll_layout_recycler_view);

        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));

        HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
        LinearLayoutManager horizontalProductLayoutManager = new LinearLayoutManager(getContext());
        horizontalProductLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        horizontalRecyclerView.setLayoutManager(horizontalProductLayoutManager);

        horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
        horizontalProductScrollAdapter.notifyDataSetChanged();

        gridLayoutTitle = view.findViewById(R.id.grid_product_layout_title);
        gridLayoutViewAllBtn = view.findViewById(R.id.grid_product_layout_btn);
        gridView = view.findViewById(R.id.grid_product_layout_view);

        gridView.setAdapter(new GridProductLayoutAdapter(horizontalProductScrollModelList));

        //homepage
        RecyclerView testing = view.findViewById(R.id.testing);
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

//    private void pageLooper(){
//        if (currentPage == sliderModelList.size() - 2){
//            currentPage = 2;
//            bannerSliderViewPager.setCurrentItem(currentPage, false);
//        }
//        if (currentPage == 1){
//            currentPage = sliderModelList.size() - 3;
//            bannerSliderViewPager.setCurrentItem(currentPage, false);
//        }
//    }

    private void startBannerSlide(){
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage >= sliderModelList.size()){
                    currentPage = 0;
                }
                bannerSliderViewPager.setCurrentItem(currentPage ++, true);
            }
        };
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_TIME, PERIOD_TIME);
    }

    private void stopBannerSlide(){
        timer.cancel();
    }
}
