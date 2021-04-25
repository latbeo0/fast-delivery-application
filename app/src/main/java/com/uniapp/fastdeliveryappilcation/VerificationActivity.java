package com.uniapp.fastdeliveryappilcation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

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

        userController = new UserController( this, null, mAuth);
        otpTextView = findViewById(R.id.otp_view);
        submit  =findViewById(R.id.submit);

        String userDetails = getIntent().getStringExtra("object");
        Map<String, Object> data = new HashMap<>();
        data.put("phone", userDetails);
        data.put("submit", submit);
        data.put("otpTextView", otpTextView);

        userController.onFirebasePhoneAuthentication(data);

    }

    /* Events */
    @Override
    public void OnLoginSuccess(Parcelable passingObj) {
        /* Move to next activity */
        startActivity(new Intent(VerificationActivity.this, MainActivity.class).putExtra("object", passingObj));
    }

    @Override
    public void OnLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}