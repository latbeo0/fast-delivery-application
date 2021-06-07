package com.uniapp.fastdeliveryappilcation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.google.android.material.tabs.TabLayout;
import com.uniapp.fastdeliveryappilcation.adapter.HistoryAdapter;

public class HistoryActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageButton backArrow;
    HistoryAdapter historyViewpagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_history);

        tabLayout =findViewById(R.id.tabs);
        viewPager =findViewById(R.id.viewpager1);
        backArrow = findViewById(R.id.arrowback);
        backArrow.setOnClickListener(v -> onBackPressed());
        tabLayout.addTab(tabLayout.newTab().setText("Veg"));
        tabLayout.addTab(tabLayout.newTab().setText("Non Veg"));
        tabLayout.setupWithViewPager(viewPager);
        historyViewpagerAdapter = new HistoryAdapter(getSupportFragmentManager(),2);
        viewPager.setAdapter(historyViewpagerAdapter);
    }
}