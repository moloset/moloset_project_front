package com.example.clothesvillage.dashboard.cody;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseRecyclerAdapter;
import com.example.clothesvillage.base.BaseViewHolder;
import com.example.clothesvillage.databinding.ItemCommonUsageBinding;
import com.example.clothesvillage.databinding.ItemSelectedPhotoBinding;
import com.example.clothesvillage.remote.response.ClothesListResponse;


public class ImagePagerAdapter extends BaseRecyclerAdapter<String, ImagePagerAdapter.ViewHolder> {
    private final String baseUrl = "http://54.180.178.116:8080/";

    public ImagePagerAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ViewHolder(parent);
    }

    public class ViewHolder extends BaseViewHolder<ItemCommonUsageBinding, String> {

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_common_usage);
        }

        @Override
        protected void bind(int position,String data) {
            Glide.with(binding.ivPhoto.getContext())
                    .load(baseUrl + data)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.ivPhoto);
        }
    }
}
