package com.uniapp.fastdeliveryappilcation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.uniapp.fastdeliveryappilcation.adapter.HistoryAdapter;

public class HistoryActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    HistoryAdapter historyViewpagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        tabLayout =findViewById(R.id.tabs);
        viewPager =findViewById(R.id.viewpager1);
        tabLayout.addTab(tabLayout.newTab().setText("Veg"));
        tabLayout.addTab(tabLayout.newTab().setText("Non Veg"));
        tabLayout.setupWithViewPager(viewPager);
        historyViewpagerAdapter = new HistoryAdapter(getSupportFragmentManager(),2);
        viewPager.setAdapter(historyViewpagerAdapter);
    }
}