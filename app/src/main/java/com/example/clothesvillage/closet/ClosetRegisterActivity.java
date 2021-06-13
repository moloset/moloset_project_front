package com.example.clothesvillage.closet;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.base.OnItemClickListener;
import com.example.clothesvillage.dashboard.cody.CodySelectAdapter;
import com.example.clothesvillage.dashboard.cody.CodyType;
import com.example.clothesvillage.databinding.ActivityClosetRegisterBinding;
import com.example.clothesvillage.databinding.ActivityCodyRegisterBinding;
import com.example.clothesvillage.remote.ClothesRepository;
import com.example.clothesvillage.utils.FileUtils;
import com.example.clothesvillage.utils.ImageLoaderHelper;
import com.example.clothesvillage.utils.MessageUtils;
import com.example.clothesvillage.utils.PreferenceHelper;
import com.github.gabrielbb.cutout.CutOut;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ClosetRegisterActivity extends BaseActivity<ActivityClosetRegisterBinding> {
    private CodySelectAdapter codySelectAdapter;
    private ClothesRepository repository;
    private Uri mCurrentUri = null;

    @Override
    protected int layoutRes() {
        return R.layout.activity_closet_register;
    }

    @Override
    protected void onViewCreated() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        repository = ClothesRepository.getInstance();
        initRecyclerView();
        initCodeCategory();


        binding.ivBack.setOnClickListener(view -> finish());
        binding.ivPhoto.setOnClickListener(view -> {
            if (!ClosetRegisterActivity.this.isFinishing()) {
                loaderHelper.imageLoaderDialogBuilder(launcher, this).show();
            }
        });

        binding.tvNext.setOnClickListener(v -> {


            if (TextUtils.isEmpty(binding.editWriteContent.getText()) || binding.editWriteContent.getText().toString().equalsIgnoreCase("")) {
                MessageUtils.showLongToastMsg(getApplicationContext(), "글내용을 입력해주세요.");
                return;
            }

            if (mCurrentUri == null) {
                MessageUtils.showLongToastMsg(getApplicationContext(), "사진을 업로드해주세요.");
                return;
            }


            compositeDisposable.add(Observable.fromIterable(codySelectAdapter.getItemList())
                    .filter(CodyType::isSelected)
                    .subscribe(result -> {
                        L.e("::::result " + result.getName());


                        RequestBody requestBody;
                        MultipartBody.Part body;
                        LinkedHashMap<String, RequestBody> mapRequestBody = new LinkedHashMap<String, RequestBody>();
                        List<MultipartBody.Part> arrBody = new ArrayList<>();


                        File file = new File(FileUtils.getFilePathFromURI(getApplicationContext(), mCurrentUri));
                        L.e("::::file " + file);
                        L.e(":::owner_no " + PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_no());
                        requestBody = RequestBody.create(file, MediaType.parse("multipart/form-data"));
                        mapRequestBody.put("clothes_category", RequestBody.create(result.getCategoryType(), MediaType.parse("text/plain")));
                        mapRequestBody.put("clothes_name", RequestBody.create(result.getName(), MediaType.parse("text/plain")));
                        mapRequestBody.put("clothes_memo", RequestBody.create(binding.editWriteContent.getText().toString(), MediaType.parse("text/plain")));
                        mapRequestBody.put("owner_no", RequestBody.create(PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_no(), MediaType.parse("text/plain")));
                        body = MultipartBody.Part.createFormData("photo", file.getName(), requestBody);
                        arrBody.add(body);

                        showProgressDialog("업로드 중입니다.");
                        compositeDisposable.add(repository.uploadFile(mapRequestBody, arrBody)
                                .observeOn(AndroidSchedulers.mainThread())
                                .doAfterTerminate(() -> {
                                    hideProgressDialog();
                                })
                                .subscribe(response -> {
                                    L.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>[File UPload]  성공<<<<<<<<<<<<<");
                                    L.i("::::::::::::::::Current Thread : " + Thread.currentThread().getName());
                                    MessageUtils.showLongToastMsg(getApplicationContext(), "업로드에 성공하였습니다.");
                                    setResult(RESULT_OK);
                                    finish();
                                }, throwable -> {
                                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                                    MessageUtils.showLongToastMsg(getApplicationContext(), "업로드에 실패하였습니다.");
                                }));
                    }, error -> {
                        L.e("::::e " + error);
                    }));
        });


    }


    private void initRecyclerView() {
        codySelectAdapter = new CodySelectAdapter(ClosetRegisterActivity.this);
        binding.rvContentCategory.setHasFixedSize(true);
        binding.rvContentCategory.setAdapter(codySelectAdapter);
    }

    private void initCodeCategory() {
        ArrayList<CodyType> dataSet = new ArrayList<>();
        dataSet.add(new CodyType("아우터", "3", true));
        dataSet.add(new CodyType("상의", "1", false));
        dataSet.add(new CodyType("하의", "2", false));
        dataSet.add(new CodyType("신발", "4", false));
        dataSet.add(new CodyType("가방", "5", false));
        dataSet.add(new CodyType("기타", "6", false));

        codySelectAdapter.addItems(dataSet);
        codySelectAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                CodyType selectItem = codySelectAdapter.getItem(position);
                compositeDisposable.add(Observable.fromIterable(codySelectAdapter.getItemList())
                        .map(codyType -> {
                            if (selectItem.getName().equalsIgnoreCase(codyType.getName())) {
                                codyType.setSelected(true);
                            } else {
                                codyType.setSelected(false);
                            }
                            return codyType;
                        })
                        .toList()
                        .subscribe(result -> {
                            codySelectAdapter.updateItems(result);
                        }, error -> {
                            L.e("::::e " + error);
                        }));
            }
        });
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
            CutOut.activity().src(uri).bordered().start(this);

        }else if(requestCode == CutOut.CUTOUT_ACTIVITY_REQUEST_CODE){
            L.i("[CUTOUT_ACTIVITY_REQUEST_CODE] " + resultCode);
            switch (resultCode) {
                case Activity.RESULT_OK:
                    Uri imageUri = CutOut.getUri(data);
                    mCurrentUri = imageUri;
                    binding.ivCardAdd.setVisibility(View.GONE);
                    setImageLoad(imageUri);
                    break;
                case CutOut.CUTOUT_ACTIVITY_RESULT_ERROR_CODE:
                    Exception ex = CutOut.getError(data);
                    break;
                default:
                    System.out.print("User cancelled the CutOut screen");
            }
        }
    }

    private void setImageLoad(Uri result) {
        Glide.with(this)
                .load(result)
                .centerCrop()
                .into(binding.ivPhoto);
    }

}
