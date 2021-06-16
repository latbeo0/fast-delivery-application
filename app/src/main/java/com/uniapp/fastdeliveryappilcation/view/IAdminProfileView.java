package com.uniapp.fastdeliveryappilcation.view;

import android.view.View;

import com.uniapp.fastdeliveryappilcation.model.Slider;
import com.uniapp.fastdeliveryappilcation.model.User;

import java.util.List;

public interface IAdminProfileView {
    void initData(View view, List<User> list);
}
