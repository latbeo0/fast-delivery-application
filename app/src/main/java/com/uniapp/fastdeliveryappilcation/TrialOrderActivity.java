package com.uniapp.fastdeliveryappilcation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class TrialOrderActivity extends AppCompatActivity {
    Button veg_subcribe,nonveg_subcribe;
    ImageButton backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_trial_order);

        veg_subcribe=findViewById(R.id.veg);
        nonveg_subcribe=findViewById(R.id.nonveg);

        backArrow = findViewById(R.id.arrowback);
        backArrow.setOnClickListener(v -> onBackPressed());
    }
}