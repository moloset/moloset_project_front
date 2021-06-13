package com.example.clothesvillage.dashboard.cody;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseRecyclerAdapter;
import com.example.clothesvillage.base.BaseViewHolder;
import com.example.clothesvillage.databinding.CodyTypeItemRowBinding;
import com.example.clothesvillage.databinding.ItemSelectedPhotoBinding;
import com.example.clothesvillage.remote.response.ClothesListResponse;


public class PhotoSelectAdapter extends BaseRecyclerAdapter<ClothesListResponse, PhotoSelectAdapter.ViewHolder> {
    private final String baseUrl = "http://54.180.178.116:8080/";

    public PhotoSelectAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ViewHolder(parent);
    }

    public class ViewHolder extends BaseViewHolder<ItemSelectedPhotoBinding, ClothesListResponse> {

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_selected_photo);
        }

        @Override
        protected void bind(int position,ClothesListResponse data) {
            Glide.with(binding.ivPhoto.getContext())
                    .load(baseUrl + data.getPhoto())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.ivPhoto);
        }
    }
}
