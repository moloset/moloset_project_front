package com.example.clothesvillage.dashboard.cody;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Movie;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseRecyclerAdapter;
import com.example.clothesvillage.base.BaseViewHolder;
import com.example.clothesvillage.databinding.CodyTypeItemRowBinding;


public class CodySelectAdapter extends BaseRecyclerAdapter<CodyType, CodySelectAdapter.ViewHolder> {

    public CodySelectAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new ViewHolder(parent);
    }

    public class ViewHolder extends BaseViewHolder<CodyTypeItemRowBinding, CodyType> {

        public ViewHolder(ViewGroup parent) {
            super(parent, R.layout.cody_type_item_row);
        }

        @Override
        protected void bind(int position,CodyType data) {
            binding.tvTitle.setText(data.getName());
            binding.tvTitle.setTextColor(data.isSelected() ? Color.parseColor("#F15F5F") : Color.parseColor("#252525"));
            binding.ivCircle.setBackgroundResource(data.isSelected() ? R.drawable.circle_background_selected : R.drawable.circle_background_none);
        }
    }
}
