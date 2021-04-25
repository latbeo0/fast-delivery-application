package com.uniapp.fastdeliveryappilcation.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.uniapp.fastdeliveryappilcation.database.UserDatabase;
import com.uniapp.fastdeliveryappilcation.model.User;
import com.uniapp.fastdeliveryappilcation.view.ILoginView;
import com.uniapp.fastdeliveryappilcation.view.IVerificationView;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;

public class UserController implements IUserController {
    IVerificationView verificationView;
    ILoginView loginView;
    private UserDatabase userDatabase;
    String codeVerificationBySystem;
    FirebaseAuth mAuth;
    private Button submit;
    private OtpTextView otpTextView;

    public UserController(IVerificationView verificationView, ILoginView loginView, FirebaseAuth mAuth) {
        this.mAuth = mAuth;
        this.verificationView = verificationView;
        this.loginView = loginView;
        if (verificationView != null) {
            userDatabase = Room.databaseBuilder((Context) verificationView, UserDatabase.class, userDatabase.DB_NAME).build();
        } else {
            userDatabase = Room.databaseBuilder((Context) loginView, UserDatabase.class, userDatabase.DB_NAME).build();
        }
    }

    /* Implementation business function */
    @Override
    public void onFirebasePhoneAuthentication(Map<String, Object> params) {
        this.submit = (Button) params.get("submit");
        this.otpTextView = (OtpTextView) params.get("otpTextView");
        sendVerificationSms((String) params.get("phone"));
        //new GetById().execute(params);
    }

    /* Firebase Authentication */
    public void sendVerificationSms(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity((Activity) verificationView)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            codeVerificationBySystem = s;

            submit.setOnClickListener(v -> {
                String code = otpTextView.getOTP();

                if (code.isEmpty() || code.length() < 6) {

                    otpTextView.showError();
                    return;
                }
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeVerificationBySystem, code);
                codeVerification(credential);

            });
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            // Verify code by user with code by system
            codeVerification(phoneAuthCredential);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            verificationView.OnLoginError(e.getMessage());
        }
    };

    @Override
    public void codeVerification(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener((Activity) verificationView, task -> {
            if (task.isSuccessful()) {
                verificationView.OnLoginSuccess(null);
            }
            else {
                verificationView.OnLoginError(task.getException().getMessage());
            }
        });
    }

    /* Google Authentication */
    @Override
    public void OnGoogleAuthentication(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
            AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);

            mAuth.signInWithCredential(authCredential).addOnCompleteListener((Activity) loginView, task1 -> {
                if (task1.isSuccessful()) {
                    loginView.OnLoginSuccess(null);
                }
                else {
                    loginView.OnLoginError(task.getException().getMessage());
                }
            });
        } catch (Exception e) {

        }
    }

    /* Facebook Authentication */
    @Override
    public void onFacebookAuthentication(AccessToken accessToken) {
        AuthCredential authCredential = FacebookAuthProvider.getCredential(accessToken.getToken());

        mAuth.signInWithCredential(authCredential).addOnCompleteListener((Activity) loginView, task -> {
            if (task.isSuccessful()) {
                loginView.OnLoginSuccess(null);
            }
            else {
                loginView.OnLoginError(task.getException().getMessage());
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
