package com.uniapp.fastdeliveryappilcation.view;

import android.view.View;

import com.uniapp.fastdeliveryappilcation.model.Slider;

import java.util.List;

public interface IDashboardView {
    void initCredit(String number);
    void sliderInitData(View view, List<Slider> sliderList);
}
