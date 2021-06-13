package com.example.clothesvillage.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clothesvillage.L;
import com.example.clothesvillage.model.FilterItem;
import com.example.clothesvillage.remote.response.UserInfoResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class PreferenceHelper {

    public static UserInfoResponse getCurrentUser(Context context) {
        SharedPreferences pref = context.getSharedPreferences("pref", AppCompatActivity.MODE_PRIVATE);
        String json = pref.getString("KEY_CURRENT_USER", null);
        Type type = new TypeToken<UserInfoResponse>() {}.getType();
        UserInfoResponse item = new Gson().fromJson(json, type);
        return item;
    }

    public static void setCurrentUser(Context context, UserInfoResponse insertUser) {
        SharedPreferences pref = context.getSharedPreferences("pref", AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(insertUser);
        L.e("json = " + json);
        prefsEditor.putString("KEY_CURRENT_USER", json);
        prefsEditor.apply();
    }

    public static void setLoginState(Context context,boolean is) {
        SharedPreferences preferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        preferences.edit().putBoolean("KEY_LOGIN_STATE", is).commit();
    }

    public static boolean getLoginState(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
        return preferences.getBoolean("KEY_LOGIN_STATE", false);
    }

    public static FilterItem getFilterItem(Context context) {
        SharedPreferences pref = context.getSharedPreferences("pref", AppCompatActivity.MODE_PRIVATE);
        String json = pref.getString("KEY_FILTER_ITEM", null);
        Type type = new TypeToken<FilterItem>() {}.getType();
        FilterItem item = new Gson().fromJson(json, type);
        return item;
    }

    public static void setFilterItem(Context context, FilterItem filterItem) {
        SharedPreferences pref = context.getSharedPreferences("pref", AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = pref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(filterItem);
        L.e("json = " + json);
        prefsEditor.putString("KEY_FILTER_ITEM", json);
        prefsEditor.apply();
    }
}
