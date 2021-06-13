package com.example.clothesvillage.mypage;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.User;
import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.closet.ClosetRegisterActivity;
import com.example.clothesvillage.databinding.ActivityProfileUpdateBinding;
import com.example.clothesvillage.databinding.ActivitySignUpBinding;
import com.example.clothesvillage.remote.ClothesRepository;
import com.example.clothesvillage.remote.request.InsertUser;
import com.example.clothesvillage.remote.request.SingleRequest;
import com.example.clothesvillage.remote.request.UpdateUserRequest;
import com.example.clothesvillage.remote.response.UserInfoResponse;
import com.example.clothesvillage.utils.DateUtils;
import com.example.clothesvillage.utils.FileUtils;
import com.example.clothesvillage.utils.FormDataUtil;
import com.example.clothesvillage.utils.ImageLoaderHelper;
import com.example.clothesvillage.utils.MessageUtils;
import com.example.clothesvillage.utils.PreferenceHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserInfoUpdateActivity extends BaseActivity<ActivityProfileUpdateBinding> {

    private String selectedGender = "남";
    private String selectedBirth = "";
    private Uri mCurrentUri = null;

    private UserInfoResponse currentUserItem;

    @Override
    protected int layoutRes() {
        return R.layout.activity_profile_update;
    }

    @Override
    protected void onViewCreated() {


        binding.ivBack.setOnClickListener(view -> finish());
        binding.tvComplte.setOnClickListener(view -> updateUser());
        binding.btnBirth.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(UserInfoUpdateActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(Calendar.YEAR, year);
                    selectedCalendar.set(Calendar.MONTH, monthOfYear);
                    selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    selectedBirth = DateUtils.getDate(selectedCalendar.getTimeInMillis());
                    binding.btnBirth.setText(selectedBirth);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            dialog.getDatePicker().setCalendarViewShown(false);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        });

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio_man:
                        selectedGender = "남";
                        break;
                    case R.id.radio_woman:
                        selectedGender = "여";
                        break;
                }
            }
        });

        binding.ivProfile.setOnClickListener(view -> {
            if (!UserInfoUpdateActivity.this.isFinishing()) {
                loaderHelper.imageLoaderDialogBuilder(launcher, this).show();
            }
        });

        getLoginUser();
    }

    private void getLoginUser() {
        currentUserItem = PreferenceHelper.getCurrentUser(getApplicationContext());
        binding.tvUserEmail.setText(currentUserItem.getUser_email());
        binding.tvUserName.setText(currentUserItem.getUser_name());
        binding.btnBirth.setText(currentUserItem.getUser_birth());

        if (currentUserItem.getUser_gender().equalsIgnoreCase("남")) {
            binding.radioGroup.check(R.id.radio_man);
            selectedGender = "남";
        } else {
            binding.radioGroup.check(R.id.radio_woman);
            selectedGender = "여";
        }

        selectedBirth = currentUserItem.getUser_birth();

        binding.editHeight.setText(currentUserItem.getUser_height());
        binding.editWeight.setText(currentUserItem.getUser_weight());


        Glide.with(binding.ivProfile.getContext())
                .load("http://54.180.178.116:8080/" + currentUserItem.getUser_profile())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(binding.ivProfile);

    }


    private void updateUser() {

        L.i(":::::updateUser");


        if (isEmtpy(binding.editHeight)) {
            MessageUtils.showLongToastMsg(getApplicationContext(), "키를 입력해주세요.");
            return;
        }

        if (isEmtpy(binding.editWeight)) {
            MessageUtils.showLongToastMsg(getApplicationContext(), "몸무게를 입력해주세요.");
            return;
        }

        String email = currentUserItem.getUser_email();
        String password = currentUserItem.getUser_pwd();
        String name = currentUserItem.getUser_name();
        String gender = selectedGender;
        String birth = selectedBirth;
        String height = binding.editHeight.getText().toString();
        String weight = binding.editWeight.getText().toString();
        String userProfile = currentUserItem.getUser_profile();

        UpdateUserRequest request = new UpdateUserRequest(email, password, name, gender, birth, height, weight, userProfile);


        if (mCurrentUri != null) {
            RequestBody requestBody;
            MultipartBody.Part body;
            List<MultipartBody.Part> arrBody = new ArrayList<>();
            File file = new File(FileUtils.getFilePathFromURI(getApplicationContext(), mCurrentUri));
            requestBody = RequestBody.create(file, MediaType.parse("multipart/form-data"));
            body = MultipartBody.Part.createFormData("photo", file.getName(), requestBody);
            arrBody.add(body);
            showProgressDialog("업로드 중입니다.");

            compositeDisposable.add(repository.uploadImg(arrBody)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate(this::hideProgressDialog)
                    .subscribe(response -> {
                        L.i("::::::::::::::::Current Thread : " + Thread.currentThread().getName());
                        L.i("[Rsponse] " + response);
                        request.setUser_profile(response.getPath());
                        setUserInfo(request);
                    }, throwable -> {
                        L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                        hideProgressDialog();
                        MessageUtils.showLongToastMsg(getApplicationContext(), "업로드에 실패하였습니다.");
                    }));
        } else {
            setUserInfo(request);
        }

    }

    private void setUserInfo(UpdateUserRequest request) {
        compositeDisposable.add(repository.userUpdate(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    L.i("::::getUserInfo response -> " + response);
                    if (response.getResult().equalsIgnoreCase("Success")) {
                        getUserInfo();
                    } else {
                        hideProgressDialog();
                        MessageUtils.showLongToastMsg(getApplicationContext(), "유저 정보 변경에 실패 하였습니다.");
                    }
                }, error -> {
                    hideProgressDialog();
                    MessageUtils.showLongToastMsg(getApplicationContext(), "다시 시도해주세요.");
                }));
    }


    private void getUserInfo() {
        compositeDisposable.add(repository.getUserInfo(new SingleRequest(PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_email()))
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(this::hideProgressDialog)
                .subscribe(response -> {
                    L.i("::::getUserInfo response -> " + response);
                    PreferenceHelper.setCurrentUser(getApplicationContext(), response);
                    MessageUtils.showLongToastMsg(getApplicationContext(), "유저정보 수정에 성공하였습니다.");
                    setResult(RESULT_OK);
                    finish();
                }, error -> {
                    MessageUtils.showLongToastMsg(getApplicationContext(), "다시 시도해주세요.");
                }));
    }


    private boolean isEmtpy(EditText editText) {
        return TextUtils.isEmpty(editText.getText()) || editText.getText().toString().equalsIgnoreCase("");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ImageLoaderHelper.OPEN_IMAGE_REQUEST_CODE) {
            Uri uri = null;
            if (loaderHelper.getmCameraUri() != null) {
                uri = loaderHelper.getmCameraUri();
            } else {
                if (data == null) {
                    return;
                }
                uri = data.getData();
            }
            L.e(":::::mUri " + uri);
            mCurrentUri = uri;
            setImageLoad(uri);
        }
    }

    private void setImageLoad(Uri result) {
        Glide.with(this)
                .load(result)
                .dontAnimate()
                .into(binding.ivProfile);
    }

}
