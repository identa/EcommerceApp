package com.example.ecommerceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import com.example.ecommerceapp.adapters.GridProductLayoutAdapter;
import com.example.ecommerceapp.adapters.HorizontalProductScrollAdapter;
import com.example.ecommerceapp.models.HorizontalProductScrollModel;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridView gridView;
    public static List<HorizontalProductScrollModel> gridModelList;

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

        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(1, "https://i.imgur.com/2G9UXB2.png", "a", "Samsung Galaxy S10", 10));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));

        if (layout_code == 0){
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);

            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            recyclerView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }else if (layout_code == 1){
            gridView.setVisibility(View.VISIBLE);
//            List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));
//            horizontalProductScrollModelList.add(new HorizontalProductScrollModel(R.mipmap.steakhouse, "Samsung Galaxy S10", "Samsung", "$1000.00"));

            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(gridModelList);
            gridView.setAdapter(gridProductLayoutAdapter);
            gridProductLayoutAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
