package com.example.ecommerceapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.AddAddressModel;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.responses.GetAddressResponse;
import com.example.ecommerceapp.models.interfaces.GetAddressAPI;
import com.example.ecommerceapp.models.services.GetAddressService;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment implements GetAddressService {


    public MyAccountFragment() {
        // Required empty public constructor
    }

    private Button editAddressBtn;
    private Button addAddressBtn;
    private TextView userName;
    private TextView userEmail;
    private CircleImageView profileImage;
    private TextView recipientName;
    private TextView apartmentNumber;
    private TextView city;
    private TextView state;
    private TextView postalCode;

    private ConstraintLayout addressLayout;

    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);

        userName = view.findViewById(R.id.user_name);
        userEmail = view.findViewById(R.id.user_email);
        profileImage = view.findViewById(R.id.profile_image);
        addAddressBtn = view.findViewById(R.id.add_address_btn);
        recipientName = view.findViewById(R.id.address_recipient_name);
        apartmentNumber = view.findViewById(R.id.address_apartment_number);
        city = view.findViewById(R.id.address_city);
        state = view.findViewById(R.id.address_state);
        postalCode = view.findViewById(R.id.address_postal_code);

        addressLayout = view.findViewById(R.id.address_layout);
        editAddressBtn = view.findViewById(R.id.edit_address_btn);

        sharedPreferences = getActivity().getSharedPreferences("signin_info", Context.MODE_PRIVATE);
        Glide.with(this).load(sharedPreferences.getString("imageURL", "a")).apply(new RequestOptions().placeholder(R.mipmap.steakhouse)).into(profileImage);

        doGetAddress(sharedPreferences.getInt("id", 1));
        userName.setText(String.format("%s %s", sharedPreferences.getString("firstName", "a"), sharedPreferences.getString("lastName", "a")));
//        editAddressBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent addAddressIntent = new Intent(getContext(), AddAddressActivity.class);
//                addAddressIntent.putExtra("mode", 0);
//                startActivity(addAddressIntent);
//            }
//        });
        return view;
    }

    @Override
    public void doGetAddress(int id) {
        GetAddressAPI api = RetrofitClient.getClient(BaseURLConst.BASE_URL).create(GetAddressAPI.class);
        Call<GetAddressResponse> call = api.getAddress(id);
        call.enqueue(new Callback<GetAddressResponse>() {
            @Override
            public void onResponse(Call<GetAddressResponse> call, Response<GetAddressResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        if (response.body().getData() == null) {
                            addAddressBtn.setVisibility(View.VISIBLE);
                            addressLayout.setVisibility(View.GONE);

                            addAddressBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent addAddressIntent = new Intent(getContext(), AddAddressActivity.class);
                                    addAddressIntent.putExtra("mode", 0);
                                    startActivity(addAddressIntent);
                                }
                            });
                        } else {
                            addAddressBtn.setVisibility(View.GONE);
                            addressLayout.setVisibility(View.VISIBLE);
                            recipientName.setText(response.body().getData().getRecipientName());
                            city.setText(response.body().getData().getCity());
                            state.setText(response.body().getData().getState());
                            apartmentNumber.setText(response.body().getData().getAddress());
                            postalCode.setText(String.format("%d", response.body().getData().getPostalCode()));

                            AddAddressActivity.addAddressModel = new AddAddressModel(response.body().getData().getRecipientName(),
                                    response.body().getData().getCity(),
                                    response.body().getData().getAddress(),
                                    response.body().getData().getState(),
                                    response.body().getData().getPostalCode());

                            editAddressBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent addAddressIntent = new Intent(getContext(), AddAddressActivity.class);
                                    addAddressIntent.putExtra("mode", 1);
                                    startActivity(addAddressIntent);
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GetAddressResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
