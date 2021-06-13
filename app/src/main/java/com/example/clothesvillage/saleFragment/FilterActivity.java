package com.example.clothesvillage.saleFragment;

import android.view.View;

import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.databinding.ActivityFilterBinding;
import com.example.clothesvillage.model.FilterItem;
import com.example.clothesvillage.utils.PreferenceHelper;

import java.util.ArrayList;

public class FilterActivity extends BaseActivity<ActivityFilterBinding> {


    @Override
    protected int layoutRes() {
        return R.layout.activity_filter;
    }

    @Override
    protected void onViewCreated() {
        onFilterSetting();
        binding.ivBack.setOnClickListener(v -> finish());
        binding.tvComplete.setOnClickListener(v -> {
            FilterItem filterItem = new FilterItem();
            filterItem.setCategory_1(binding.checkCategory1.isChecked());
            filterItem.setCategory_2(binding.checkCategory2.isChecked());
            filterItem.setCategory_3(binding.checkCategory3.isChecked());
            filterItem.setCategory_4(binding.checkCategory4.isChecked());
            filterItem.setCategory_5(binding.checkCategory5.isChecked());
            filterItem.setCategory_6(binding.checkCategory6.isChecked());
            filterItem.setHeight(binding.tvHeightContent.getText() == null ? "" : binding.tvHeightContent.getText().toString());
            filterItem.setWeight(binding.tvWeightContent.getText() == null ? "" : binding.tvWeightContent.getText().toString());
            filterItem.setHeightEnable(binding.checkHeightNone.isChecked());
            filterItem.setWeightEnable(binding.checkWeightNone.isChecked());
            PreferenceHelper.setFilterItem(getApplicationContext(),filterItem);
            setResult(RESULT_OK);
            finish();
        });

    }

    private void onFilterSetting(){
        FilterItem filterItem = null;
        if(PreferenceHelper.getFilterItem(getApplicationContext()) != null){
            filterItem = PreferenceHelper.getFilterItem(getApplicationContext());
        }else{
            filterItem = new FilterItem();
        }

        binding.checkCategory1.setChecked(filterItem.isCategory_1());
        binding.checkCategory2.setChecked(filterItem.isCategory_2());
        binding.checkCategory3.setChecked(filterItem.isCategory_3());
        binding.checkCategory4.setChecked(filterItem.isCategory_4());
        binding.checkCategory5.setChecked(filterItem.isCategory_5());
        binding.checkCategory6.setChecked(filterItem.isCategory_6());
        binding.checkHeightNone.setChecked(filterItem.isHeightEnable());
        binding.checkWeightNone.setChecked(filterItem.isWeightEnable());

        if(!filterItem.getHeight().equalsIgnoreCase("")){
            binding.tvHeightContent.setText(filterItem.getHeight());
        }

        if(!filterItem.getWeight().equalsIgnoreCase("")){
            binding.tvHeightContent.setText(filterItem.getWeight());
        }
    }

}
