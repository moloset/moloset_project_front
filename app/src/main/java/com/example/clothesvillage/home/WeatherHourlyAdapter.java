package com.example.clothesvillage.home;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseRecyclerAdapter;
import com.example.clothesvillage.base.BaseViewHolder;
import com.example.clothesvillage.dashboard.cody.CodyType;
import com.example.clothesvillage.databinding.CodyTypeItemRowBinding;
import com.example.clothesvillage.databinding.WeatherHourlyItemRowBinding;
import com.example.clothesvillage.remote.response.HourlyWeatherResponse;


public class WeatherHourlyAdapter extends BaseRecyclerAdapter<HourlyWeatherResponse, WeatherHourlyAdapter.ViewHolder> {

    public WeatherHourlyAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ViewHolder(parent);
    }

    public class ViewHolder extends BaseViewHolder<WeatherHourlyItemRowBinding, HourlyWeatherResponse> {

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.weather_hourly_item_row);
        }

        @Override
        protected void bind(int position,HourlyWeatherResponse data) {
            binding.tvTopCotnent.setText(data.getTime());
            binding.tvBottomContent.setText(data.getTemp() + "℃");


            Glide.with(binding.ivWeather.getContext())
                    .load(data.getIcon())
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.ivWeather);

        }
    }
}
