package com.example.clothesvillage.utils;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class ActivityLauncher {
    private Fragment fragment = null;
    private Activity activity = null;


    public ActivityLauncher(Activity activity) {
        fragment = null;
        this.activity = activity;
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }


}
