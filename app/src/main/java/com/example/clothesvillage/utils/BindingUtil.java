package com.example.clothesvillage.utils;

import android.graphics.Color;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;

import com.example.clothesvillage.R;

public class BindingUtil {

    @BindingConversion
    public static int setVisibility(boolean isVisible){
        return isVisible ? View.VISIBLE : View.GONE;
    }

    @BindingAdapter({"selectCircle"})
    public static void setCircle(ImageView imageView, boolean is){
        imageView.setBackgroundResource(is ? R.drawable.circle_background_selected : R.drawable.circle_background_none);
    }

    @BindingAdapter({"selectText"})
    public static void setSelectText(TextView textView, boolean is){
        textView.setTextColor(is ? Color.parseColor("#F15F5F") : Color.parseColor("#252525"));
    }

}
