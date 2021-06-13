package com.example.clothesvillage.dashboard;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseFragment;
import com.example.clothesvillage.base.OnItemClickListener;
import com.example.clothesvillage.closet.ClosetAdapter;
import com.example.clothesvillage.dashboard.cody.CodyContentRegisterActivity;
import com.example.clothesvillage.dashboard.cody.CodyDetailActivity;
import com.example.clothesvillage.dashboard.cody.CodyRegisterActivity;
import com.example.clothesvillage.dashboard.cody.CodySearchActivity;
import com.example.clothesvillage.dashboard.cody.CodySelectAdapter;
import com.example.clothesvillage.dashboard.cody.CodyType;
import com.example.clothesvillage.databinding.FragmentDashboardBinding;
import com.example.clothesvillage.home.WeatherDailyAdapter;
import com.example.clothesvillage.home.WeatherDetailActivity;
import com.example.clothesvillage.home.WeatherHourlyAdapter;
import com.example.clothesvillage.remote.request.ClothesListRequest;
import com.example.clothesvillage.remote.request.StyleListRequest;
import com.example.clothesvillage.remote.request.StyleSelectRequest;
import com.example.clothesvillage.remote.request.WeatherRequest;
import com.example.clothesvillage.remote.response.ClothesListResponse;
import com.example.clothesvillage.remote.response.StyleListResponse;
import com.example.clothesvillage.utils.PreferenceHelper;
import com.tedpark.tedonactivityresult.rx2.TedRxOnActivityResult;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import static android.app.Activity.RESULT_OK;

public class DashboardFragment extends BaseFragment<FragmentDashboardBinding> {
    private DashBoardAdapter dashBoardAdapter;


    @Override
    protected int layoutRes() {
        return R.layout.fragment_dashboard;
    }

    @Override
    protected void onViewCreated() {
        L.i("::::DashboardFragment");

        initRecyclerView();
        onLoad();

        binding.ivAdd.setOnClickListener(view -> TedRxOnActivityResult.with(getActivity())
                .startActivityForResult(new Intent(DashboardFragment.this.getActivity(), CodyRegisterActivity.class))
                .subscribe(activityResult -> {
                    if (activityResult.getResultCode() == RESULT_OK) {
                        onLoad();
                    }
                }));

        binding.ivSearch.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CodySearchActivity.class);
            startActivity(intent);
        });
    }

    private void initRecyclerView() {
        dashBoardAdapter = new DashBoardAdapter(getActivity()) {
            @Override
            public void onItemClick(StyleListResponse data) {
                L.i(":::아이탬 버튼 클릭" + data);
                Intent intent = new Intent(getActivity(), CodyDetailActivity.class);
                intent.putExtra("style_no",data.getStyle_no());
                intent.putExtra("user_no",Integer.parseInt(PreferenceHelper.getCurrentUser(getActivity()).getUser_no()));
                startActivity(intent);
            }

            @Override
            public void onLikeItemClick(int position, StyleListResponse data) {
                L.i(":::좋아요 버튼 클릭" + data);
                compositeDisposable.add(repository.styleSelect(new StyleSelectRequest(
                        data.getStyle_no(),
                        PreferenceHelper.getCurrentUser(getActivity()).getUser_no(),
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
        binding.rvStyle.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rvStyle.setHasFixedSize(true);
        binding.rvStyle.setAdapter(dashBoardAdapter);


    }


    private void onLoad() {
        binding.loading.setVisibility(View.VISIBLE);

        int user_no = Integer.parseInt(PreferenceHelper.getCurrentUser(getActivity()).getUser_no());
        String user_gender = PreferenceHelper.getCurrentUser(getActivity()).getUser_gender();
        String search_div = "2";
        String search_text = "";
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

}
