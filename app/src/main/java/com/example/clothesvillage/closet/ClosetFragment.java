package com.example.clothesvillage.closet;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseFragment;
import com.example.clothesvillage.base.OnItemClickListener;
import com.example.clothesvillage.dashboard.cody.CodySelectAdapter;
import com.example.clothesvillage.dashboard.cody.CodyType;
import com.example.clothesvillage.databinding.FragmentClosetBinding;
import com.example.clothesvillage.remote.request.ClothesListRequest;
import com.example.clothesvillage.remote.response.ClothesListResponse;
import com.example.clothesvillage.saleFragment.TradeRegisterActivity;
import com.example.clothesvillage.utils.PreferenceHelper;
import com.tedpark.tedonactivityresult.rx2.TedRxOnActivityResult;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

import static android.app.Activity.RESULT_OK;

public class ClosetFragment extends BaseFragment<FragmentClosetBinding> {
    private CodySelectAdapter codySelectAdapter;
    private ClosetAdapter closetAdapter;
    private ArrayList<CodyType> dataSet = new ArrayList<>();
    @Override
    protected int layoutRes() {
        return R.layout.fragment_closet;
    }

    @Override
    protected void onViewCreated() {
        initRecyclerView();
        initData();
        initCodeCategory();

        binding.ivAdd.setOnClickListener(view ->
                TedRxOnActivityResult.with(getActivity())
                .startActivityForResult(new Intent(ClosetFragment.this.getActivity(), ClosetRegisterActivity.class))
                .subscribe(result -> {
                    if(result.getResultCode() == RESULT_OK){
                        initData();
                        codySelectAdapter.addItems(dataSet);
                        onLoad("A");
                    }
                }, error -> {

                }));
        onLoad("A");
    }


    private void onLoad(String clothes_category) {
        L.i(":::ON LOAD " + clothes_category);
        binding.loading.setVisibility(View.VISIBLE);
        compositeDisposable.add(repository.getClothesList(new ClothesListRequest(Integer.parseInt(PreferenceHelper.getCurrentUser(getActivity()).getUser_no()), clothes_category))
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    binding.loading.setVisibility(View.GONE);
                })
                .subscribe(response -> {
                    closetAdapter.updateItems(response);
                }, throwable -> {
                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                }));
    }


    private void initRecyclerView() {
        codySelectAdapter = new CodySelectAdapter(getActivity());
        binding.rvContentCategory.setHasFixedSize(true);
        binding.rvContentCategory.setAdapter(codySelectAdapter);


        closetAdapter = new ClosetAdapter(getActivity());
        binding.rvPhoto.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        binding.rvPhoto.setHasFixedSize(true);
        binding.rvPhoto.setAdapter(closetAdapter);

        closetAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ClothesListResponse item = closetAdapter.getItem(position);
                int owner_no = item.getOwner_no();
                int clothes_no = item.getClothes_no();

                Intent intent = new Intent(getActivity(),ClosetDetailActivity.class);
                intent.putExtra("owner_no",owner_no);
                intent.putExtra("clothes_no",clothes_no);

                TedRxOnActivityResult.with(getActivity())
                        .startActivityForResult(intent)
                        .subscribe(activityResult -> {
                            L.i(":::::::activityResult " + activityResult.getResultCode());
                            if (activityResult.getResultCode() == RESULT_OK) {
                                initData();
                                codySelectAdapter.addItems(dataSet);
                                onLoad("A");
                            }
                        });
            }
        });
    }

    private void initData(){
        dataSet.clear();
        dataSet.add(new CodyType("ALL", "A", true));
        dataSet.add(new CodyType("아우터", "3", false));
        dataSet.add(new CodyType("상의", "1", false));
        dataSet.add(new CodyType("하의", "2", false));
        dataSet.add(new CodyType("신발", "4", false));
        dataSet.add(new CodyType("가방", "5", false));
        dataSet.add(new CodyType("기타", "6", false));
    }


    private void initCodeCategory() {
        codySelectAdapter.addItems(dataSet);
        codySelectAdapter.setOnItemClickListener(position -> {
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
        });
    }


}
