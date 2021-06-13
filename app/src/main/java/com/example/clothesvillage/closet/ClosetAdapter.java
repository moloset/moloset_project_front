package com.example.clothesvillage.closet;

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
import com.example.clothesvillage.databinding.ItemCommonUsageBinding;
import com.example.clothesvillage.remote.response.ClothesListResponse;


public class ClosetAdapter extends BaseRecyclerAdapter<ClothesListResponse, ClosetAdapter.ViewHolder> {

    private final String baseUrl = "http://54.180.178.116:8080/";

    public ClosetAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ViewHolder(parent);
    }

    public class ViewHolder extends BaseViewHolder<ItemCommonUsageBinding, ClothesListResponse> {

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_common_usage);
        }

        @Override
        protected void bind(int position, ClothesListResponse data) {
            Glide.with(binding.ivPhoto.getContext())
                    .load(baseUrl + data.getPhoto())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.ivPhoto);
        }
    }
}
