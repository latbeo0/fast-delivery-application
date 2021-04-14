package com.uniapp.fastdeliveryappilcation.view;

import android.os.Parcelable;

public interface ILoginView {
    void OnLoginSuccess(String message, Parcelable passingObj);
    void OnLoginError(String message);
}
