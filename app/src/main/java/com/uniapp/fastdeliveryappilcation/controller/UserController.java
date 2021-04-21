package com.uniapp.fastdeliveryappilcation.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.uniapp.fastdeliveryappilcation.database.UserDatabase;
import com.uniapp.fastdeliveryappilcation.model.User;
import com.uniapp.fastdeliveryappilcation.view.IVerificationView;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UserController implements IUserController {
    IVerificationView verificationView;
    private UserDatabase userDatabase;
    String codeVerificationBySystem;
    FirebaseAuth mAuth;

    public UserController(IVerificationView verificationView) {
        this.verificationView = verificationView;
        FirebaseApp.initializeApp((Context) verificationView);
        userDatabase = Room.databaseBuilder((Context) verificationView, UserDatabase.class, userDatabase.DB_NAME).build();
    }

    /* Implementation business function */
    @Override
    public void onLogin(Map<String, Object> params) {
        sendVerificationSms((String) params.get("phone"));
        //new GetById().execute(params);
    }

    /* Firebase Authentication */
    public void sendVerificationSms(String phoneNumber) {
        mAuth = FirebaseAuth.getInstance();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity((Activity) verificationView)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

        Log.e("loginauthen", phoneNumber);
    }

    public PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeVerificationBySystem = s;
            Log.e("codeverify", s);
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String codeByUser = phoneAuthCredential.getSmsCode();

            // Verify code by user with code by system
            codeVerification(codeByUser);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            verificationView.OnLoginError(e.getMessage());
        }
    };

    @Override
    public void codeVerification(String codeByUser) {
        Log.e("codebyuser", codeByUser);

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeVerificationBySystem, codeByUser);

        mAuth.signInWithCredential(credential).addOnCompleteListener((Activity) verificationView, task -> {
            if (task.isSuccessful()) {
                verificationView.OnLoginSuccess(null);
            }
            else {
                verificationView.OnLoginError(task.getException().getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        userDatabase.close();
    }

    /* Implementation async tasks */
    @SuppressLint("StaticFieldLeak")
    private class GetById extends AsyncTask<Map<String, Object>, Map<String, Object>, Map<String, Object>> {
        @Override
        public Map<String, Object> doInBackground(Map<String, Object>... maps) {
            Map<String, Object> params = maps[0];
            User user = userDatabase.getUserDao().findById((Long) params.get("phoneNumber"));

            if (user != null) {
                params.put("result", true);
            }
            else {
                params.put("result", false);
            }
            return params;
        }

        @Override
        protected void onPostExecute(Map<String, Object> stringObjectMap) {
            super.onPostExecute(stringObjectMap);

            if ((Boolean) stringObjectMap.get("result")) {
                verificationView.OnLoginSuccess((Parcelable) stringObjectMap.get("phoneNumber"));
            }
            else {
                verificationView.OnLoginError("Login failed");
            }

            onDestroy();
        }
    }
}
