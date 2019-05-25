package com.example.ecommerceapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.constants.ValidationConst;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.requests.UpdateInfoRequest;
import com.example.ecommerceapp.models.entities.requests.UpdatePassRequest;
import com.example.ecommerceapp.models.entities.responses.UpdatePassResponse;
import com.example.ecommerceapp.models.interfaces.UpdateInfoAPI;
import com.example.ecommerceapp.models.services.UpdateInfoService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdatePasswordFragment extends Fragment implements UpdateInfoService {


    public UpdatePasswordFragment() {
        // Required empty public constructor
    }

    private EditText oldPass, newPass, confirmPass;
    private Button updateBtn;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_password, container, false);
        oldPass = view.findViewById(R.id.old_password);
        newPass = view.findViewById(R.id.new_password);
        confirmPass = view.findViewById(R.id.confirm_password);
        updateBtn = view.findViewById(R.id.update_password_btn);

        sharedPreferences = getActivity().getSharedPreferences("signin_info", Context.MODE_PRIVATE);
        oldPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        newPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirmPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPassword();
            }
        });


        return view;
    }

    private void checkInput() {
        if (!TextUtils.isEmpty(oldPass.getText()) && oldPass.length() >= 8) {
            if (!TextUtils.isEmpty(newPass.getText()) && newPass.length() >= 8) {
                if (!TextUtils.isEmpty(confirmPass.getText()) && confirmPass.length() >= 8) {
                    updateBtn.setEnabled(true);
                    updateBtn.setTextColor(Color.rgb(255, 255, 255));
                } else {
                    updateBtn.setEnabled(false);
                    updateBtn.setTextColor(Color.argb(50, 255, 255, 255));
                }
            } else {
                updateBtn.setEnabled(false);
                updateBtn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            updateBtn.setEnabled(false);
            updateBtn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    private void checkPassword() {
        Drawable customWarningIcon = getResources().getDrawable(R.mipmap.warning);
        customWarningIcon.setBounds(0, 0, customWarningIcon.getIntrinsicWidth(), customWarningIcon.getIntrinsicHeight());
            if (newPass.getText().toString().equals(confirmPass.getText().toString())) {
                updateBtn.setEnabled(false);
                updateBtn.setTextColor(Color.argb(50, 255, 255, 255));
                UpdatePassRequest request = new UpdatePassRequest();
                request.setOldPass(oldPass.getText().toString());
                request.setNewPass(newPass.getText().toString());
                updatePass(sharedPreferences.getInt("id", 1), request);
            } else {
                confirmPass.setError(ValidationConst.PWD_ERROR, customWarningIcon);
            }
    }

    @Override
    public void updateInfo(int id, UpdateInfoRequest request) {

    }

    @Override
    public void updatePass(int id, UpdatePassRequest request) {
        UpdateInfoAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(UpdateInfoAPI.class);
        Call<UpdatePassResponse> call = api.updatePass(id, request);
        call.enqueue(new Callback<UpdatePassResponse>() {
            @Override
            public void onResponse(Call<UpdatePassResponse> call, Response<UpdatePassResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        Intent accountIntent = new Intent(getContext(), HomeActivity.class);
                        accountIntent.putExtra("showAccount", true);
                        startActivity(accountIntent);

                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else if (response.body().getStatus().equals("FAILED")){
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdatePassResponse> call, Throwable t) {

            }
        });
    }
}
