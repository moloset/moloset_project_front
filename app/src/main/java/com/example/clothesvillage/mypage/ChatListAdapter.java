package com.example.clothesvillage.mypage;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseRecyclerAdapter;
import com.example.clothesvillage.base.BaseViewHolder;
import com.example.clothesvillage.databinding.ChatListRowBinding;
import com.example.clothesvillage.databinding.TradeItemRowBinding;
import com.example.clothesvillage.remote.response.ChatListResponse;
import com.example.clothesvillage.remote.response.TradeListResponse;

import java.text.DecimalFormat;


public class ChatListAdapter extends BaseRecyclerAdapter<ChatListResponse, ChatListAdapter.ViewHolder> {

    private final String baseUrl = "http://54.180.178.116:8080/";

    public ChatListAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ViewHolder(parent);
    }

    public class ViewHolder extends BaseViewHolder<ChatListRowBinding, ChatListResponse> {

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.chat_list_row);
        }

        @Override
        protected void bind(int position, ChatListResponse data) {
            binding.tvTitle.setText(data.getUser_name());
            binding.tvContent.setText(data.getLast_msg());
            binding.tvType.setText(data.getTrade_case());

            Glide.with(binding.ivPhoto.getContext())
                    .load(baseUrl + data.getPhoto())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.ivPhoto);

        }


    }
}
