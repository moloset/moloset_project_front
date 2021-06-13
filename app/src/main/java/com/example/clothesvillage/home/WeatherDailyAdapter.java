package com.example.clothesvillage.home;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseRecyclerAdapter;
import com.example.clothesvillage.base.BaseViewHolder;
import com.example.clothesvillage.databinding.WeatherDailyItemRowBinding;
import com.example.clothesvillage.databinding.WeatherHourlyItemRowBinding;
import com.example.clothesvillage.remote.response.DailyWeatherResponse;
import com.example.clothesvillage.remote.response.HourlyWeatherResponse;


public class WeatherDailyAdapter extends BaseRecyclerAdapter<DailyWeatherResponse, WeatherDailyAdapter.ViewHolder> {

    public WeatherDailyAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ViewHolder(parent);
    }

    public class ViewHolder extends BaseViewHolder<WeatherDailyItemRowBinding, DailyWeatherResponse> {

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.weather_daily_item_row);
        }

        @Override
        protected void bind(int position,DailyWeatherResponse data) {
            binding.tvDay.setText(data.getDay());
            binding.tvTemp.setText(data.getMinTemp() + "℃" + " / " + data.getMaxTemp() + "℃");

            Glide.with(binding.ivWeather.getContext())
                    .load(data.getIcon())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.ivWeather);
        }
    }
}
