package com.example.clothesvillage.home;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.base.OnItemClickListener;
import com.example.clothesvillage.closet.ClosetAdapter;
import com.example.clothesvillage.dashboard.cody.CodyContentRegisterActivity;
import com.example.clothesvillage.dashboard.cody.CodySelectAdapter;
import com.example.clothesvillage.dashboard.cody.CodyType;
import com.example.clothesvillage.dashboard.cody.PhotoSelectAdapter;
import com.example.clothesvillage.databinding.ActivityCodyRegisterBinding;
import com.example.clothesvillage.databinding.ActivityWeatherDetailBinding;
import com.example.clothesvillage.remote.request.ClothesListRequest;
import com.example.clothesvillage.remote.request.WeatherRequest;
import com.example.clothesvillage.remote.response.ClothesListResponse;
import com.example.clothesvillage.utils.MessageUtils;
import com.example.clothesvillage.utils.PreferenceHelper;
import com.tedpark.tedonactivityresult.rx2.TedRxOnActivityResult;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;

public class WeatherDetailActivity extends BaseActivity<ActivityWeatherDetailBinding> {
    private WeatherHourlyAdapter weatherHourlyAdapter;
    private WeatherDailyAdapter weatherDailyAdapter;


    @Override
    protected int layoutRes() {
        return R.layout.activity_weather_detail;
    }

    @Override
    protected void onViewCreated() {

        binding.ivBack.setOnClickListener(view -> finish());

        initRecyclerView();

        Intent intent = getIntent();

        String lat = intent.getStringExtra("lat");
        String lon = intent.getStringExtra("lon");

        onLoad(lat,lon);
    }

    private void initRecyclerView() {
        weatherHourlyAdapter = new WeatherHourlyAdapter(WeatherDetailActivity.this);
        binding.rvContentHourWeather.setHasFixedSize(true);
        binding.rvContentHourWeather.setAdapter(weatherHourlyAdapter);

        weatherDailyAdapter = new WeatherDailyAdapter(WeatherDetailActivity.this);
        binding.rvContentDayWeather.setHasFixedSize(true);
        binding.rvContentDayWeather.setAdapter(weatherDailyAdapter);

    }


    private void onLoad(String lat,String lon) {
        L.i(":::ON LOAD 좌표" + lat + " , " + lon);

        compositeDisposable.add(repository.hourlyWeather(new WeatherRequest(lat,lon))
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {

                })
                .subscribe(response -> {
                    weatherHourlyAdapter.updateItems(response);
                }, throwable -> {
                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                }));


        compositeDisposable.add(repository.dailyWeather(new WeatherRequest(lat,lon))
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {

                })
                .subscribe(response -> {
                    weatherDailyAdapter.updateItems(response);
                }, throwable -> {
                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                }));
    }
}
