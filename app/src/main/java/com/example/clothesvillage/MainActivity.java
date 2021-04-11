package com.example.clothesvillage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.clothesvillage.dashboard.DashboardFragment;
import com.example.clothesvillage.home.HomeFragment;
import com.example.clothesvillage.mypage.MypageFragment;
import com.example.clothesvillage.notifications.NotificationsFragment;
import com.example.clothesvillage.saleFragment.SaleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private DashboardFragment dashboardFragment;
    private HomeFragment homeFragment;
    private MypageFragment mypageFragment;
    private NotificationsFragment notificationsFragment;
    private SaleFragment saleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            startSignUpActivity();
        }
        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.navigation_home:
                        setFrag(0);
                        break;
                    case R.id.navigation_dashboard:
                        setFrag(1);
                        break;
                    case R.id.navigation_notifications:
                        setFrag(2);
                        break;
                    case R.id.navigation_sale:
                        setFrag(3);
                        break;
                    case R.id.navigation_mypage:
                        setFrag(4);
                        break;
                }

                return true;
            }
        });
        dashboardFragment = new DashboardFragment();
        homeFragment = new HomeFragment();
        mypageFragment = new MypageFragment();
        notificationsFragment = new NotificationsFragment();
        saleFragment = new SaleFragment();

        setFrag(0); //first display
    }

    private void startSignUpActivity() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    //fragment change
    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, homeFragment);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, dashboardFragment);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, notificationsFragment);
                ft.commit();
                break;

            case 3:
                ft.replace(R.id.main_frame, saleFragment);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.main_frame, mypageFragment);
                ft.commit();
                break;
        }
    }
}