package com.example.clothesvillage.closet;

import android.content.Intent;
import android.net.Uri;
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
import com.example.clothesvillage.databinding.ActivityClosetDetailBinding;
import com.example.clothesvillage.databinding.ActivityClosetRegisterBinding;
import com.example.clothesvillage.remote.ClothesRepository;
import com.example.clothesvillage.remote.request.ClothesDeleteRequest;
import com.example.clothesvillage.remote.request.ClothesDetailRequest;
import com.example.clothesvillage.remote.request.ClothesUpdateRequest;
import com.example.clothesvillage.remote.request.WeatherRequest;
import com.example.clothesvillage.remote.response.ClothesDetailResponse;
import com.example.clothesvillage.utils.FileUtils;
import com.example.clothesvillage.utils.ImageLoaderHelper;
import com.example.clothesvillage.utils.MessageUtils;
import com.example.clothesvillage.utils.PreferenceHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ClosetDetailActivity extends BaseActivity<ActivityClosetDetailBinding> {
    private final String baseUrl = "http://54.180.178.116:8080/";
    private int owner_no;
    private int clothes_no;

    private ClothesDetailResponse currentResponse;

    @Override
    protected int layoutRes() {
        return R.layout.activity_closet_detail;
    }

    @Override
    protected void onViewCreated() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        binding.ivBack.setOnClickListener(view -> finish());
        binding.ivDelete.setOnClickListener(v -> compositeDisposable.add(repository.clothesDelete(new ClothesDeleteRequest(owner_no, clothes_no))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if(response.getResult().equalsIgnoreCase("success")){
                        MessageUtils.showLongToastMsg(getApplicationContext(),"삭제 되었습니다.");
                        setResult(RESULT_OK);
                        finish();
                    }else if(response.getResult().equalsIgnoreCase("failTradeInfo")){
                        MessageUtils.showLongToastMsg(getApplicationContext(),"거래 게시판에 등록되어있어 삭제불가 합니다.");
                    }else{
                        MessageUtils.showLongToastMsg(getApplicationContext(),"코디 게시판에 등록되어있어 삭제불가 합니다.");
                    }
                }, throwable -> {
                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                })));

        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.editWriteContent.getText().toString().equalsIgnoreCase("")){
                    MessageUtils.showLongToastMsg(getApplicationContext(),"내용을 채워주세요.");
                    return;
                }
                ClothesUpdateRequest request = new ClothesUpdateRequest();
                request.setClothes_no(currentResponse.getClothes_no());
                request.setClothes_category(currentResponse.getClothes_category());
                request.setClothes_name(currentResponse.getClothes_name());
                request.setClothes_memo(binding.editWriteContent.getText().toString());
                request.setPhoto(currentResponse.getPhoto());
                request.setOwner_no(String.valueOf(currentResponse.getOwner_no()));
                compositeDisposable.add(repository.clothesUpdate(request)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            if(response.getResult().equalsIgnoreCase("success")){
                                MessageUtils.showLongToastMsg(getApplicationContext(),"수정완료");
                            }else{
                                MessageUtils.showLongToastMsg(getApplicationContext(),"거래 게시판에 등록되어있어 수정 불가 합니다.");
                            }
                        }, throwable -> {
                            L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                        }));
            }
        });

        owner_no = getIntent().getIntExtra("owner_no", -1);
        clothes_no = getIntent().getIntExtra("clothes_no", -1);
        onLoad(owner_no, clothes_no);

    }

    private void onLoad(int owner_no, int clothes_no) {
        compositeDisposable.add(repository.getClothesDetail(new ClothesDetailRequest(owner_no, clothes_no))
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {

                })
                .subscribe(response -> {
                    this.currentResponse = response;
                    binding.editWriteContent.setText(response.getClothes_memo());
                    Glide.with(binding.ivPhoto.getContext())
                            .load(baseUrl + response.getPhoto())
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(binding.ivPhoto);
                }, throwable -> {
                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                }));
    }


}
