package com.example.ecommerceapp.adapters;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerceapp.ProductDetailActivity;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.SliderAdapter;
import com.example.ecommerceapp.SliderModel;
import com.example.ecommerceapp.ViewAllActivity;
import com.example.ecommerceapp.models.HomePageModel;
import com.example.ecommerceapp.models.HorizontalProductScrollModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.ecommerceapp.adapters.GridProductLayoutAdapter.isGridInHomePage;
import static com.example.ecommerceapp.adapters.HorizontalProductScrollAdapter.isHorizontalInHomePage;

public class HomePageAdapter extends RecyclerView.Adapter {
    private List<HomePageModel> homePageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case HomePageModel.BANNER_SLIDER:
                View bannerSliderView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sliding_ad_layout, viewGroup, false);
                return new BannerSliderViewHolder(bannerSliderView);
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalProductView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_layout, viewGroup, false);
                return new HorizontalProductViewHolder(horizontalProductView);
            case HomePageModel.GRID_PRODUCT_VIEW:
                View gridProductView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_product_layout, viewGroup, false);
                return new GridProductViewHolder(gridProductView);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (homePageModelList.get(i).getType()) {
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList = homePageModelList.get(i).getSliderModelList();
                ((BannerSliderViewHolder) viewHolder).setBannerSliderViewPager(sliderModelList);
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String horizontalTitle = homePageModelList.get(i).getTitle();
                List<HorizontalProductScrollModel> horizontalProductScrollModelList = homePageModelList.get(i).getHorizontalProductScrollModelList();
                ((HorizontalProductViewHolder) viewHolder).setHorizontalProductLayout(horizontalProductScrollModelList, horizontalTitle);
                isHorizontalInHomePage = false;
                break;
            case HomePageModel.GRID_PRODUCT_VIEW:
                String gridTitle = homePageModelList.get(i).getTitle();
                List<HorizontalProductScrollModel> gridProductModelList = homePageModelList.get(i).getHorizontalProductScrollModelList();
                ((GridProductViewHolder) viewHolder).setGridProductLayout(gridProductModelList, gridTitle);
                isGridInHomePage = false;
                break;
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return homePageModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelList.get(position).getType()) {
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 2:
                return HomePageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }
    }

    public class BannerSliderViewHolder extends RecyclerView.ViewHolder {

        private int currentPage = 0;
        private Timer timer;
        private ViewPager bannerSliderViewPager;

        private final long DELAY_TIME = 3000;
        private final long PERIOD_TIME = 3000;
        private List<SliderModel> arrangedList;

        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.banner_slider_view_pager);

        }

        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList) {
//            currentPage = 2;
            if (timer != null){
                timer.cancel();
            }
//            arrangedList = new ArrayList<>();
//            for (int i = 0; i < sliderModelList.size(); i++){
//                arrangedList.add(i, sliderModelList.get(i));
//            }
//            arrangedList.add(0, sliderModelList.get(sliderModelList.size() - 2));
//            arrangedList.add(1, sliderModelList.get(sliderModelList.size() - 1));
//            arrangedList.add(sliderModelList.get(0));
//            arrangedList.add(sliderModelList.get(1));

            SliderAdapter sliderAdapter = new SliderAdapter(sliderModelList);
            bannerSliderViewPager.setAdapter(sliderAdapter);
            bannerSliderViewPager.setClipToPadding(false);
            bannerSliderViewPager.setPageMargin(20);

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
                    if (i == ViewPager.SCROLL_STATE_IDLE) {
                    }
                }
            };
            bannerSliderViewPager.addOnPageChangeListener(pageChangeListener);

            startBannerSlide(sliderModelList);
            bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    stopBannerSlide();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        startBannerSlide(sliderModelList);
                    }
                    return false;
                }
            });
        }

        private void startBannerSlide(final List<SliderModel> sliderModelList) {
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentPage >= sliderModelList.size()) {
                        currentPage = 0;
                    }
                    bannerSliderViewPager.setCurrentItem(currentPage++, true);
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

        private void stopBannerSlide() {
            timer.cancel();
        }
    }

    public class HorizontalProductViewHolder extends RecyclerView.ViewHolder {

        private TextView horizontalLayoutTitle;
        private Button horizontalViewAllBtn;
        private RecyclerView horizontalRecyclerView;

        public HorizontalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontalLayoutTitle = itemView.findViewById(R.id.horizontal_scroll_layout_title);
            horizontalViewAllBtn = itemView.findViewById(R.id.horizontal_scroll_layout_view_all);
            horizontalRecyclerView = itemView.findViewById(R.id.horizontal_scroll_layout_recycler_view);
            horizontalRecyclerView.setRecycledViewPool(recycledViewPool);
        }

        private void setHorizontalProductLayout(final List<HorizontalProductScrollModel> horizontalProductScrollModelList, String title) {
            horizontalLayoutTitle.setText(title);

            if (horizontalProductScrollModelList.size() > 8) {
                horizontalViewAllBtn.setVisibility(View.VISIBLE);
                horizontalViewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.horizontalProductScrollModelList = horizontalProductScrollModelList;
                        Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        viewAllIntent.putExtra("layout_code", 0);
                        itemView.getContext().startActivity(viewAllIntent);
                    }
                });
            } else {
                horizontalViewAllBtn.setVisibility(View.INVISIBLE);
            }
            isHorizontalInHomePage = true;
            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            LinearLayoutManager horizontalProductLayoutManager = new LinearLayoutManager(itemView.getContext());
            horizontalProductLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horizontalRecyclerView.setLayoutManager(horizontalProductLayoutManager);

            horizontalRecyclerView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }

    public class GridProductViewHolder extends RecyclerView.ViewHolder {

        private TextView gridLayoutTitle;
        private Button gridLayoutViewAllBtn;
        private GridView gridView;
//        private GridLayout gridProductLayout;

        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
            gridLayoutTitle = itemView.findViewById(R.id.grid_product_layout_title);
            gridLayoutViewAllBtn = itemView.findViewById(R.id.grid_product_layout_btn);
            gridView = itemView.findViewById(R.id.grid_product_layout_view);
//            gridProductLayout = itemView.findViewById(R.id.grid_layout);
        }

        private void setGridProductLayout(final List<HorizontalProductScrollModel> gridProductModelList, final String title) {
            gridLayoutTitle.setText(title);
            isGridInHomePage = true;
            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(gridProductModelList);
            gridView.setAdapter(gridProductLayoutAdapter);
            gridProductLayoutAdapter.notifyDataSetChanged();

//            for (int i = 0; i < 4; i++) {
//                ImageView productImage = gridProductLayout.getChildAt(i).findViewById(R.id.h_s_product_image);
//                TextView productTitle = gridProductLayout.getChildAt(i).findViewById(R.id.h_s_product_title);
//                TextView productDesc = gridProductLayout.getChildAt(i).findViewById(R.id.h_s_product_desc);
//                TextView productPrice = gridProductLayout.getChildAt(i).findViewById(R.id.h_s_product_price);
//
////                productImage.setImageResource(gridProductModelList.get(i).getNum());
////                Glide.with(itemView.getContext()).load(gridProductModelList.get(i).getImageLink()).apply(new RequestOptions().placeholder(R.mipmap.steakhouse)).into(productImage);
//                productTitle.setText(gridProductModelList.get(i).getTitle());
//                productDesc.setText(gridProductModelList.get(i).getDesc());
//                productPrice.setText(String.format("$%s", gridProductModelList.get(i).getPrice()));
//
//                gridProductLayout.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
//                gridProductLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent productDetailsIntent = new Intent(itemView.getContext(), ProductDetailActivity.class);
//                        itemView.getContext().startActivity(productDetailsIntent);
//                    }
//                });
//            }
            if (gridProductModelList.size() > 4){
                gridLayoutViewAllBtn.setVisibility(View.VISIBLE);
                gridLayoutViewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ViewAllActivity.gridModelList = gridProductModelList;
                        Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                        viewAllIntent.putExtra("layout_code", 1);
                        viewAllIntent.putExtra("title", title);
                        itemView.getContext().startActivity(viewAllIntent);
                    }
                });
            } else {
                gridLayoutViewAllBtn.setVisibility(View.INVISIBLE);
            }

        }
    }
}
