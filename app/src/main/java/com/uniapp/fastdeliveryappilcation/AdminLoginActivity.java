package com.uniapp.fastdeliveryappilcation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.uniapp.fastdeliveryappilcation.controller.AdminController;
import com.uniapp.fastdeliveryappilcation.controller.IAdminController;
import com.uniapp.fastdeliveryappilcation.view.IAdminLoginView;

public class AdminLoginActivity extends AppCompatActivity implements IAdminLoginView {
    TextView signUp;
    Button login;
    IAdminController adminController;
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.login);
        adminController = new AdminController(this);

        Signup();
        OnLoginListener();
    }

    private void Signup() {
        signUp = findViewById(R.id.gotoRegister);

        signUp.setOnClickListener(t -> {
            startActivity(new Intent(AdminLoginActivity.this, AdminSignupActivity.class));
        });
    }

    private void OnLoginListener() {
        login = findViewById(R.id.btnLogin);
        email = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPassword);

        login.setOnClickListener(t -> {
            if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please fill all empty fields!", Toast.LENGTH_LONG).show();
                return;
            }

            adminController.Login(email.getText().toString(), password.getText().toString());
        });
    }

    @Override
    public void OnLoginSuccess() {
        startActivity(new Intent(AdminLoginActivity.this, AdminActivity.class));
    }

    @Override
    public void OnLoginFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}