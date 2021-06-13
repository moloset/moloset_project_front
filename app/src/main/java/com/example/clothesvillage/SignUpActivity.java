package com.example.clothesvillage;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.clothesvillage.base.BaseActivity;
import com.example.clothesvillage.databinding.ActivitySignUpBinding;
import com.example.clothesvillage.remote.ClothesRepository;
import com.example.clothesvillage.remote.request.InsertUser;
import com.example.clothesvillage.remote.request.SingleRequest;
import com.example.clothesvillage.utils.DateUtils;
import com.example.clothesvillage.utils.MessageUtils;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class SignUpActivity extends BaseActivity<ActivitySignUpBinding> {

    private String selectedGender = "남";
    private String selectedBirth = "";
    private boolean isEmailCheck = false;
    private boolean isNickNameCheck = false;
    private ClothesRepository clothesRepository;

    @Override
    protected int layoutRes() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void onViewCreated() {
        clothesRepository = ClothesRepository.getInstance();

        binding.ivBack.setOnClickListener(view -> finish());
        binding.btnNamecheck.setOnClickListener(view -> checkEmail());
        binding.btnNickcheck.setOnClickListener(view -> checkNickName());
        binding.tvComplte.setOnClickListener(view -> insertUser());

        binding.btnBirth.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(SignUpActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(Calendar.YEAR, year);
                    selectedCalendar.set(Calendar.MONTH, monthOfYear);
                    selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    selectedBirth = DateUtils.getDate(selectedCalendar.getTimeInMillis());
                    binding.btnBirth.setText(selectedBirth);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

            dialog.getDatePicker().setCalendarViewShown(false);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.show();
        });

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radio_man:
                        selectedGender = "남";
                        break;
                    case R.id.radio_woman:
                        selectedGender = "여";
                        break;
                }
            }
        });
    }


    private void insertUser() {

        L.i(":::::insertUser");

        if (isEmtpy(binding.editId)) {
            MessageUtils.showLongToastMsg(getApplicationContext(), "이메일을 입력해주세요.");
            return;
        }

        if (isEmtpy(binding.editPassword) || isEmtpy(binding.editPasswordConfirm)) {
            MessageUtils.showLongToastMsg(getApplicationContext(), "비밀번호를 입력해주세요.");
            return;
        }

        if (isEmtpy(binding.editNickname)) {
            MessageUtils.showLongToastMsg(getApplicationContext(), "닉네임 입력해주세요.");
            return;
        }

        if (isEmtpy(binding.editHeight)) {
            MessageUtils.showLongToastMsg(getApplicationContext(), "키를 입력해주세요.");
            return;
        }

        if (isEmtpy(binding.editWeight)) {
            MessageUtils.showLongToastMsg(getApplicationContext(), "몸무게를 입력해주세요.");
            return;
        }

        if (!binding.editPassword.getText().toString().equalsIgnoreCase(binding.editPasswordConfirm.getText().toString())) {
            binding.tvErrorPassword.setVisibility(View.VISIBLE);
            return;
        }

        if (!isEmailCheck) {
            MessageUtils.showLongToastMsg(getApplicationContext(), "이메일 중복체크를 해주세요.");
            return;
        }

        if (!isNickNameCheck) {
            MessageUtils.showLongToastMsg(getApplicationContext(), "닉네임 중복체크를 해주세요.");
            return;
        }

        String email = binding.editId.getText().toString();
        String password = binding.editPassword.getText().toString();
        String name = binding.editNickname.getText().toString();
        String gender = selectedGender;
        String birth = selectedBirth;
        String height = binding.editHeight.getText().toString();
        String weight = binding.editWeight.getText().toString();
        String location = "";
        InsertUser insertUser = new InsertUser(email,password,name,gender,birth,height,weight,location);


        compositeDisposable.add(clothesRepository.userInsert(insertUser)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    L.i("::::response -> " + response);
                    MessageUtils.showLongToastMsg(getApplicationContext(), "회원가입에 성공하였습니다.");
                    finish();
                }, error -> {
                    MessageUtils.showLongToastMsg(getApplicationContext(), "다시 시도해주세요.");
                }));

    }

    private void checkEmail() {
        if (isEmtpy(binding.editId)) {
            MessageUtils.showLongToastMsg(getApplicationContext(), "이메일을 입력해주세요.");
            return;
        }

        if(!isEmailFormat(binding.editId.getText().toString())){
            MessageUtils.showLongToastMsg(getApplicationContext(), "이메일 양식을 지켜주세요.");
            return;
        }
        if (isEmailCheck) {
            return;
        }
        compositeDisposable.add(clothesRepository.checkEmail(new SingleRequest(binding.editId.getText().toString()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    L.i("::::response -> " + response);
                    if (response.getResult().equalsIgnoreCase("Success")) {
                        //중복없음
                        binding.editId.setEnabled(false);
                        binding.editId.setTextColor(Color.parseColor("#ababab"));
                        isEmailCheck = true;
                    } else {
                        binding.tvErrorEmail.setVisibility(View.VISIBLE);
                        isEmailCheck = false;
                    }
                }, error -> {
                    isEmailCheck = false;
                    MessageUtils.showLongToastMsg(getApplicationContext(), "다시 시도해주세요.");
                }));
    }


    private void checkNickName() {
        if (isEmtpy(binding.editNickname)) {
            MessageUtils.showLongToastMsg(getApplicationContext(), "닉네임 입력해주세요.");
            return;
        }
        if (isNickNameCheck) {
            return;
        }

        compositeDisposable.add(clothesRepository.checkName(new SingleRequest(binding.editNickname.getText().toString()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    L.i("::::response -> " + response);
                    if (response.getResult().equalsIgnoreCase("Success")) {
                        //중복없음
                        binding.editNickname.setEnabled(false);
                        binding.editNickname.setTextColor(Color.parseColor("#ababab"));
                        isNickNameCheck = true;
                    } else {
                        binding.tvErrorNick.setVisibility(View.VISIBLE);
                        isNickNameCheck = false;
                    }
                }, error -> {
                    isNickNameCheck = false;
                    MessageUtils.showLongToastMsg(getApplicationContext(), "다시 시도해주세요.");
                }));
    }

    private boolean isEmtpy(EditText editText) {
        return TextUtils.isEmpty(editText.getText()) || editText.getText().toString().equalsIgnoreCase("");
    }

    private boolean isEmailFormat(String email){
        boolean returnValue = false;
        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()){
            returnValue = true;
        }
        return returnValue;
    }
}
