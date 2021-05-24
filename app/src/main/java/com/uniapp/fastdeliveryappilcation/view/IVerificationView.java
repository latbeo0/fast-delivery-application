package com.uniapp.fastdeliveryappilcation.view;

import android.os.Parcelable;

import java.util.Map;

public interface IVerificationView {
    void OnLoginSuccess(Parcelable passingObj);
    void OnLoginError(String message);
    void handlePreferences(Map<String, Object> params);
}
