package com.uniapp.fastdeliveryappilcation.view;

import android.os.Parcelable;

public interface IVerificationView {
    void OnLoginSuccess(Parcelable passingObj);
    void OnLoginError(String message);
}
