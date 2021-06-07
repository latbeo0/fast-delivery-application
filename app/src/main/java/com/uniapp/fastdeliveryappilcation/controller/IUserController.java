package com.uniapp.fastdeliveryappilcation.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.facebook.AccessToken;
import com.google.firebase.auth.PhoneAuthCredential;
import com.uniapp.fastdeliveryappilcation.model.User;
import com.uniapp.fastdeliveryappilcation.view.IAddMoneyView;
import com.uniapp.fastdeliveryappilcation.view.ILoginView;
import com.uniapp.fastdeliveryappilcation.view.IVerificationView;

import java.util.Map;

public interface IUserController {
    void onFirebasePhoneAuthentication(Map<String, Object> params);
    Map<String, Object> codeVerification(PhoneAuthCredential credential);
    void onFacebookAuthentication(AccessToken accessToken);
    void OnGoogleAuthentication(Intent data);
    void SaveUserData(Activity activity, Map<String, Object> params);
    Map<String, Object> getUserDataFromFirebase();
    void getUserData(String info, IVerificationView verificationView, ILoginView loginView);
    void updateData(User user, View view);
    void handleWallet(String id, long price, Boolean isUpdate, IAddMoneyView addMoneyView);
    void handleAddress(String id, String address);
}
