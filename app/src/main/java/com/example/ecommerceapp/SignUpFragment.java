package com.example.ecommerceapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.constants.ValidationConst;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.requests.SignUpRequest;
import com.example.ecommerceapp.models.entities.responses.SignInResponse;
import com.example.ecommerceapp.models.interfaces.SignUpAPI;
import com.example.ecommerceapp.models.services.SignUpService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment implements SignUpService {


    public SignUpFragment() {
        // Required empty public constructor
    }

    private TextView alreadyHaveAccount;
    private FrameLayout parentFrameLayout;

    private EditText email;
    private EditText fullName;
    private EditText lastName;
    private EditText password;
    private EditText confirmPassword;

    private Button signUpBtn;
    private ImageButton closeBtn;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        alreadyHaveAccount = view.findViewById(R.id.tv_already_have_account);
        parentFrameLayout = getActivity().findViewById(R.id.sign_up_frame_layout);

        email = view.findViewById(R.id.sign_up_email);
        fullName = view.findViewById(R.id.sign_up_full_name);
        lastName = view.findViewById(R.id.sign_up_last_name);
        password = view.findViewById(R.id.sign_up_password);
        confirmPassword = view.findViewById(R.id.sign_up_confirm);

        signUpBtn = view.findViewById(R.id.ok_btn);
        closeBtn = view.findViewById(R.id.sign_up_close_btn);
        progressBar = view.findViewById(R.id.sign_up_progress_bar);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });

        email.addTextChangedListener(new TextWatcher() {
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

        fullName.addTextChangedListener(new TextWatcher() {
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

        password.addTextChangedListener(new TextWatcher() {
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

        confirmPassword.addTextChangedListener(new TextWatcher() {
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

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_left, R.anim.slideout_right);
        transaction.replace(parentFrameLayout.getId(), fragment);
        transaction.commit();
    }

//    private void checkInput() {
//        if (!TextUtils.isEmpty(email.getText())) {
//            if (!TextUtils.isEmpty(fullName.getText())) {
//                if (!TextUtils.isEmpty(password.getText()) && password.length() >= 8) {
//                    if (!TextUtils.isEmpty(confirmPassword.getText())) {
//                        signUpBtn.setEnabled(true);
//                        signUpBtn.setTextColor(Color.rgb(255, 255, 255));
//                    } else {
//                        signUpBtn.setEnabled(false);
//                        signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
//                    }
//                } else {
//                    signUpBtn.setEnabled(false);
//                    signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
//                }
//            } else {
//                signUpBtn.setEnabled(false);
//                signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
//            }
//        } else {
//            signUpBtn.setEnabled(false);
//            signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
//        }
//    }

    private void checkInput() {
        Drawable customWarningIcon = getResources().getDrawable(R.mipmap.warning);
        customWarningIcon.setBounds(0, 0, customWarningIcon.getIntrinsicWidth(), customWarningIcon.getIntrinsicHeight());
        if (!TextUtils.isEmpty(email.getText())) {
            if (email.getText().toString().matches(ValidationConst.EMAIL)) {
                if (!TextUtils.isEmpty(fullName.getText())){
                    if (!TextUtils.isEmpty(lastName.getText())){
                        if (!TextUtils.isEmpty(password.getText())) {
                            if (password.getText().toString().matches(ValidationConst.PW)) {
                                if (!TextUtils.isEmpty(confirmPassword.getText())){
                                    if (confirmPassword.getText().toString().equals(password.getText().toString())){
                                        signUpBtn.setEnabled(true);
                                        signUpBtn.setTextColor(Color.rgb(255, 255, 255));
                                    }else {
                                        confirmPassword.setError("Password doesn't match", customWarningIcon);
                                        signUpBtn.setEnabled(false);
                                        signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
                                    }
                                }else {
                                    signUpBtn.setEnabled(false);
                                    signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
                                }
                            } else {
                                password.setError("Password doesn't format right", customWarningIcon);
                                signUpBtn.setEnabled(false);
                                signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
                            }
                        } else {
                            signUpBtn.setEnabled(false);
                            signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
                        }
                    }else {
                        signUpBtn.setEnabled(false);
                        signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
                    }
                }else {
                    signUpBtn.setEnabled(false);
                    signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
                }

            } else {
                email.setError("Email doesn't format right", customWarningIcon);
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            signUpBtn.setEnabled(false);
            signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    private void checkEmailAndPassword() {
        Drawable customWarningIcon = getResources().getDrawable(R.mipmap.warning);
        customWarningIcon.setBounds(0, 0, customWarningIcon.getIntrinsicWidth(), customWarningIcon.getIntrinsicHeight());
        if (email.getText().toString().matches(ValidationConst.EMAIL)) {
            if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                progressBar.setVisibility(View.VISIBLE);
                signUpBtn.setEnabled(false);
                signUpBtn.setTextColor(Color.argb(50, 255, 255, 255));

                SignUpRequest request = new SignUpRequest();
                request.setEmail(email.getText().toString());
                request.setFirstName(fullName.getText().toString());
                request.setLastName(lastName.getText().toString());
                request.setPassword(password.getText().toString());

                doSignUp(request);
            } else {
                confirmPassword.setError(ValidationConst.PWD_ERROR, customWarningIcon);
            }
        } else {
            email.setError(ValidationConst.EMAIL_ERROR, customWarningIcon);
        }
    }

    private void goHome() {
        Intent homeIntent = new Intent(getActivity(), HomeActivity.class);
        startActivity(homeIntent);
        getActivity().finish();
    }

    @Override
    public void doSignUp(SignUpRequest request) {
        SignUpAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(SignUpAPI.class);
        Call<SignInResponse> call = api.signUp(request);
        call.enqueue(new Callback<SignInResponse>() {
            @Override
            public void onResponse(Call<SignInResponse> call, Response<SignInResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("signin_info", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("firstName", response.body().getData().getFirstName());
                        editor.putString("lastName", response.body().getData().getLastName());
                        editor.putString("imageURL", response.body().getData().getImageURL());
                        editor.putString("token", response.body().getData().getToken());
                        editor.putInt("id", response.body().getData().getId());
                        editor.putString("email", email.getText().toString());

                        editor.apply();
                        goHome();
                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        signUpBtn.setEnabled(true);
                        signUpBtn.setTextColor(Color.rgb(255, 255, 255));
                        String error = response.body().getMessage();
                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                progressBar.setVisibility(View.INVISIBLE);
                signUpBtn.setEnabled(true);
                signUpBtn.setTextColor(Color.rgb(255, 255, 255));
                String error = t.getMessage();
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
