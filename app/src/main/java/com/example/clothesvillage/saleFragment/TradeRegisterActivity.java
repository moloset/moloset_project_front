package com.example.clothesvillage.saleFragment;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.base.OnItemClickListener;
import com.example.clothesvillage.dashboard.cody.CodySelectAdapter;
import com.example.clothesvillage.dashboard.cody.CodyType;
import com.example.clothesvillage.databinding.ActivityTradeRegisterBinding;
import com.example.clothesvillage.remote.request.ClothesListRequest;
import com.example.clothesvillage.remote.response.ClothesListResponse;
import com.example.clothesvillage.utils.MessageUtils;
import com.example.clothesvillage.utils.PreferenceHelper;
import com.tedpark.tedonactivityresult.rx2.TedRxOnActivityResult;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

import static io.reactivex.rxjava3.core.Observable.error;

public class TradeRegisterActivity extends BaseActivity<ActivityTradeRegisterBinding> {
    private CodySelectAdapter codySelectAdapter;
    private TradeAdapter tradeAdapter;



    @Override
    protected int layoutRes() {
        return R.layout.activity_trade_register;
    }

    @Override
    protected void onViewCreated() {


        initRecyclerView();
        initCodeCategory();
        initTrade();

        binding.ivBack.setOnClickListener(view -> finish());
        binding.tvNext.setOnClickListener(v -> {
            compositeDisposable.add(Observable.fromIterable(tradeAdapter.getItemList())
                    .filter(CodyType::isSelected)
                    .switchIfEmpty(error(new NullPointerException()))
                    .subscribe(result -> {
                        L.i("::::::::::아이탬 목록.." + result);
                        Intent intent = new Intent(TradeRegisterActivity.this, TradeRegisterDetailActivity.class);
                        intent.putExtra("photo_url", result.getName());
                        intent.putExtra("clothes_no", result.getCategoryType());

                        TedRxOnActivityResult.with(this)
                                .startActivityForResult(intent)
                                .subscribe(activityResult -> {
                                    if (activityResult.getResultCode() == RESULT_OK) {
                                        setResult(RESULT_OK);
                                        finish();
                                    }

                                });
                    }, error -> {
                        L.e("::::e " + error);
                        MessageUtils.showLongToastMsg(getApplicationContext(),"이미지를 선택해주세요.");
                    }));


        });
        onLoad("A");
    }

    private void initRecyclerView() {
        codySelectAdapter = new CodySelectAdapter(TradeRegisterActivity.this);
        binding.rvContentCategory.setHasFixedSize(true);
        binding.rvContentCategory.setAdapter(codySelectAdapter);

        tradeAdapter = new TradeAdapter(TradeRegisterActivity.this);
        binding.rvPhoto.setLayoutManager(new GridLayoutManager(TradeRegisterActivity.this, 3));
        binding.rvPhoto.setHasFixedSize(true);
        binding.rvPhoto.setAdapter(tradeAdapter);

    }

    private void initTrade() {
        tradeAdapter.setOnItemClickListener(position -> {
            CodyType selectItem = tradeAdapter.getItem(position);
            compositeDisposable.add(Observable.fromIterable(tradeAdapter.getItemList())
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
                        tradeAdapter.updateItems(result);
                    }, error -> {
                        L.e("::::e " + error);
                    }));
        });
    }


    private void initCodeCategory() {
        ArrayList<CodyType> dataSet = new ArrayList<>();
        dataSet.add(new CodyType("ALL", "A", true));
        dataSet.add(new CodyType("아우터", "3", false));
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
                            onLoad(selectItem.getCategoryType());
                            codySelectAdapter.updateItems(result);
                        }, error -> {
                            L.e("::::e " + error);
                        }));
            }
        });
    }


    private void onLoad(String clothes_category) {
        L.i(":::ON LOAD " + clothes_category);
        binding.loading.setVisibility(View.VISIBLE);
        compositeDisposable.add(repository.getClothesList(new ClothesListRequest(Integer.parseInt(PreferenceHelper.getCurrentUser(getApplicationContext()).getUser_no()), clothes_category))
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    binding.loading.setVisibility(View.GONE);
                })
                .subscribe(response -> {

                    List<CodyType> list = new ArrayList<>();
                    for (int i = 0; i < response.size(); i++) {
                        ClothesListResponse item = response.get(i);
                        list.add(new CodyType(item.getPhoto(), String.valueOf(item.getClothes_no()), false));
                    }
                    tradeAdapter.updateItems(list);
                }, throwable -> {
                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                }));
    }


}
