package com.uniapp.fastdeliveryappilcation;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.uniapp.fastdeliveryappilcation.fragment.AdminMenuFragment;
import com.uniapp.fastdeliveryappilcation.fragment.AdminProfileFragment;
import com.uniapp.fastdeliveryappilcation.fragment.DashboardFragment;
import com.uniapp.fastdeliveryappilcation.fragment.ProfileFragment;
import com.uniapp.fastdeliveryappilcation.fragment.SubscriptionFragment;
import com.uniapp.fastdeliveryappilcation.view.IAdminView;

public class AdminActivity extends AppCompatActivity implements IAdminView {
    private Fragment fragment;
    SharedPreferences sharedPreferences;
    private static final String TAG = MainActivity.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.main_admin);

        loadFragment(new AdminMenuFragment(getApplicationContext(), sharedPreferences));
        setDefaultFragment();
        onFragmentChangeListener();
    }

    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onFragmentChangeListener() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu:
                    fragment = new AdminMenuFragment(getApplicationContext(), sharedPreferences);
                    loadFragment(fragment);
                    return true;
                case R.id.profile:
                    fragment = new AdminProfileFragment(getApplicationContext(), sharedPreferences);
                    loadFragment(fragment);
                    return true;
            }
            return true;
        });
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.screens,fragment,TAG).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setDefaultFragment() {
        fragment = new AdminMenuFragment(getApplicationContext(), sharedPreferences);
        loadFragment(fragment);
    }
}