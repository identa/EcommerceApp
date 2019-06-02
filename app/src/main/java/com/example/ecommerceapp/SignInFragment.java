package com.example.ecommerceapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.example.ecommerceapp.models.entities.requests.SignInRequest;
import com.example.ecommerceapp.models.entities.responses.SignInResponse;
import com.example.ecommerceapp.models.interfaces.SignInAPI;
import com.example.ecommerceapp.models.services.SignInService;
import com.example.ecommerceapp.models.services.SignInServiceImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.ecommerceapp.SignUpActivity.onResetPassword;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment implements SignInService {


    public SignInFragment() {
        // Required empty public constructor
    }

    private TextView dontHaveAccount;
    private FrameLayout parentFrameLayout;

    private EditText email;
    private EditText password;

    private TextView forgotPassword;

    private Button signInBtn;
    private ImageButton closeBtn;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAccount = view.findViewById(R.id.tv_dont_have_account);
        parentFrameLayout = getActivity().findViewById(R.id.sign_up_frame_layout);
        email = view.findViewById(R.id.sign_in_email);
        password = view.findViewById(R.id.sign_in_password);
        signInBtn = view.findViewById(R.id.cancel_btn);
        closeBtn = view.findViewById(R.id.sign_in_close_btn);
        progressBar = view.findViewById(R.id.sign_in_progress_bar);
        forgotPassword = view.findViewById(R.id.sign_in_forgot_password);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetPassword = true;
                setFragment(new ForgotPasswordFragment());
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

        signInBtn.setOnClickListener(new View.OnClickListener() {
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
        transaction.setCustomAnimations(R.anim.slide_right, R.anim.slideout_left);
        transaction.replace(parentFrameLayout.getId(), fragment);
        transaction.commit();
    }

//    private void checkInput() {
//        Drawable customWarningIcon = getResources().getDrawable(R.mipmap.warning);
//        customWarningIcon.setBounds(0, 0, customWarningIcon.getIntrinsicWidth(), customWarningIcon.getIntrinsicHeight());
//        if (!TextUtils.isEmpty(email.getText())) {
//            if (!TextUtils.isEmpty(password.getText())) {
//                signInBtn.setEnabled(true);
//                signInBtn.setTextColor(Color.rgb(255, 255, 255));
//            } else {
//                signInBtn.setEnabled(false);
//                signInBtn.setTextColor(Color.argb(50, 255, 255, 255));
//            }
//        } else {
//            signInBtn.setEnabled(false);
//            signInBtn.setTextColor(Color.argb(50, 255, 255, 255));
//        }
//    }

    private void checkInput() {
        Drawable customWarningIcon = getResources().getDrawable(R.mipmap.warning);
        customWarningIcon.setBounds(0, 0, customWarningIcon.getIntrinsicWidth(), customWarningIcon.getIntrinsicHeight());
        if (!TextUtils.isEmpty(email.getText())) {
            if (email.getText().toString().matches(ValidationConst.EMAIL)) {
                if (!TextUtils.isEmpty(password.getText())) {
                    if (password.getText().toString().matches(ValidationConst.PW)) {
                        signInBtn.setEnabled(true);
                        signInBtn.setTextColor(Color.rgb(255, 255, 255));
                    } else {
                        password.setError("Password doesn't format right", customWarningIcon);
                        signInBtn.setEnabled(false);
                        signInBtn.setTextColor(Color.argb(50, 255, 255, 255));
                    }
                } else {
                    signInBtn.setEnabled(false);
                    signInBtn.setTextColor(Color.argb(50, 255, 255, 255));
                }
            } else {
                email.setError("Email doesn't format right", customWarningIcon);
                signInBtn.setEnabled(false);
                signInBtn.setTextColor(Color.argb(50, 255, 255, 255));
            }
        } else {
            signInBtn.setEnabled(false);
            signInBtn.setTextColor(Color.argb(50, 255, 255, 255));
        }
    }

    private void checkEmailAndPassword() {
        Drawable customWarningIcon = getResources().getDrawable(R.mipmap.warning);
        customWarningIcon.setBounds(0, 0, customWarningIcon.getIntrinsicWidth(), customWarningIcon.getIntrinsicHeight());
        if (email.getText().toString().matches(ValidationConst.EMAIL)) {
            if (password.length() >= 7) {
                progressBar.setVisibility(View.VISIBLE);
                signInBtn.setEnabled(false);
                signInBtn.setTextColor(Color.argb(50, 255, 255, 255));

                SignInRequest request = new SignInRequest();
                request.setEmail(email.getText().toString());
                request.setPassword(password.getText().toString());

                doSignIn(request);
            } else {
                Toast.makeText(getActivity(), ValidationConst.INCORRECT, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getActivity(), ValidationConst.INCORRECT, Toast.LENGTH_SHORT).show();
        }
    }

    private void goHome() {
        Intent homeIntent = new Intent(getActivity(), HomeActivity.class);
        startActivity(homeIntent);
        getActivity().finish();
    }

    @Override
    public void doSignIn(SignInRequest request) {
        SignInAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(SignInAPI.class);
        Call<SignInResponse> call = api.signIn(request);
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
                        signInBtn.setEnabled(true);
                        signInBtn.setTextColor(Color.rgb(255, 255, 255));
                        String error = response.body().getMessage();
                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignInResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                progressBar.setVisibility(View.INVISIBLE);
                signInBtn.setEnabled(true);
                signInBtn.setTextColor(Color.rgb(255, 255, 255));
                String error = t.getMessage();
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
