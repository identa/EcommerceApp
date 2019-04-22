package com.example.ecommerceapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import static com.example.ecommerceapp.ProductDetailActivity.productDescription;
//import static com.example.ecommerceapp.ProductDetailActivity.productOtherDetails;
//import static com.example.ecommerceapp.ProductDetailActivity.tabsPosition;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDescriptionFragment extends Fragment {


    public ProductDescriptionFragment() {
        // Required empty public constructor
    }

    private TextView descBody;
    public String body;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_description, container, false);
        descBody = view.findViewById(R.id.tv_product_desc);
        descBody.setText(body);
//        if (tabsPosition == 0) {
//            descBody.setText(productDescription);
//        } else {
//            descBody.setText(productOtherDetails);
//        }
        return view;
    }

}
