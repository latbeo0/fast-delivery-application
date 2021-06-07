package com.uniapp.fastdeliveryappilcation.view;

import android.view.View;

import java.util.Map;

public interface ISubscriptionView {
    void initData(View view, Map<String, Object> sublist);
    void handleRemove();
}
