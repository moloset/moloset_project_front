package com.example.clothesvillage.saleFragment;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseRecyclerAdapter;
import com.example.clothesvillage.base.BaseViewHolder;
import com.example.clothesvillage.databinding.CodyItemRowBinding;
import com.example.clothesvillage.databinding.TradeItemRowBinding;
import com.example.clothesvillage.remote.response.StyleListResponse;
import com.example.clothesvillage.remote.response.TradeListResponse;

import java.text.DecimalFormat;


public class TradeListAdapter extends BaseRecyclerAdapter<TradeListResponse, TradeListAdapter.ViewHolder> {

    private final String baseUrl = "http://54.180.178.116:8080/";

    public TradeListAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ViewHolder(parent);
    }

    public class ViewHolder extends BaseViewHolder<TradeItemRowBinding, TradeListResponse> {

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.trade_item_row);
        }

        @Override
        protected void bind(int position, TradeListResponse data) {
            binding.tvTitle.setText(data.getTrade_name());
            binding.tvContent.setText(data.getTrade_content());
            binding.tvType.setText(data.getTrade_case());

            DecimalFormat moenyFormat = new DecimalFormat("###,###");
            String formattedStringPrice = moenyFormat.format(Integer.parseInt(data.getTrade_price()));
            binding.tvPrice.setText(formattedStringPrice);

            Glide.with(binding.ivPhoto.getContext())
                    .load(baseUrl + data.getPhoto())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.ivPhoto);

        }


    }
}
