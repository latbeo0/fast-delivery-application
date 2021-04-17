package com.uniapp.fastdeliveryappilcation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.uniapp.fastdeliveryappilcation.controller.IUserController;
import com.uniapp.fastdeliveryappilcation.controller.UserController;
import com.uniapp.fastdeliveryappilcation.model.User;
import com.uniapp.fastdeliveryappilcation.view.ISignUpView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignupActivity extends AppCompatActivity implements ISignUpView {

    IUserController userController;
    Button next;
    TextInputEditText name, email, phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userController = new UserController(null, this);

        signUp();
    }

    @Override
    public void OnSignUpSuccess(User user) {
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
    }

    @Override
    public void OnSignUpError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /* SignUp */
    private void signUp() {
        next = findViewById(R.id.next);

        name = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        phone = findViewById(R.id.altphone);
        password = findViewById(R.id.user_password);


        next.setOnClickListener(view -> {
            Map<String, Object> params = new HashMap<>();
            params.put("name",name);
            params.put("email",email);
            params.put("phone",phone);
            params.put("password",password);

            userController.onSignUp(params);
        });
    }
}