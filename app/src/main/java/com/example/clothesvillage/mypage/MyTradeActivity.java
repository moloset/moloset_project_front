package com.example.clothesvillage.mypage;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.dashboard.cody.CodyContentRegisterActivity;
import com.example.clothesvillage.databinding.ActivityMyTradeBinding;
import com.example.clothesvillage.model.FilterItem;
import com.example.clothesvillage.remote.request.TradeDeleteRequest;
import com.example.clothesvillage.remote.request.TradeListRequest;
import com.example.clothesvillage.remote.response.TradeListResponse;
import com.example.clothesvillage.saleFragment.TradeDetailActivity;
import com.example.clothesvillage.saleFragment.TradeListAdapter;
import com.example.clothesvillage.utils.PreferenceHelper;
import com.tedpark.tedonactivityresult.rx2.TedRxOnActivityResult;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class MyTradeActivity extends BaseActivity<ActivityMyTradeBinding> {
    private TradeListAdapter tradeListAdapter;


    @Override
    protected int layoutRes() {
        return R.layout.activity_my_trade;
    }

    @Override
    protected void onViewCreated() {

        binding.ivBack.setOnClickListener(view -> finish());
        initRecyclerView();
        onLoad();

    }

    private void initRecyclerView() {
        tradeListAdapter = new TradeListAdapter(this);
        binding.rvTrade.setLayoutManager(new LinearLayoutManager(this));
        binding.rvTrade.setHasFixedSize(true);
        binding.rvTrade.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        binding.rvTrade.setAdapter(tradeListAdapter);


        tradeListAdapter.setOnItemClickListener(position -> {
            TradeListResponse item = tradeListAdapter.getItem(position);
            Intent intent = new Intent(MyTradeActivity.this, TradeDetailActivity.class);
            intent.putExtra("trade_no", item.getTrade_no());
            intent.putExtra("myself", true);
            TedRxOnActivityResult.with(this)
                    .startActivityForResult(intent)
                    .subscribe(activityResult -> {
                        if (activityResult.getResultCode() == RESULT_OK) {
                            onLoad();
                        }

                    });
        });

        tradeListAdapter.setOnLongItemClickListener(position -> {
            TradeListResponse item = tradeListAdapter.getItem(position);
            new AlertDialog.Builder(MyTradeActivity.this).setMessage("삭제 하시겠습니까?").setNegativeButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    compositeDisposable.add(repository.tradeDelete(new TradeDeleteRequest(item.getTrade_no()))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                tradeListAdapter.removeItem(position);
                            }, throwable -> {
                                L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                            }));
                }
            }).show();
        });

    }


    private void onLoad() {

        FilterItem filterItem = null;
        if (PreferenceHelper.getFilterItem(getApplicationContext()) != null) {
            filterItem = PreferenceHelper.getFilterItem(getApplicationContext());
        } else {
            filterItem = new FilterItem();
        }

        List<String> filterCategory = new ArrayList<>();

        if (filterItem.isCategory_1()) {
            filterCategory.add("1");
        }

        if (filterItem.isCategory_2()) {
            filterCategory.add("2");
        }

        if (filterItem.isCategory_3()) {
            filterCategory.add("3");
        }

        if (filterItem.isCategory_4()) {
            filterCategory.add("4");
        }

        if (filterItem.isCategory_5()) {
            filterCategory.add("5");
        }

        if (filterItem.isCategory_6()) {
            filterCategory.add("6");
        }

        String clothes_category = getFilterClothesCategory(filterCategory);
        String height = filterItem.getHeight();
        String h_check = filterItem.isHeightEnable() ? "Y" : "N";
        String weight = filterItem.getHeight();
        String w_check = filterItem.isHeightEnable() ? "Y" : "N";
        String user_no = PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_no();

        TradeListRequest request = new TradeListRequest(clothes_category, height, h_check, weight, w_check, user_no);

        binding.loading.setVisibility(View.VISIBLE);
        compositeDisposable.add(repository.getTradeList(request)
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    binding.loading.setVisibility(View.GONE);
                })
                .subscribe(response -> {
                    tradeListAdapter.updateItems(response);
                }, throwable -> {
                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                }));


    }


    private String getFilterClothesCategory(List<String> list) {
        StringBuilder builder = new StringBuilder();
        if (null != list) {
            for (int i = 0; i < list.size(); i++) {
                if (false == "".equals(list.get(i))) {
                    if (1 < getValidCount(list)) {
                        if (getFirstValidIndex(list) == i) {
                            builder.append(list.get(i));
                        } else {
                            builder.append("," + list.get(i));
                        }
                    } else {
                        builder.append(list.get(i));
                    }
                }
            }
        }
        return builder.toString();
    }

    private int getValidCount(List<String> list) {
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (false == "".equals(list.get(i))) {
                count++;
            }
        }
        return count;
    }

    public int getFirstValidIndex(List<String> list) {
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            if (false == "".equals(list.get(i))) {
                return i;
            }
        }
        return -1;
    }


}
