package com.uniapp.fastdeliveryappilcation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.uniapp.fastdeliveryappilcation.controller.IUserController;
import com.uniapp.fastdeliveryappilcation.controller.UserController;
import com.uniapp.fastdeliveryappilcation.view.ILoginView;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    IUserController userController;
    EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userController = new UserController(this);

        OnLogin();
    }

    /* Events */
    @Override
    public void OnLoginSuccess(String message, Parcelable passingObj) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

        /* Move to next activity */
        startActivity(new Intent(LoginActivity.this, MainActivity.class).putExtra("object", passingObj));
    }

    @Override
    public void OnLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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

                    userController.onLogin(passingObj);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}