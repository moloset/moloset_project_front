package com.example.clothesvillage;

import android.Manifest;
import android.content.Intent;

import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.databinding.ActivitySplashBinding;
import com.example.clothesvillage.saleFragment.TradeMapActivity;
import com.example.clothesvillage.saleFragment.TradeRegisterDetailActivity;
import com.example.clothesvillage.utils.PreferenceHelper;
import com.google.android.libraries.places.api.model.Place;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.tedpark.tedonactivityresult.rx2.TedRxOnActivityResult;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;


public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    private Disposable timerDispose;

    @Override
    protected int layoutRes() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onViewCreated() {
        L.i(":::::onViewCreated");
        timerDispose = Flowable.timer(2000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Dexter.withActivity(SplashActivity.this).withPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            L.i("::report allPermission " + report.areAllPermissionsGranted());
                            if (report.areAllPermissionsGranted()) {
                                if (PreferenceHelper.getLoginState(getApplicationContext())) {
                                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                } else {
                                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                }
                            }
                            finish();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();

                });
    }

    @Override
    protected void onDestroy() {
        if (timerDispose != null) {
            timerDispose.dispose();
        }
        super.onDestroy();
    }
}
