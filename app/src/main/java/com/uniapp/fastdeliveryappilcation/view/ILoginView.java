package com.uniapp.fastdeliveryappilcation.view;

import android.os.Parcelable;

import java.util.Map;

public interface ILoginView {
    void OnOtpLogin();
    void OnGoogleLogin();
    void OnFacebookLogin();
    void OnLoginSuccess(Parcelable passingObj);
    void OnLoginError(String message);
    void handlePreferences(Map<String, Object> params);
}
