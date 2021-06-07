package com.uniapp.fastdeliveryappilcation;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.uniapp.fastdeliveryappilcation.controller.UserController;
import com.uniapp.fastdeliveryappilcation.fragment.DashboardFragment;
import com.uniapp.fastdeliveryappilcation.fragment.ProfileFragment;
import com.uniapp.fastdeliveryappilcation.fragment.SubscriptionFragment;
import com.uniapp.fastdeliveryappilcation.view.IMainView;

public class MainActivity extends AppCompatActivity implements IMainView {

    private Fragment fragment;
    private BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;
    private static final String TAG = MainActivity.class.getSimpleName();

    public MainActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        loadFragment(new DashboardFragment(getApplicationContext(), sharedPreferences));
        setDefaultFragment();
        onFragmentChangeListener();
    }

    @Override
    public void onFragmentChangeListener() {
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu:
                    fragment = new DashboardFragment(getApplicationContext(), sharedPreferences);
                    loadFragment(fragment);
                    return true;
                case R.id.subscribe:
                    fragment = new SubscriptionFragment(getApplicationContext(), sharedPreferences);
                    loadFragment(fragment);
                    return true;
                case R.id.profile:
                    fragment = new ProfileFragment(getApplicationContext(), sharedPreferences);
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

    private void setDefaultFragment() {

        fragment = new DashboardFragment(getApplicationContext(), sharedPreferences);
        loadFragment(fragment);
    }
}