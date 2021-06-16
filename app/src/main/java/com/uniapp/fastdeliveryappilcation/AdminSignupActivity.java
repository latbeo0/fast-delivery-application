package com.uniapp.fastdeliveryappilcation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.uniapp.fastdeliveryappilcation.controller.AdminController;
import com.uniapp.fastdeliveryappilcation.controller.IAdminController;
import com.uniapp.fastdeliveryappilcation.model.Admin;
import com.uniapp.fastdeliveryappilcation.view.IAdminSignupView;

public class AdminSignupActivity extends AppCompatActivity implements IAdminSignupView {
    IAdminController adminController;
    MaterialButton next;
    TextInputEditText name, email, phone, password, confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_signup);
        adminController = new AdminController(this);

        OnSignupListener();
    }

    @Override
    public void OnSignupSuccess() {
        startActivity(new Intent(AdminSignupActivity.this, AdminLoginActivity.class));
    }

    private void OnSignupListener() {
        next = findViewById(R.id.next);
        name = findViewById(R.id.user_name);
        email = findViewById(R.id.user_email);
        phone = findViewById(R.id.altphone);
        password = findViewById(R.id.user_password);
        confirm = findViewById(R.id.user_confirm_password);

        next.setOnClickListener(t -> {
            if (!confirm.getText().toString().equals(password.getText().toString())) {
                Toast.makeText(this, "Password doesn't macht!", Toast.LENGTH_LONG).show();
                return;
            }

            adminController.Signup(new Admin(name.getText().toString(), email.getText().toString(), phone.getText().toString(), password.getText().toString()));
        });
    }
}