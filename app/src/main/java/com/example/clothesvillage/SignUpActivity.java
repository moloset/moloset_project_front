package com.example.clothesvillage;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clothesvillage.home.WData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;
    private DatePickerDialog datePickerDialog;
    private Button btn_birth;
    //private User data;

    RadioGroup radioGroup;
    RadioButton radiobtn_woman;
    RadioButton radiobtn_man;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.btn_signup).setOnClickListener(onClickListener);
        findViewById(R.id.btn_goto_login).setOnClickListener(onClickListener);
        btn_birth = findViewById(R.id.btn_birth);

        //radio button set
        radiobtn_woman = (RadioButton)findViewById(R.id.radio_woman);
        radiobtn_man = (RadioButton)findViewById(R.id.radio_man);

        //radio group set
        radioGroup = findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);

        initDatePicker();;
        btn_birth.setText(getTodaysDate());
    }

    //user
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    //button click listener
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_login:
                    signup();
                    break;
                case R.id.btn_goto_signup:
                    startLoginActivity();
                    break;
                case R.id.btn_namecheck:
                    UsernameCheck();
                    break;
                case R.id.btn_goto_login:
                    startLoginActivity();
                    break;
            }
        }
    };

    private void signup() {
        String email = ((EditText)findViewById(R.id.edit_email)).getText().toString();
        String password = ((EditText)findViewById(R.id.edit_password)).getText().toString();
        String passwordCheck = ((EditText)findViewById(R.id.edit_password_check)).getText().toString();

        //email, password check
        if(email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0) {
            if(password.equals(passwordCheck)) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) { //success ui logic
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("sign up success");
                                } else {//failed ui logic
                                    // If sign in fails, display a message to the user.
                                    if(task.getException() != null) {
                                        startToast(task.getException().toString());
                                    }
                                }
                            }
                        });
            }else {
                Toast.makeText(this, "password do not match", Toast.LENGTH_SHORT).show();
            }
        } else {
            startToast("please enter again");
        }

    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    //init DatePicker
    private void initDatePicker() {
        User data = new User();

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                btn_birth.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;

        //User data set
        data.setUser_birth_year(year);
        data.setUser_birth_month(month);
        data.setUser_birth_day(day);

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return year + "년 " + month + "월 " + day + "일";
    }

    //show datepicker
    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    //radiogroup listener
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        User data = new User();
        @Override
        public void onCheckedChanged(RadioGroup group, int i) {
            if(i == R.id.radio_woman) {
                data.setUser_gender("여자");
            } else if(i == R.id.radio_man) {
                data.setUser_gender("남자");
            }
        }
    };
    public void UsernameCheck(){
        //
    }
}
