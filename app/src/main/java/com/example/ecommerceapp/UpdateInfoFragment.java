package com.example.ecommerceapp;


import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.ecommerceapp.constants.BaseURLConst;
import com.example.ecommerceapp.models.client.RetrofitClient;
import com.example.ecommerceapp.models.entities.requests.UpdateInfoRequest;
import com.example.ecommerceapp.models.entities.requests.UpdatePassRequest;
import com.example.ecommerceapp.models.entities.responses.UpdateInfoResponse;
import com.example.ecommerceapp.models.interfaces.UpdateInfoAPI;
import com.example.ecommerceapp.models.services.UpdateInfoService;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateInfoFragment extends Fragment implements UpdateInfoService {


    public UpdateInfoFragment() {
        // Required empty public constructor
    }

    private CircleImageView circleImageView;
    private Button changePhotoBtn, updateBtn;
    private EditText firstNameField, lastNameField;
    private String photo;
    private String firstName;
    private String lastName;
    private StorageReference storageReference;
    private SharedPreferences sharedPreferences;
    private Dialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update_info, container, false);

        circleImageView = view.findViewById(R.id.profile_image);
        changePhotoBtn = view.findViewById(R.id.change_photo_btn);
        updateBtn = view.findViewById(R.id.update_info_btn);
        firstNameField = view.findViewById(R.id.first_name);
        lastNameField = view.findViewById(R.id.last_name);

        storageReference = FirebaseStorage.getInstance().getReference("avatars");
        sharedPreferences = getActivity().getSharedPreferences("signin_info", Context.MODE_PRIVATE);

        firstName = getArguments().getString("firstName");
        lastName = getArguments().getString("lastName");
        photo = getArguments().getString("photo");

        Glide.with(getContext()).load(photo).into(circleImageView);
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);

        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        firstNameField.addTextChangedListener(new TextWatcher() {
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

        lastNameField.addTextChangedListener(new TextWatcher() {
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

        changePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 1);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateInfoRequest request = new UpdateInfoRequest();
                request.setFirstName(firstNameField.getText().toString());
                request.setLastName(lastNameField.getText().toString());
                request.setPhoto(photo);
                updateInfo(sharedPreferences.getInt("id", 1), request);
            }
        });
        return view;
    }

    private void checkInput() {
        if (!TextUtils.isEmpty(firstNameField.getText())) {
            if (!TextUtils.isEmpty(lastNameField.getText())) {
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            if (resultCode == getActivity().RESULT_OK){
                if (data != null){
                    Uri uri = data.getData();
                    Glide.with(getContext()).load(uri).into(circleImageView);
                    uploadImage(data.getData());
                }else {
                    Toast.makeText(getContext(), "Image not found!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void updateInfo(int id, UpdateInfoRequest request) {
        loadingDialog.show();
        UpdateInfoAPI api = RetrofitClient.getClient(BaseURLConst.ALT_URL).create(UpdateInfoAPI.class);
        Call<UpdateInfoResponse> call = api.updateInfo(id, request);
        call.enqueue(new Callback<UpdateInfoResponse>() {
            @Override
            public void onResponse(Call<UpdateInfoResponse> call, Response<UpdateInfoResponse> response) {
                if (response.code() == 200) {
                    if (response.body().getStatus().equals("SUCCESS")) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("firstName", response.body().getData().getFirstName());
                        editor.putString("lastName", response.body().getData().getLastName());
                        editor.putString("imageURL", response.body().getData().getPhoto());
                        editor.apply();

                        Intent accountIntent = new Intent(getContext(), HomeActivity.class);
                        accountIntent.putExtra("showAccount", true);
                        startActivity(accountIntent);
                        loadingDialog.dismiss();
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else if (response.body().getStatus().equals("FAILED")){
                        loadingDialog.dismiss();
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdateInfoResponse> call, Throwable t) {
                loadingDialog.dismiss();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void updatePass(int id, UpdatePassRequest request) {

    }

    private String getFileExtension(Uri imageUri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    private void uploadImage(Uri imageUri){
        if (imageUri != null){
            loadingDialog.show();
            final StorageReference reference = storageReference.child(sharedPreferences.getString("email", "no_email") + "." + System.currentTimeMillis() + "." + getFileExtension(imageUri));

            Task<Uri> uriTask = reference.putFile(imageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }
                    return reference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        photo = task.getResult().toString();
                        loadingDialog.dismiss();
                        Toast.makeText(getContext(), "Get image uri successfully", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else {
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_LONG).show();
        }
    }
}
