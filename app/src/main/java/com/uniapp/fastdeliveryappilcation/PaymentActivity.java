package com.uniapp.fastdeliveryappilcation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.Objects;

public class PaymentActivity extends AppCompatActivity {
    private TextView number,balance;
    private FloatingActionButton add_money;
    private ImageView backArrow;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        Objects.requireNonNull(getSupportActionBar()).hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_payment);
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        init();
    }

    private void init(){
        backArrow = findViewById(R.id.b2);
        backArrow.setOnClickListener(v -> onBackPressed());

        number  = findViewById(R.id.payment_number);
        balance = findViewById(R.id.credit);
        add_money=findViewById(R.id.add_money);

        balance.setText(sharedPreferences.getString("amount", "").isEmpty() ? "0" : sharedPreferences.getString("amount", ""));
        number.setText(sharedPreferences.getString("phone", ""));

        add_money.setOnClickListener(view -> {
            Intent i=new Intent(PaymentActivity.this,AddMoneyActivity.class);
            startActivity(i);
        });
    }
}