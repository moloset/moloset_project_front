package com.example.clothesvillage.base;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.clothesvillage.remote.ClothesRepository;
import com.example.clothesvillage.utils.ActivityLauncher;
import com.example.clothesvillage.utils.ImageLoaderHelper;

import io.reactivex.rxjava3.disposables.CompositeDisposable;


public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {
    @LayoutRes
    protected abstract int layoutRes();

    protected abstract void onViewCreated();

    protected ProgressDialog mProgressDialog;
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected ActivityLauncher launcher;
    protected ImageLoaderHelper loaderHelper;
    protected ClothesRepository repository;

    protected B binding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, layoutRes());
        binding.setLifecycleOwner(this);
        launcher = new ActivityLauncher(this);
        loaderHelper = new ImageLoaderHelper(this);
        repository = ClothesRepository.getInstance();
        onViewCreated();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    public void showProgressDialog(String title) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(title);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }


}
