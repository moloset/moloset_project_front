package com.example.clothesvillage;

import android.content.Intent;
import android.widget.Toast;

import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.databinding.ActivityLoginBinding;
import com.example.clothesvillage.remote.ClothesRepository;
import com.example.clothesvillage.remote.request.LoginRequest;
import com.example.clothesvillage.remote.request.SingleRequest;
import com.example.clothesvillage.utils.MessageUtils;
import com.example.clothesvillage.utils.PreferenceHelper;
import com.google.firebase.auth.FirebaseAuth;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;
    private ClothesRepository clothesRepository;

    @Override
    protected int layoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void onViewCreated() {
        mAuth = FirebaseAuth.getInstance();
        clothesRepository = ClothesRepository.getInstance();

        binding.btnLogin.setOnClickListener(view -> signup());
        binding.btnGotoSignup.setOnClickListener(view -> startSignUpActivity());

//        binding.editId.setText("c@naver.com");
//        binding.editPass.setText("111111");
    }


    private void signup() {
        String email =   binding.editId.getText().toString();
        String password = binding.editPass.getText().toString();

        if(email.length() > 0 && password.length() > 0) {
            compositeDisposable.add(clothesRepository.login(new LoginRequest(email,password))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        L.i("::::response -> " + response);
                        if(response.getResult().equalsIgnoreCase("Success")){
                            getUserInfo();
                        }else{
                            MessageUtils.showLongToastMsg(getApplicationContext(), "로그인에 실패하였습니다.");
                        }
                    }, error -> {
                        MessageUtils.showLongToastMsg(getApplicationContext(), "다시 시도해주세요.");
                    }));
        } else {
            startToast("please enter again");
        }
    }

    private void getUserInfo(){
        compositeDisposable.add(clothesRepository.getUserInfo(new SingleRequest(binding.editId.getText().toString()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    L.i("::::getUserInfo response -> " + response);
                    PreferenceHelper.setCurrentUser(getApplicationContext(),response);
                    PreferenceHelper.setLoginState(getApplicationContext(),true);
                            startMainActivity();
                            finish();
                }, error -> {
                    MessageUtils.showLongToastMsg(getApplicationContext(), "다시 시도해주세요.");
                }));
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void startSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
