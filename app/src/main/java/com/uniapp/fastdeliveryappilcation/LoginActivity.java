package com.uniapp.fastdeliveryappilcation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.uniapp.fastdeliveryappilcation.controller.IUserController;
import com.uniapp.fastdeliveryappilcation.controller.UserController;
import com.uniapp.fastdeliveryappilcation.view.ILoginView;

import java.util.HashMap;
import java.util.Map;

import com.facebook.FacebookSdk;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    private GoogleSignInClient googleSignInClient;
    private Integer RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;
    private CallbackManager callbackManager;
    private SharedPreferences sharedPreferences;

    IUserController userController;
    EditText phoneNumber;
    ImageView googleLogin;
    LoginButton facebookLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        /* Initialization facebook */
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        setContentView(R.layout.activity_login);

        /* Initialization firebase */
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        /* Initialization google */
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        /* Initialization Controller */
        userController = new UserController(null, this,null,null, mAuth);
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        OnLoginCheck();

        OnGoogleLogin();

        OnFacebookLogin();

        OnOtpLogin();

    }

    private void OnLoginCheck() {
        if (!sharedPreferences.getString("email","").isEmpty()
            && !sharedPreferences.getString("name","").isEmpty()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    /* Button event listeners */
    @Override
    public void OnOtpLogin() {
        phoneNumber = findViewById(R.id.login_number);

        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.length()==10){
                    String phone_number = "+84" + charSequence.toString();

                    Map<String, Object> passingObj = new HashMap<>();
                    passingObj.put("phone", phone_number);

                    startActivity(new Intent(LoginActivity.this, VerificationActivity.class)
                            .putExtra("object", (String) passingObj.get("phone")));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void OnGoogleLogin() {
        googleLogin=findViewById(R.id.googleLogin);
        googleLogin.setOnClickListener(v -> {
            Intent intent = googleSignInClient.getSignInIntent();
            startActivityForResult(intent, RC_SIGN_IN);
        });
    }

    @Override
    public void OnFacebookLogin() {
        facebookLogin = findViewById(R.id.facebookLogin);
        callbackManager = CallbackManager.Factory.create();
        facebookLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                userController.onFacebookAuthentication(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            userController.OnGoogleAuthentication(data);
        }
    }

    @Override
    public void handlePreferences(Map<String, Object> params) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", params.get("email") != null ? (String) params.get("email") : "");
        editor.putString("name", params.get("name") != null ? (String) params.get("email") : "");
        editor.putString("phone", params.get("phone") != null ? (String) params.get("phone") : "");
        editor.apply();
    }

    /* Events */
    @Override
    public void OnLoginSuccess(Parcelable passingObj) {
        /* Move to next activity */
        Map<String, Object> params = userController.getUserDataFromFirebase();
        userController.SaveUserData(this, params);
        startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("object", passingObj));
    }

    @Override
    public void OnLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}