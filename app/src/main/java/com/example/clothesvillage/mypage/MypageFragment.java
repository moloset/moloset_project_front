package com.example.clothesvillage.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clothesvillage.LoginActivity;
import com.example.clothesvillage.R;
import com.example.clothesvillage.SignUpActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MypageFragment extends Fragment implements View.OnClickListener {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mypage, container, false);

        Button button = view.findViewById(R.id.btn_logout);
        button.setOnClickListener(this);

        return view;
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_logout:
                FirebaseAuth.getInstance().signOut();
                startSignUpActivity();
                break;
        }
    }

    private void startSignUpActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

}
