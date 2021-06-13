package com.example.clothesvillage.mypage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseRecyclerAdapter;
import com.example.clothesvillage.base.BaseViewHolder;
import com.example.clothesvillage.databinding.CodyItemRowBinding;
import com.example.clothesvillage.databinding.MyFeedItemRowBinding;
import com.example.clothesvillage.remote.response.StyleListResponse;


public abstract class MyFeedAdapter extends BaseRecyclerAdapter<StyleListResponse, MyFeedAdapter.ViewHolder> {
    abstract void onItemClick(int position,StyleListResponse data);
    abstract void onClickMenu(View view, int position, StyleListResponse data);
    private final String baseUrl = "http://54.180.178.116:8080/";

    public MyFeedAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ViewHolder(parent);
    }

    public class ViewHolder extends BaseViewHolder<MyFeedItemRowBinding, StyleListResponse> {

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.my_feed_item_row);
        }

        @Override
        protected void bind(int position,StyleListResponse data) {
            binding.tvUsreName.setText(data.getUser_name());
            binding.tvTitle.setText(data.getStyle_name());
            binding.tvContent.setText(data.getStyle_content());


            Glide.with(binding.ivPhoto.getContext())
                    .load(baseUrl + data.getPhoto())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.ivPhoto);


            Glide.with(binding.ivProfile.getContext())
                    .load(baseUrl+data.getUser_profile())
                    .placeholder(R.drawable.profile_image_default)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.ivProfile);

            itemView.setOnClickListener(v -> {
                onItemClick(position,data);
            });

            binding.ivEdit.setOnClickListener(v -> onClickMenu(binding.ivEdit,position,data));

        }


    }
}
