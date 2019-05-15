package com.example.ecommerceapp;

import android.app.Dialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.ecommerceapp.adapters.CatCheckboxAdapter;
import com.example.ecommerceapp.adapters.GridProductLayoutAdapter;
import com.example.ecommerceapp.adapters.HorizontalProductScrollAdapter;
import com.example.ecommerceapp.adapters.WishlistAdapter;
import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.CheckboxModel;
import com.example.ecommerceapp.models.HorizontalProductScrollModel;
import com.example.ecommerceapp.models.WishlistModel;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.responses.HomePageProductData;
import com.example.ecommerceapp.models.entities.responses.HomePageProductResponse;
import com.example.ecommerceapp.models.entities.responses.HorizontalViewAllData;
import com.example.ecommerceapp.models.entities.responses.HorizontalViewAllResponse;
import com.example.ecommerceapp.models.interfaces.HomePageAPI;
import com.example.ecommerceapp.models.interfaces.ViewAllAPI;
import com.example.ecommerceapp.models.services.HomePageService;
import com.example.ecommerceapp.models.services.ViewAllService;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllActivity extends AppCompatActivity implements ViewAllService {

    private RecyclerView recyclerView;
    private GridView gridView;
    private ListView listView;
    private List<HorizontalProductScrollModel> gridModelList;
    //    private List<HorizontalProductScrollModel> horizontalProductScrollModelList;
//    private HorizontalProductScrollAdapter horizontalProductScrollAdapter;
    private List<WishlistModel> horizontalModelList;
    private List<WishlistModel> searchModelList;
    private List<WishlistModel> categoryModelList;

    private List<CheckboxModel> priceModelList;

    private WishlistAdapter horizontalAdapter;
    private GridProductLayoutAdapter gridProductLayoutAdapter;

    private Dialog sortDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_view);
        gridView = findViewById(R.id.grid_view);

        int layout_code = getIntent().getIntExtra("layout_code", -1);

        if (layout_code == 0) {
//            horizontalProductScrollModelList = new ArrayList<>();
            horizontalModelList = new ArrayList<>();
            doGetMostViewedProductAll();
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            horizontalAdapter = new WishlistAdapter(horizontalModelList, false);
            recyclerView.setAdapter(horizontalAdapter);
            horizontalAdapter.notifyDataSetChanged();
//            horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
//            recyclerView.setAdapter(horizontalProductScrollAdapter);
//            horizontalProductScrollAdapter.notifyDataSetChanged();
        } else if (layout_code == 1) {
            gridModelList = new ArrayList<>();
            doGetMostOrderedProductAll();
            gridView.setVisibility(View.VISIBLE);
            gridProductLayoutAdapter = new GridProductLayoutAdapter(gridModelList);
            gridView.setAdapter(gridProductLayoutAdapter);
            gridProductLayoutAdapter.notifyDataSetChanged();
        } else if (layout_code == 2) {
            String searchQuery = getIntent().getStringExtra("search_query");
            searchModelList = new ArrayList<>();
            doSearch(searchQuery);
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            horizontalAdapter = new WishlistAdapter(searchModelList, false);
            recyclerView.setAdapter(horizontalAdapter);
            horizontalAdapter.notifyDataSetChanged();
        } else if (layout_code == 3) {
//            MaterialSpinner spinner = findViewById(R.id.spinner);
//            spinner.setVisibility(View.VISIBLE);
//            spinner.setItems("Newest", "Oldest", "Price: DESC", "Price: ASC", "Most popular", "Best seller");
//            spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
//
//                @Override
//                public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                    Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
//                }
//            });

//            listView.setVisibility(View.VISIBLE);
            priceModelList = new ArrayList<>();

//            CatCheckboxAdapter catCheckboxAdapter = new CatCheckboxAdapter(priceModelList);
//            listView.setAdapter(catCheckboxAdapter);
//            catCheckboxAdapter.notifyDataSetChanged();

            sortDialog = new Dialog(ViewAllActivity.this);
            sortDialog.setContentView(R.layout.sort_dialog);
            sortDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            sortDialog.setCancelable(true);

            LinearLayout priceLinearLayout = sortDialog.findViewById(R.id.price_checkbox_layout);

            MaterialSpinner spinner = sortDialog.findViewById(R.id.spinner);
            spinner.setItems("Newest", "Oldest", "Price: DESC", "Price: ASC", "Most popular", "Best seller");
            spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                    Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                }
            });
            for (int i = 1; i <= priceLinearLayout.getChildCount(); i++){
                View checkboxView = priceLinearLayout.getChildAt(i);
                if (checkboxView instanceof CheckBox){
                    ((CheckBox) checkboxView).setChecked(true);
                }
            }

            categoryModelList = new ArrayList<>();
            doShowCat(getIntent().getIntExtra("cat_id", 1));
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            horizontalAdapter = new WishlistAdapter(categoryModelList, false);
            recyclerView.setAdapter(horizontalAdapter);
            horizontalAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }else if (id == R.id.home_search_icon){
            sortDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void doGetMostViewedProductAll() {
        ViewAllAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(ViewAllAPI.class);
        Call<HorizontalViewAllResponse> call = api.getMVProductAll();
        call.enqueue(new Callback<HorizontalViewAllResponse>() {
            @Override
            public void onResponse(Call<HorizontalViewAllResponse> call, Response<HorizontalViewAllResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
//                        for (HomePageProductData data : response.body().getData()){
//                            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(data.getId(),
//                                    data.getImageURL(),
//                                    data.getName(),
//                                    data.getCatName(),
//                                    data.getPrice()));
//                        }
//                        horizontalProductScrollAdapter.notifyDataSetChanged();
                        for (HorizontalViewAllData data : response.body().getData()) {
                            horizontalModelList.add(new WishlistModel(data.getId(),
                                    data.getProductImage(),
                                    data.getProductTitle(),
                                    data.getRating(),
                                    data.getTotalRatings(),
                                    data.getProductPrice(),
                                    data.getCuttedPrice()));
                        }

                        horizontalAdapter.notifyDataSetChanged();
                    } else if (response.body().getStatus().equals("FAILED")) {
                    }
                }
            }

            @Override
            public void onFailure(Call<HorizontalViewAllResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    @Override
    public void doGetMostOrderedProductAll() {
        ViewAllAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(ViewAllAPI.class);
        Call<HomePageProductResponse> call = api.getMOProductAll();
        call.enqueue(new Callback<HomePageProductResponse>() {
            @Override
            public void onResponse(Call<HomePageProductResponse> call, Response<HomePageProductResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        for (HomePageProductData data : response.body().getData()) {
                            gridModelList.add(new HorizontalProductScrollModel(data.getId(),
                                    data.getImageURL(),
                                    data.getName(),
                                    data.getCatName(),
                                    data.getPrice()));
                        }
                        gridProductLayoutAdapter.notifyDataSetChanged();
                    } else if (response.body().getStatus().equals("FAILED")) {
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
    public void doSearch(String query) {
        ViewAllAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(ViewAllAPI.class);
        Call<HorizontalViewAllResponse> call = api.search(query);
        call.enqueue(new Callback<HorizontalViewAllResponse>() {
            @Override
            public void onResponse(Call<HorizontalViewAllResponse> call, Response<HorizontalViewAllResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        for (HorizontalViewAllData data : response.body().getData()) {
                            searchModelList.add(new WishlistModel(data.getId(),
                                    data.getProductImage(),
                                    data.getProductTitle(),
                                    data.getRating(),
                                    data.getTotalRatings(),
                                    data.getProductPrice(),
                                    data.getCuttedPrice()));
                        }

                        horizontalAdapter.notifyDataSetChanged();
                    } else if (response.body().getStatus().equals("FAILED")) {
                    }
                }
            }

            @Override
            public void onFailure(Call<HorizontalViewAllResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    @Override
    public void doShowCat(int id) {
        ViewAllAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(ViewAllAPI.class);
        Call<HorizontalViewAllResponse> call = api.showCat(id);
        call.enqueue(new Callback<HorizontalViewAllResponse>() {
            @Override
            public void onResponse(Call<HorizontalViewAllResponse> call, Response<HorizontalViewAllResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        for (HorizontalViewAllData data : response.body().getData()) {
                            categoryModelList.add(new WishlistModel(data.getId(),
                                    data.getProductImage(),
                                    data.getProductTitle(),
                                    data.getRating(),
                                    data.getTotalRatings(),
                                    data.getProductPrice(),
                                    data.getCuttedPrice()));
                        }

                        horizontalAdapter.notifyDataSetChanged();
                    } else if (response.body().getStatus().equals("FAILED")) {
                    }
                }
            }

            @Override
            public void onFailure(Call<HorizontalViewAllResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
