package com.uniapp.fastdeliveryappilcation.controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.uniapp.fastdeliveryappilcation.dao.UserDao;
import com.uniapp.fastdeliveryappilcation.database.UserDatabase;
import com.uniapp.fastdeliveryappilcation.model.User;
import com.uniapp.fastdeliveryappilcation.view.IAddMoneyView;
import com.uniapp.fastdeliveryappilcation.view.ICheckoutView;
import com.uniapp.fastdeliveryappilcation.view.ILoginView;
import com.uniapp.fastdeliveryappilcation.view.IMapView;
import com.uniapp.fastdeliveryappilcation.view.IProfileView;
import com.uniapp.fastdeliveryappilcation.view.IVerificationView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;

public class UserController implements IUserController {
    IVerificationView verificationView;
    ILoginView loginView;
    IProfileView profile;
    ICheckoutView checkoutView;
    IAddMoneyView addMoneyView;
    IMapView mapView;

    View profileView;
    private UserDatabase userDatabase;
    String codeVerificationBySystem;
    FirebaseAuth mAuth;
    private Button submit;
    private OtpTextView otpTextView;

    public UserController(IVerificationView verificationView, FirebaseAuth mAuth) {
        this.mAuth = mAuth;
        this.verificationView = verificationView;

        userDatabase = Room.databaseBuilder((Context) verificationView, UserDatabase.class, userDatabase.DB_NAME).build();
    }

    public UserController(ILoginView loginView,  FirebaseAuth mAuth) {
        this.mAuth = mAuth;
        this.loginView = loginView;

        userDatabase = Room.databaseBuilder((Context) loginView, UserDatabase.class, userDatabase.DB_NAME).build();
    }

    public UserController(View profileView,IProfileView profile) {
        this.profileView = profileView;
        this.profile = profile;

        userDatabase = Room.databaseBuilder(profileView.getContext(), UserDatabase.class, userDatabase.DB_NAME).build();
    }

    public UserController(ICheckoutView checkoutView) {
        this.checkoutView = checkoutView;

        userDatabase = Room.databaseBuilder((Context) checkoutView, UserDatabase.class, userDatabase.DB_NAME).build();
    }

    public UserController(IAddMoneyView addMoneyView) {
        this.addMoneyView = addMoneyView;

        userDatabase = Room.databaseBuilder((Context) addMoneyView, UserDatabase.class, userDatabase.DB_NAME).build();
    }

    public UserController(IMapView mapView) {
        this.mapView = mapView;

        userDatabase = Room.databaseBuilder((Context) mapView, UserDatabase.class, userDatabase.DB_NAME).build();
    }

    /* Implementation business function */
    @Override
    public void onFirebasePhoneAuthentication(Map<String, Object> params) {
        this.submit = (Button) params.get("submit");
        this.otpTextView = (OtpTextView) params.get("otpTextView");
        sendVerificationSms((String) params.get("phone"));
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
    public Map<String, Object> codeVerification(PhoneAuthCredential credential) {
        Map<String, Object> params = new HashMap<>();
        mAuth.signInWithCredential(credential).addOnCompleteListener((Activity) verificationView, task -> {
            if (task.isSuccessful()) {
               verificationView.OnLoginSuccess(null);
            }
            else {
                verificationView.OnLoginError(task.getException().getMessage());
            }
        });

        return params;
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
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser != null) {
                    loginView.OnLoginSuccess(null);
                } else {
                    loginView.OnLoginError("Cannot get user information!");
                }
            }
            else {
                loginView.OnLoginError(task.getException().getMessage());
            }
        });
    }

    @Override
    public void SaveUserData(Activity activity, Map<String, Object> params) {
        ContextCompat.getMainExecutor(activity).execute(()  -> {
            new saveUserData(activity).execute(params);
        });
    }

    @Override
    public Map<String, Object> getUserDataFromFirebase() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) return null;

        Map<String, Object> params = new HashMap<>();
        params.put("name", firebaseUser.getDisplayName());
        params.put("email", firebaseUser.getEmail());
        params.put("phone", firebaseUser.getPhoneNumber());

        return params;
    }

    @Override
    public void getUserData(String info, IVerificationView verificationView, ILoginView loginView) {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            UserDao userDao = userDatabase.getUserDao();
            User user = userDao.findById(info);
            Map<String, Object> params = new HashMap<>();
            params.put("id",user.getId());
            params.put("email",user.getEmail());
            params.put("phone",user.getPhone());
            params.put("name",user.getName());
            params.put("amount",user.getAmount());
            params.put("address",user.getAddress());

            if (verificationView != null) verificationView.handlePreferences(params);
            else if (loginView != null) loginView.handlePreferences(params);
        });
    }

    /* Implementation async tasks */
    @SuppressLint("StaticFieldLeak")
    private class saveUserData extends AsyncTask<Map<String, Object>, Map<String, Object>, Map<String, Object>> {
        private ProgressDialog dialog;

        public saveUserData(Activity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        public Map<String, Object> doInBackground(Map<String, Object>... maps) {
            Map<String, Object> params = maps[0];
            User user = new User();
            if (params.get("phone") != null) {
                user.setPhone((String) params.get("phone"));
            }

            if (params.get("email") != null) {
                user.setEmail((String) params.get("email"));
            }

            if (params.get("name") != null) {
                user.setName((String) params.get("name"));
            }

            UserDao userDao = userDatabase.getUserDao();
            userDao.insetAll(user);

            return params;
        }

        @Override
        protected void onPostExecute(Map<String, Object> stringObjectMap) {
            super.onPostExecute(stringObjectMap);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            String info = stringObjectMap.get("phone") != null ? (String) stringObjectMap.get("phone") :
                    (stringObjectMap.get("email") != null ? (String) stringObjectMap.get("email") :
                            (stringObjectMap.get("name") != null ? (String) stringObjectMap.get("name") : ""));

            if (verificationView != null) {
                getUserData("%" + info + "%",verificationView, null);
            }
            else {
                getUserData("%" + info + "%", null, loginView);
            }
        }
    }

    @Override
    public void updateData(User user, View view) {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            UserDao userDao = userDatabase.getUserDao();
            userDao.updateAll(user);

            ContextCompat.getMainExecutor(view.getContext()).execute(()  -> {
                profile.handleEditSuccess(user);
            });
        });
    }

    @Override
    public void handleWallet(String id, long price, Boolean isUpdate, IAddMoneyView addMoneyView) {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        if (isUpdate) {
            myExecutor.execute(() -> {
                UserDao userDao = userDatabase.getUserDao();
                userDao.handleWallet(id, String.valueOf(price));

                checkoutView.updatePreferences(String.valueOf(price));
                checkoutView.addSubscription(addMoneyView);
            });
        } else {
            myExecutor.execute(() -> {
                UserDao userDao = userDatabase.getUserDao();
                userDao.handleWallet(id, String.valueOf(price));

                addMoneyView.updatePreferences(String.valueOf(price));
                addMoneyView.addWalletTransaction("", addMoneyView);
            });
        }
    }

    @Override
    public void handleAddress(String id, String address) {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            UserDao userDao = userDatabase.getUserDao();
            userDao.handleAddress(id, address);

            ContextCompat.getMainExecutor((Context) mapView).execute(()  -> {
                mapView.updateAddressPreferences(address);
            });
        });
    }
}
