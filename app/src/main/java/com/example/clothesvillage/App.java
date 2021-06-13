package com.example.clothesvillage;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;


public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        //디버깅을 할수있는 함수의 tag 를 초기화 한다.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        L.initialize("young", true);
        Places.initialize(getApplicationContext(), "AIzaSyDATAotaZR_IK9O99nQ9xq9IIdBUqPJSsc");
        PlacesClient placesClient = Places.createClient(this);
    }
}
