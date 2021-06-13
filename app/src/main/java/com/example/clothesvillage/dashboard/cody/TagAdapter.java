package com.example.clothesvillage.dashboard.cody;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseRecyclerAdapter;
import com.example.clothesvillage.base.BaseViewHolder;
import com.example.clothesvillage.databinding.ItemSelectedPhotoBinding;
import com.example.clothesvillage.databinding.ItemTagRowBinding;
import com.example.clothesvillage.remote.response.ClothesListResponse;


public class TagAdapter extends BaseRecyclerAdapter<String, TagAdapter.ViewHolder> {

    public TagAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ViewHolder(parent);
    }

    public class ViewHolder extends BaseViewHolder<ItemTagRowBinding, String> {

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_tag_row);
        }

        @Override
        protected void bind(int position,String data) {
           binding.tvName.setText(data);
        }
    }
}
