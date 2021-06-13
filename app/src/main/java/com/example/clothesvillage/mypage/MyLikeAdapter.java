package com.example.clothesvillage.mypage;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseRecyclerAdapter;
import com.example.clothesvillage.base.BaseViewHolder;
import com.example.clothesvillage.databinding.CodyItemRowBinding;
import com.example.clothesvillage.remote.response.StyleListResponse;
import com.example.clothesvillage.remote.response.UserSelectListResponse;


public abstract class MyLikeAdapter extends BaseRecyclerAdapter<UserSelectListResponse, MyLikeAdapter.ViewHolder> {
    public abstract void onItemClick(UserSelectListResponse data);

    public abstract void onLikeItemClick(int position, UserSelectListResponse data);

    private final String baseUrl = "http://54.180.178.116:8080/";

    public MyLikeAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ViewHolder(parent);
    }

    public class ViewHolder extends BaseViewHolder<CodyItemRowBinding, UserSelectListResponse> {

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.cody_item_row);
        }

        @Override
        protected void bind(int position, UserSelectListResponse data) {
            binding.tvUsreName.setText(data.getUser_name());
            binding.tvTitle.setText(data.getStyle_name());
            binding.tvContent.setText(data.getStyle_content());

            if (data.getGoodCheck().equalsIgnoreCase("Y")) {
                binding.ivLike.setBackgroundResource(R.drawable.black_heart_button);
            } else {
                binding.ivLike.setBackgroundResource(R.drawable.heart_button);
            }

            Glide.with(binding.ivPhoto.getContext())
                    .load(baseUrl + data.getPhoto())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.ivPhoto);


            Glide.with(binding.ivProfile.getContext())
                    .load(baseUrl + data.getUser_profile())
                    .placeholder(R.drawable.profile_image_default)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.ivProfile);

            itemView.setOnClickListener(v -> onItemClick(data));
            binding.ivLike.setOnClickListener(v -> onLikeItemClick(position, data));

        }


    }
}
