package com.uniapp.fastdeliveryappilcation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.uniapp.fastdeliveryappilcation.controller.IUserController;
import com.uniapp.fastdeliveryappilcation.view.ILoginView;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    IUserController userController;
    EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_login);
        OnLogin();
    }

    /* Button event listeners */
    public void OnLogin() {
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
}