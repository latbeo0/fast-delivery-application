package com.uniapp.fastdeliveryappilcation.controller;

import android.content.Intent;

import com.facebook.AccessToken;
import com.google.firebase.auth.PhoneAuthCredential;

import java.util.Map;

public interface IUserController {
    void onFirebasePhoneAuthentication(Map<String, Object> params);
    void codeVerification(PhoneAuthCredential credential);
    void onFacebookAuthentication(AccessToken accessToken);
    void OnGoogleAuthentication(Intent data);
    void onDestroy();
}
