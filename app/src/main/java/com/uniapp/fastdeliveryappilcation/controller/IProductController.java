package com.uniapp.fastdeliveryappilcation.controller;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.SliderView;
import com.uniapp.fastdeliveryappilcation.model.ActiveSubscription;
import com.uniapp.fastdeliveryappilcation.ultils.ViewPagerAdapter;
import com.uniapp.fastdeliveryappilcation.view.IAddMoneyView;
import com.uniapp.fastdeliveryappilcation.view.ISubscriptionView;

import java.util.Map;

public interface IProductController {
    void handleSliderInitialization(View view, ViewPager viewPager, SliderView sliderView);
    void handleMenuInitialization(TabLayout tabLayout, ViewPager viewPager, ViewPagerAdapter viewPagerAdapter);
    void handleWalletTransaction(Map<String, Object> params);
    void handleAddWalletTransaction(Map<String, Object> params);
    void handleSubscription(Map<String, Object> params, IAddMoneyView addMoneyView);
    void handleInitSubscriptionData(String id);
    void handleInitCredit(String id);
    void handleSubscriptionHistory();
    void handleTransactionHistory();
    void handleRemoveSubscription(ActiveSubscription activeSubscription, ISubscriptionView subscriptionView);
}
