package com.uniapp.fastdeliveryappilcation.controller;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.SliderView;
import com.uniapp.fastdeliveryappilcation.ultils.ViewPagerAdapter;

public interface IProductController {
    void handleSliderInitialization(View view, ViewPager viewPager, SliderView sliderView);
    void handleMenuInitialization(TabLayout tabLayout, ViewPager viewPager, ViewPagerAdapter viewPagerAdapter);
}
