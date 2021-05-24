package com.uniapp.fastdeliveryappilcation.controller;

import android.app.Activity;
import android.content.Intent;

import com.facebook.AccessToken;
import com.google.firebase.auth.PhoneAuthCredential;
import com.uniapp.fastdeliveryappilcation.model.User;

import java.util.Map;

public interface IUserController {
    void onFirebasePhoneAuthentication(Map<String, Object> params);
    Map<String, Object> codeVerification(PhoneAuthCredential credential);
    void onFacebookAuthentication(AccessToken accessToken);
    void OnGoogleAuthentication(Intent data);
    void SaveUserData(Activity activity, Map<String, Object> params);
    Map<String, Object> getUserDataFromFirebase();
    void getUserData(String email);
}
