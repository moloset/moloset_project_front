package com.example.clothesvillage.saleFragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.base.OnItemClickListener;
import com.example.clothesvillage.dashboard.cody.CodySelectAdapter;
import com.example.clothesvillage.dashboard.cody.CodyType;
import com.example.clothesvillage.databinding.ActivityTradeDetailRegisterBinding;
import com.example.clothesvillage.databinding.ActivityTradeRegisterBinding;
import com.example.clothesvillage.remote.request.ClothesListRequest;
import com.example.clothesvillage.remote.request.TradeInsertRequest;
import com.example.clothesvillage.remote.response.ClothesListResponse;
import com.example.clothesvillage.utils.MessageUtils;
import com.example.clothesvillage.utils.PreferenceHelper;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.tedpark.tedonactivityresult.rx2.TedRxOnActivityResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

import static io.reactivex.rxjava3.core.Observable.error;

public class TradeRegisterDetailActivity extends BaseActivity<ActivityTradeDetailRegisterBinding> {
    private final String baseUrl = "http://54.180.178.116:8080/";

    private String selectedTradeMode;
    private String selectedCategoryMode;
    private String selectedAddress;

    @Override
    protected int layoutRes() {
        return R.layout.activity_trade_detail_register;
    }

    @Override
    protected void onViewCreated() {

        initClothesImage();
        setTradeTypeSpinner();
        initMap();
        postTrade();

        binding.ivBack.setOnClickListener(v -> finish());
    }



    private void postTrade() {
        binding.tvComplete.setOnClickListener(v -> {
            String clothes_no = getIntent().getStringExtra("clothes_no");
            String clothes_category = selectedCategoryMode;
            String trade_name = binding.editTradeName.getText() == null ? "" : binding.editTradeName.getText().toString();;
            String trade_content = binding.editContent.getText() == null ? "" : binding.editContent.getText().toString();
            String trade_price = binding.editPrice.getText() == null ? "" : binding.editPrice.getText().toString();
            String trade_case = selectedTradeMode;
            String trade_area_name = selectedAddress == null ? "" : selectedAddress;
            String trade_area = "";

            TradeInsertRequest request = new TradeInsertRequest(
                    clothes_no,
                    clothes_category,
                    trade_name,
                    trade_content,
                    trade_price,
                    trade_case,
                    trade_area_name,
                    trade_area
            );

            compositeDisposable.add(repository.tradeInsert(request)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        L.i("::::response -> " + response);
                        MessageUtils.showLongToastMsg(getApplicationContext(), "거래등록 성공하였습니다.");
                        setResult(RESULT_OK);
                        finish();
                    }, error -> {
                        MessageUtils.showLongToastMsg(getApplicationContext(), "다시 시도해주세요.");
                    }));
        });
    }

    private void initClothesImage() {
        Glide.with(binding.ivPhoto.getContext())
                .load(baseUrl + getIntent().getStringExtra("photo_url"))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(binding.ivPhoto);
    }

    private void setTradeTypeSpinner() {
        ArrayAdapter spinnerTrade = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.filter_trade));
        binding.spinnerTradeType.setAdapter(spinnerTrade);
        binding.spinnerTradeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTradeMode = getResources().getStringArray(R.array.filter_trade)[position];

                switch (getResources().getStringArray(R.array.filter_trade)[position]) {
                    case "거래":
                        selectedTradeMode ="1";
                        break;
                    case "대여":
                        selectedTradeMode ="2";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter spinnerCategory = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.filter_category));
        binding.spinnerCategoryType.setAdapter(spinnerCategory);
        binding.spinnerCategoryType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (getResources().getStringArray(R.array.filter_category)[position]) {
                    case "아우터":
                        selectedCategoryMode ="3";
                        break;
                    case "상의":
                        selectedCategoryMode ="1";
                        break;
                    case "하의":
                        selectedCategoryMode ="2";
                        break;
                    case "신발":
                        selectedCategoryMode ="4";
                        break;
                    case "가방":
                        selectedCategoryMode ="5";
                        break;
                    case "기타":
                        selectedCategoryMode ="6";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initMap() {
        binding.tvAddress.setOnClickListener(v -> Dexter.withActivity(TradeRegisterDetailActivity.this).withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                L.i("::report allPermission " + report.areAllPermissionsGranted());
                if (report.areAllPermissionsGranted()) {


                    TedRxOnActivityResult.with(TradeRegisterDetailActivity.this)
                            .startActivityForResult(new Intent(TradeRegisterDetailActivity.this, TradeMapActivity.class))
                            .subscribe(activityResult -> {
                                L.i(":::::::activityResult " + activityResult.getResultCode());
                                if (activityResult.getResultCode() == RESULT_OK) {
                                    if (activityResult.getData() == null) return;
                                    Place place = (Place) activityResult.getData().getParcelableExtra("EXTRA_PLACE_KEY");
                                    selectedAddress =place.getName();
                                    binding.tvAddress.setText(place.getName());
                                }
                            });
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check());
    }

}
