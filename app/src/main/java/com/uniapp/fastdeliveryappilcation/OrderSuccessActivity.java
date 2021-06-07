package com.uniapp.fastdeliveryappilcation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uniapp.fastdeliveryappilcation.fragment.DashboardFragment;

public class OrderSuccessActivity extends AppCompatActivity{

    private TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.orer_success_layout);
        text = findViewById(R.id.my_orders);
        text.setOnClickListener(v -> {
            startActivity(new Intent(OrderSuccessActivity.this, MainActivity.class));
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
