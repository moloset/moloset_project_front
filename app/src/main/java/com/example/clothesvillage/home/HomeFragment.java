package com.example.clothesvillage.home;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.example.clothesvillage.base.BaseFragment;
import com.example.clothesvillage.databinding.FragmentHomeBinding;
import com.example.clothesvillage.remote.request.ClothesListRequest;
import com.example.clothesvillage.remote.request.WeatherRequest;
import com.example.clothesvillage.remote.volley.LocationProvider;
import com.example.clothesvillage.remote.volley.VolleyResult;
import com.example.clothesvillage.remote.volley.VolleyService;
import com.example.clothesvillage.utils.MessageUtils;
import com.example.clothesvillage.utils.PreferenceHelper;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private SettingsClient settingsClient;
    private LocationRequest locationRequest;
    private LocationSettingsRequest locationSettingsRequest;

    private Location location;


    @Override
    protected int layoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onViewCreated() {
        initFusedLocation();
        binding.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (location == null) {
                    MessageUtils.showLongToastMsg(getActivity(), "위치정보를 알수없습니다.");
                    return;
                }

                Intent intent = new Intent(getActivity(), WeatherDetailActivity.class);
                intent.putExtra("lat", String.valueOf(location.getLatitude()));
                intent.putExtra("lon", String.valueOf(location.getLongitude()));
                startActivity(intent);
            }
        });
    }

    private void initFusedLocation() {
        showProgressDialog("날씨 데이터를 불러오는중 입니다.");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        settingsClient = LocationServices.getSettingsClient(getActivity());
        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();
        startLocationUpdates();

    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder =
                new LocationSettingsRequest.Builder();

        builder.addLocationRequest(locationRequest);
        locationSettingsRequest = builder.build();
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        L.e(">>>>task.isSuccessful() = " + task.isSuccessful());
                    }
                });
    }


    private void createLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                location = locationResult.getLastLocation();
                L.e(">>>>>[GET] location = " + location);
                try {
                    getCurrentWeather(location);
                    getCurrentAddress(location);
                    stopLocationUpdates();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                hideProgressDialog();
//                stopLocationUpdates();
            }
        };
    }

    private void startLocationUpdates() {
        L.e(">>>>>>>>>>");
        settingsClient.checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        // 권한 확인
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        L.e(">>>>>>>>>>>>>>>>>>>>>>");
                        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

                    }
                })
                .addOnFailureListener(getActivity(), new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                L.e("LocationSettingsStatusCodes.RESOLUTION_REQUIRED");
                                //위치 설정이 만족스럽지 않을 때 감지.
                                // 그러나 사용자에게 대화상자를  보여줌으로써 고칠 수있습니다.
                                ResolvableApiException rae = (ResolvableApiException) e;
                                try {
                                    rae.startResolutionForResult(getActivity(), 10000);
                                } catch (IntentSender.SendIntentException ex) {
                                    ex.printStackTrace();
                                }
                                break;

                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                L.e("errorMessage = " + errorMessage);
                        }
                    }
                });
    }

    private void getCurrentWeather(Location location) {
        compositeDisposable.add(repository.currentWeather(new WeatherRequest(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude())))
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(this::hideProgressDialog)
                .subscribe(response -> {
                    L.i("::::response " + response);
                    recommend_style(Integer.parseInt(response.getTemp()));
                    binding.textviewWeatherTemperature.setText(response.getTemp() + "℃");
                    binding.textviewWeatherHighlowtemp.setText(response.getMinTemp() + "℃" + "/" + response.getMaxTemp() + "℃");
                    Glide.with(this)
                            .load(response.getIcon())
                            .placeholder(0)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .into(binding.imageviewWeatherPicture);
                }, throwable -> {
                    L.e(">>>>>>>>>>>>>>>>" + throwable.getMessage());
                }));
    }

    private void getCurrentAddress(Location location) {
        VolleyService volleyService = new VolleyService(new VolleyResult() {
            @Override
            public void notifySuccess(String type, JSONObject response) {
                try {
                    JSONArray ja = response.getJSONArray("documents");
                    if (ja.length() > 0) {
                        String address_name = "";
                        for (int i = 0; i < ja.length(); i++) {
                            JSONObject wtmJson = ja.getJSONObject(i);
                            if (wtmJson.has("address")) {
                                JSONObject address = wtmJson.getJSONObject("address");
                                address_name = address.getString("address_name");
                                L.e(":::address_name : " + address_name);

                            }
                        }

                        if (!address_name.equalsIgnoreCase("")) {
                            L.e(":::::::::::address " + address_name);
                            binding.tvAddress.setText(address_name);
                        }

                    }
                } catch (JSONException e) {

                }
            }

            @Override
            public void notifyError(VolleyError error) {

            }
        }, getActivity());
        volleyService.getGeoWTM(location);
    }

        public void recommend_style(int temperature) {
            if (temperature < -5) {
                binding.textviewRecomandStyle.setText("두꺼운 코트, 롱패딩, 모자, 귀마개");
            } else if (temperature < 9) {
                binding.textviewRecomandStyle.setText("가벼운 코트, 라이더 자켓");
            } else if (temperature < 11) {
                binding.textviewRecomandStyle.setText("트렌치 코트, 면자켓");
            } else if (temperature < 16) {
                binding.textviewRecomandStyle.setText("자켓, 셔츠, 가디건, 치마");
            } else if (temperature < 19) {
                binding.textviewRecomandStyle.setText("니트, 가디건, 후드티, 맨투맨, 청바지, 면바지");
            } else if (temperature < 22) {
                binding.textviewRecomandStyle.setText("긴팔, 가디건, 면바지, 슬랙스");
            } else if (temperature < 26) {
                binding.textviewRecomandStyle.setText("반팔, 얇은 셔츠, 반바지");
            } else {
                binding.textviewRecomandStyle.setText("민소매, 반바지, 원피스");
            }
        }
}
