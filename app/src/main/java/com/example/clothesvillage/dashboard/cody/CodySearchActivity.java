package com.example.clothesvillage.dashboard.cody;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.dashboard.DashBoardAdapter;
import com.example.clothesvillage.databinding.ActivityCodyDetailBinding;
import com.example.clothesvillage.databinding.ActivityCodySearchBinding;
import com.example.clothesvillage.model.HashTag;
import com.example.clothesvillage.remote.request.ReplyInsertRequest;
import com.example.clothesvillage.remote.request.ReplyListRequest;
import com.example.clothesvillage.remote.request.StyleDetailRequest;
import com.example.clothesvillage.remote.request.StyleListRequest;
import com.example.clothesvillage.remote.request.StylePhotoListRequest;
import com.example.clothesvillage.remote.request.StyleSelectRequest;
import com.example.clothesvillage.remote.response.StyleListResponse;
import com.example.clothesvillage.utils.MessageUtils;
import com.example.clothesvillage.utils.PreferenceHelper;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

import static com.example.clothesvillage.utils.KeyboardUtilsKt.hideKeyboard;

public class CodySearchActivity extends BaseActivity<ActivityCodySearchBinding> {
    private DashBoardAdapter dashBoardAdapter;

    @Override
    protected int layoutRes() {
        return R.layout.activity_cody_search;
    }

    @Override
    protected void onViewCreated() {

        initRecyclerView();
        binding.ivBack.setOnClickListener(view -> finish());
        binding.ivSearch.setOnClickListener(v -> {
            if(TextUtils.isEmpty(binding.editSearch.getText()) || binding.editSearch.getText().toString().equalsIgnoreCase("")){
                MessageUtils.showLongToastMsg(getApplicationContext(),"검색어를 입력해주세요.");
                return;
            }
            onLoad();
        });
    }


    private void onLoad() {
        binding.loading.setVisibility(View.VISIBLE);

        int user_no = Integer.parseInt(PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_no());
        String user_gender = PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_gender();
        String search_div = "2";
        String search_text = binding.editSearch.getText().toString();
        compositeDisposable.add(repository.getStyleList(new StyleListRequest(user_no, user_gender, search_div,search_text))
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    binding.loading.setVisibility(View.GONE);
                })
                .subscribe(response -> {
                    L.i(":::::::::::response " + response);
                    dashBoardAdapter.updateItems(response);
                }, throwable -> {
                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                }));
    }

    private void initRecyclerView() {
        dashBoardAdapter = new DashBoardAdapter(this) {
            @Override
            public void onItemClick(StyleListResponse data) {
                L.i(":::아이탬 버튼 클릭" + data);
                Intent intent = new Intent(CodySearchActivity.this, CodyDetailActivity.class);
                intent.putExtra("style_no",data.getStyle_no());
                intent.putExtra("user_no",Integer.parseInt(PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_no()));
                startActivity(intent);
            }

            @Override
            public void onLikeItemClick(int position, StyleListResponse data) {
                L.i(":::좋아요 버튼 클릭" + data);
                compositeDisposable.add(repository.styleSelect(new StyleSelectRequest(
                        data.getStyle_no(),
                        PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_no(),
                        data.getGoodCheck()
                ))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            L.i("::::response " + response);
                            if (response.getResult().equalsIgnoreCase("Y")) {
                                data.setGoodCheck("Y");
                            } else {
                                data.setGoodCheck("");
                            }
                            dashBoardAdapter.updateItem(position, data);
                        }, throwable -> {
                            L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                        }));
            }
        };
        binding.rvStyle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.rvStyle.setHasFixedSize(true);
        binding.rvStyle.setAdapter(dashBoardAdapter);


    }





}
