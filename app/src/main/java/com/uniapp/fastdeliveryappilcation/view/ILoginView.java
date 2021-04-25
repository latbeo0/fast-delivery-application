package com.uniapp.fastdeliveryappilcation.view;

import android.os.Parcelable;

public interface ILoginView {
    void OnOtpLogin();
    void OnGoogleLogin();
    void OnFacebookLogin();
    void OnLoginSuccess(Parcelable passingObj);
    void OnLoginError(String message);
}
