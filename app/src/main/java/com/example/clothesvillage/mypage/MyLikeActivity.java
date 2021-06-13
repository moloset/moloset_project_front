package com.example.clothesvillage.mypage;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.dashboard.DashBoardAdapter;
import com.example.clothesvillage.databinding.ActivityMyLikeBinding;
import com.example.clothesvillage.remote.request.StyleListRequest;
import com.example.clothesvillage.remote.request.StyleSelectRequest;
import com.example.clothesvillage.remote.request.StyleUserSelectRequest;
import com.example.clothesvillage.remote.response.StyleListResponse;
import com.example.clothesvillage.remote.response.UserSelectListResponse;
import com.example.clothesvillage.utils.PreferenceHelper;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class MyLikeActivity extends BaseActivity<ActivityMyLikeBinding> {
    private MyLikeAdapter myLikeAdapter;
    private int user_no;

    @Override
    protected int layoutRes() {
        return R.layout.activity_my_like;
    }

    @Override
    protected void onViewCreated() {

        binding.ivBack.setOnClickListener(view -> finish());

        initRecyclerView();
        onLoad();

    }

    private void initRecyclerView() {
        myLikeAdapter = new MyLikeAdapter(this) {
            @Override
            public void onItemClick(UserSelectListResponse data) {

            }

            @Override
            public void onLikeItemClick(int position, UserSelectListResponse data) {
                compositeDisposable.add(repository.styleSelect(new StyleSelectRequest(
                        data.getStyle_no(),
                        PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_no(),
                        data.getGoodCheck()))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            myLikeAdapter.removeItem(position);
                        }, throwable -> {
                            L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                        }));
            }

        };
        binding.rvMyFeed.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMyFeed.setHasFixedSize(true);
        binding.rvMyFeed.setAdapter(myLikeAdapter);
    }


    private void onLoad() {
        binding.loading.setVisibility(View.VISIBLE);

        int user_no = Integer.parseInt(PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_no());
        compositeDisposable.add(repository.getUserSelectList(new StyleUserSelectRequest(user_no))
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    binding.loading.setVisibility(View.GONE);
                })
                .subscribe(response -> {
                    L.i(":::::::::::response " + response);
                    myLikeAdapter.updateItems(response);
                }, throwable -> {
                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                }));
    }



}
