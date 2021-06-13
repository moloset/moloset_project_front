package com.example.clothesvillage.mypage;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.base.OnItemClickListener;
import com.example.clothesvillage.dashboard.DashBoardAdapter;
import com.example.clothesvillage.dashboard.cody.CodyContentRegisterActivity;
import com.example.clothesvillage.dashboard.cody.CodyDetailActivity;
import com.example.clothesvillage.dashboard.cody.CodyRegisterActivity;
import com.example.clothesvillage.databinding.ActivityMyFeedBinding;
import com.example.clothesvillage.databinding.ActivityMyTradeBinding;
import com.example.clothesvillage.model.FilterItem;
import com.example.clothesvillage.remote.request.StyleDeleteRequest;
import com.example.clothesvillage.remote.request.StyleListRequest;
import com.example.clothesvillage.remote.request.StyleSelectRequest;
import com.example.clothesvillage.remote.request.TradeListRequest;
import com.example.clothesvillage.remote.response.StyleListResponse;
import com.example.clothesvillage.remote.response.TradeListResponse;
import com.example.clothesvillage.saleFragment.TradeListAdapter;
import com.example.clothesvillage.utils.PreferenceHelper;
import com.tedpark.tedonactivityresult.rx2.TedRxOnActivityResult;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class MyFeedActivity extends BaseActivity<ActivityMyFeedBinding> {
    private MyFeedAdapter myFeedAdapter;


    @Override
    protected int layoutRes() {
        return R.layout.activity_my_feed;
    }

    @Override
    protected void onViewCreated() {

        binding.ivBack.setOnClickListener(view -> finish());
        initRecyclerView();
        onLoad();

    }

    private void initRecyclerView() {
        myFeedAdapter = new MyFeedAdapter(this) {
            @Override
            void onItemClick(int position,StyleListResponse data) {
                L.i(":::::::아이탬클릭 " + data);
                Intent intent = new Intent(MyFeedActivity.this, CodyDetailActivity.class);
                intent.putExtra("style_no",data.getStyle_no());
                intent.putExtra("user_no",Integer.parseInt(PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_no()));
                startActivity(intent);
            }

            @Override
            void onClickMenu(View view,int position, StyleListResponse data) {
                PopupMenu popup = new PopupMenu(getApplicationContext(),view);
                getMenuInflater().inflate(R.menu.card_menu,popup.getMenu());
                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()){
//                        case R.id.modify:
//                            TedRxOnActivityResult.with(MyFeedActivity.this)
//                                    .startActivityForResult(new Intent(MyFeedActivity.this, CodyContentRegisterActivity.class).putExtra("clothes_no", clothes_no))
//                                    .subscribe(activityResult -> {
//                                        if (activityResult.getResultCode() == RESULT_OK) {
//                                            setResult(RESULT_OK);
//                                            finish();
//                                        }
//
//                                    });
//                            return true;
                        case R.id.delete:
                            compositeDisposable.add(repository.styleDelete(new StyleDeleteRequest(data.getStyle_no()))
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(response -> {
                                        myFeedAdapter.removeItem(position);
                                    }, throwable -> {
                                        L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                                    }));
                            return true;
                    }
                    return false;
                });
                popup.show();
            }

        };
        binding.rvMyFeed.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMyFeed.setHasFixedSize(true);
        binding.rvMyFeed.setAdapter(myFeedAdapter);
    }



    private void onLoad() {
        binding.loading.setVisibility(View.VISIBLE);

        int user_no = Integer.parseInt(PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_no());
        String user_gender = PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_gender();
        String search_div = "1";
        String search_text = "";
        compositeDisposable.add(repository.getStyleList(new StyleListRequest(user_no, user_gender, search_div,search_text))
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    binding.loading.setVisibility(View.GONE);
                })
                .subscribe(response -> {
                    L.i(":::::::::::response " + response);
                    myFeedAdapter.updateItems(response);
                }, throwable -> {
                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                }));
    }


}
