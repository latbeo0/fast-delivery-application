package com.uniapp.fastdeliveryappilcation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.uniapp.fastdeliveryappilcation.controller.IProductController;
import com.uniapp.fastdeliveryappilcation.controller.IUserController;
import com.uniapp.fastdeliveryappilcation.controller.ProductController;
import com.uniapp.fastdeliveryappilcation.controller.UserController;
import com.uniapp.fastdeliveryappilcation.view.IAddMoneyView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddMoneyActivity extends AppCompatActivity implements IAddMoneyView {
    private EditText et_amount;
    private ImageView backArrow, done;
    private TextView min;
    IUserController userController;
    IProductController productController;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_add_money);

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        userController = new UserController(this);
        productController = new ProductController(this);

        initViews();
    }

    private void initViews() {
        done = findViewById(R.id.done1);
        backArrow = findViewById(R.id.b1);
        et_amount = findViewById(R.id.amount);
        min = findViewById(R.id.minimum_indicator);
        backArrow.setOnClickListener(v -> onBackPressed());

        et_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @SuppressLint("RestrictedApi")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()>0){
                    done.setVisibility(View.VISIBLE);
                }
                else{
                    done.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        done.setOnClickListener(v -> {
            long amount = sharedPreferences.getString("amount", "0").isEmpty() ? 0 : Long.parseLong(sharedPreferences.getString("amount", "0"));
            long price = amount + Long.parseLong(et_amount.getText().toString());
            userController.handleWallet(sharedPreferences.getString("id", ""), price, false, this);
        });
    }

    @Override
    public void addWalletTransaction(String id, IAddMoneyView addMoneyView) {
        String currentTime = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        String currentDate = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(new Date());

        Map<String,Object> wal=new HashMap<>();
        wal.put("time_Of_transaction", currentDate +" "+ currentTime);
        wal.put("amount_added",et_amount.getText().toString());

        productController.handleAddWalletTransaction(wal);
    }

    @Override
    public void updatePreferences(String amount) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("amount", amount);
        editor.apply();

        startActivity(new Intent(AddMoneyActivity.this, MainActivity.class));
    }
}
