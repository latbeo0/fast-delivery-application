package com.uniapp.fastdeliveryappilcation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.uniapp.fastdeliveryappilcation.controller.IUserController;
import com.uniapp.fastdeliveryappilcation.controller.UserController;
import com.uniapp.fastdeliveryappilcation.view.IVerificationView;

import java.util.HashMap;
import java.util.Map;

import in.aabhasjindal.otptextview.OtpTextView;

public class VerificationActivity extends AppCompatActivity implements IVerificationView {
    private Button submit;
    private OtpTextView otpTextView;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;

    IUserController userController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_verification);

        /* Initialization firebase */
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        userController = new UserController( this, mAuth);
        otpTextView = findViewById(R.id.otp_view);
        submit  =findViewById(R.id.submit);

        String userDetails = getIntent().getStringExtra("object");
        Map<String, Object> data = new HashMap<>();
        data.put("phone", userDetails);
        data.put("submit", submit);
        data.put("otpTextView", otpTextView);

        userController.onFirebasePhoneAuthentication(data);
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
    }

    @Override
    public void handlePreferences(Map<String, Object> params) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", params.get("id") != null ?  params.get("id").toString() : "");
        editor.putString("email", params.get("email") != null ? (String) params.get("email") : "");
        editor.putString("name", params.get("name") != null ? (String) params.get("email") : "");
        editor.putString("phone", params.get("phone") != null ? (String) params.get("phone") : "");
        editor.putString("amount", params.get("amount") != null ? (String) params.get("amount") : "");
        editor.putString("address", params.get("address") != null ? (String) params.get("address") : "");
        editor.apply();
    }

    /* Events */
    @Override
    public void OnLoginSuccess(Parcelable passingObj) {
        /* Move to next activity */
        Map<String, Object> params = userController.getUserDataFromFirebase();
        userController.SaveUserData(this, params);
        startActivity(new Intent(VerificationActivity.this, MainActivity.class).putExtra("object", passingObj));
    }

    @Override
    public void OnLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}