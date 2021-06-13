package com.example.clothesvillage.saleFragment;

import android.content.Intent;
import android.content.res.Resources;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseFragment;
import com.example.clothesvillage.databinding.FragmentSaleBinding;
import com.example.clothesvillage.model.FilterItem;
import com.example.clothesvillage.remote.request.TradeListRequest;
import com.example.clothesvillage.remote.response.TradeListResponse;
import com.example.clothesvillage.utils.MessageUtils;
import com.example.clothesvillage.utils.PreferenceHelper;
import com.tedpark.tedonactivityresult.rx2.TedRxOnActivityResult;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

import static android.app.Activity.RESULT_OK;
import static io.reactivex.rxjava3.core.Observable.error;

public class SaleFragment extends BaseFragment<FragmentSaleBinding> {

    private TradeListAdapter tradeListAdapter;
    private List<TradeListResponse> currentList;

    @Override

    protected int layoutRes() {
        return R.layout.fragment_sale;
    }

    @Override
    protected void onViewCreated() {

        initRecyclerView();
        binding.ivAdd.setOnClickListener(v -> TedRxOnActivityResult.with(getActivity())
                .startActivityForResult(new Intent(getActivity(), TradeRegisterActivity.class))
                .subscribe(activityResult -> {
                    L.i(":::::::activityResult " + activityResult.getResultCode());
                    if (activityResult.getResultCode() == RESULT_OK) {
                        onLoad();
                    }
                }));
        binding.ivSort.setOnClickListener(v ->
                TedRxOnActivityResult.with(getActivity())
                        .startActivityForResult(new Intent(getActivity(), FilterActivity.class))
                        .subscribe(activityResult -> {
                            L.i(":::::::activityResult " + activityResult.getResultCode());
                            if (activityResult.getResultCode() == RESULT_OK) {
                                onLoad();
                            }
                        }));

        binding.btnSearch.setOnClickListener(v -> {
            if(binding.editSearch.getText().toString().equalsIgnoreCase("")){
                MessageUtils.showLongToastMsg(getActivity(),"검색어를 입력해주세요.");
                return;
            }
            compositeDisposable.add(Observable.fromIterable(currentList)
                    .filter(result -> result.getTrade_name().contains(binding.editSearch.getText().toString()))
                    .switchIfEmpty(error(new Resources.NotFoundException(binding.editSearch.getText().toString())))
                    .toList()
                    .subscribe(result -> {
                        tradeListAdapter.updateItems(result);
                    }, error -> {
                        L.e("::::e " + error);
                        if(error instanceof Resources.NotFoundException){
                            MessageUtils.showLongToastMsg(getActivity(),error.getMessage() +"의 검색 결과가 없습니다");
                        }
                    }));
        });

        onLoad();
    }

    private void initRecyclerView() {
        tradeListAdapter = new TradeListAdapter(getActivity());
        binding.rvTrade.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvTrade.setHasFixedSize(true);
        binding.rvTrade.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        binding.rvTrade.setAdapter(tradeListAdapter);


        tradeListAdapter.setOnItemClickListener(position -> {
            TradeListResponse item = tradeListAdapter.getItem(position);
            Intent intent = new Intent(getActivity(), TradeDetailActivity.class);
            intent.putExtra("trade_no", item.getTrade_no());
            startActivity(intent);
        });


    }


    private void onLoad() {

        FilterItem filterItem = null;
        if (PreferenceHelper.getFilterItem(getActivity()) != null) {
            filterItem = PreferenceHelper.getFilterItem(getActivity());
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
        String user_no = "0";

        TradeListRequest request = new TradeListRequest(clothes_category, height, h_check, weight, w_check, user_no);

        binding.loading.setVisibility(View.VISIBLE);
        compositeDisposable.add(repository.getTradeList(request)
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    binding.loading.setVisibility(View.GONE);
                })
                .subscribe(response -> {
                    this.currentList = response;
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
