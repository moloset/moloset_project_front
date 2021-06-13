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
import com.example.clothesvillage.databinding.ReplyListRowBinding;
import com.example.clothesvillage.remote.response.ClothesListResponse;
import com.example.clothesvillage.remote.response.ReplyListResponse;


public class ReplyAdapter extends BaseRecyclerAdapter<ReplyListResponse, ReplyAdapter.ViewHolder> {
    private final String baseUrl = "http://54.180.178.116:8080/";

    public ReplyAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ViewHolder(parent);
    }

    public class ViewHolder extends BaseViewHolder<ReplyListRowBinding, ReplyListResponse> {

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.reply_list_row);
        }

        @Override
        protected void bind(int position,ReplyListResponse data) {
            binding.tvUsreName.setText(data.getUser_name());
            binding.tvContent.setText(data.getReply_content());

            Glide.with(binding.ivProfile.getContext())
                    .load(baseUrl + data.getUser_profile())
                    .placeholder(R.drawable.profile_image_default)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.ivProfile);
        }
    }
}
