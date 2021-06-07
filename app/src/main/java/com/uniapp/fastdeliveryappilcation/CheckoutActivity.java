package com.uniapp.fastdeliveryappilcation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.uniapp.fastdeliveryappilcation.controller.IProductController;
import com.uniapp.fastdeliveryappilcation.controller.IUserController;
import com.uniapp.fastdeliveryappilcation.controller.ProductController;
import com.uniapp.fastdeliveryappilcation.controller.UserController;
import com.uniapp.fastdeliveryappilcation.view.IAddMoneyView;
import com.uniapp.fastdeliveryappilcation.view.ICheckoutView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CheckoutActivity extends AppCompatActivity implements ICheckoutView {
    private TextView totalPrice, planPrice, planDays, selectedDate, deliveryAddress;
    private TextView category_veg, category_nonveg;
    private int no_days, per_day, meal, num;
    private MaterialButton pay, selectDate, defaultAddress;
    public ElegantNumberButton btn1;
    private SharedPreferences sharedPreferences;
    IProductController productController;
    IUserController userController;
    private long credits = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_checkout);

        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        productController = new ProductController(this);
        userController = new UserController(this);

        initData();
        rangeOfItem();
        handleDatePicker();
        handleMap();
        handlePayment();
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        category_nonveg = findViewById(R.id.checkout_nonveg);
        category_veg = findViewById(R.id.checkout_veg);
        totalPrice = findViewById(R.id.checkout_total_price);
        planPrice = findViewById(R.id.checkout_price);
        planDays = findViewById(R.id.days);
        selectedDate = findViewById(R.id.selected_Date);
        selectDate = findViewById(R.id.select_Date);
        deliveryAddress = findViewById(R.id.checkout_address);
        defaultAddress = findViewById(R.id.checkout_update);
        pay = findViewById(R.id.checkout_pay);
        credits = Long.parseLong(sharedPreferences.getString("amount", ""));

        ((ImageView) findViewById(R.id.checkout_image)).setImageResource(R.drawable.photo2);
        ((TextView) findViewById(R.id.bvb)).setText("Credits: " + sharedPreferences.getString("amount", ""));

        defaultAddress.setOnClickListener(v -> {
            ((TextView) findViewById(R.id.checkout_address)).setText(sharedPreferences.getString("address", ""));
        });

        per_day = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("prices")));
        meal = getIntent().getIntExtra("meal", 0);

        no_days = Integer.parseInt(Objects.requireNonNull(getIntent().getStringExtra("days")));
        planPrice.setText("₹ " + no_days * per_day);
        planDays.setText(no_days + " Days");
        totalPrice.setText(planPrice.getText());

        if (meal == 0) {
            category_veg.setVisibility(View.INVISIBLE);
            category_nonveg.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void rangeOfItem() {
        btn1 = findViewById(R.id.number_button2);
        btn1.setRange(1, 5);
        num = Integer.parseInt(btn1.getNumber());
        btn1.setOnClickListener((ElegantNumberButton.OnClickListener) view -> {
            num = Integer.parseInt(btn1.getNumber());
            planPrice.setText("₹" + no_days * per_day);
            totalPrice.setText(planPrice.getText());

            if (num != 0) {
                totalPrice.setText("₹" + Long.parseLong(planPrice.getText().toString().substring(1).trim()) * num);
            }
        });
    }

    private void handleDatePicker() {
        selectDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            @SuppressLint("SimpleDateFormat") DatePickerDialog datePickerDialog = new DatePickerDialog(CheckoutActivity.this,
                    (datePicker, year1, month1, day1) -> {
                        Date date = new Date(year1 - 1900, month1, day1);
                        selectedDate.setText(new SimpleDateFormat("dd MMM, yyyy").format(date));
                    }, year, month, day);

            datePickerDialog.show();
        });
    }

    private void handleMap() {
        deliveryAddress.setOnClickListener(v -> startActivity(new Intent(CheckoutActivity.this, MapActivity.class)));
    }

    private void handlePayment() {
        pay.setOnClickListener(v -> {
            if(credits >=Long.parseLong(totalPrice.getText().toString().substring(1).trim())){
                long remaining_balance = credits - Long.parseLong(totalPrice.getText().toString().substring(1).trim());

                userController.handleWallet(sharedPreferences.getString("id",""), remaining_balance, true, null);
            } else {
                Toast.makeText(getApplicationContext(),"You don't have enough credit balance",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void addSubscription(IAddMoneyView addMoneyView) {
        String type = "Veg";
        if(meal == 0) {
            type = "Non-Veg";
        }

        Map<String,Object> subscription = new HashMap<>();
        subscription.put("date_Of_activation",selectedDate.getText());
        subscription.put("days",no_days);
        subscription.put("userId",sharedPreferences.getString("id",""));
        subscription.put("plan","p1");
        subscription.put("skip",0);
        subscription.put("extented",0);
        subscription.put("status","active");
        subscription.put("type",type);
        subscription.put("no_of_dabba",num);

        productController.handleSubscription(subscription, addMoneyView);
    }

    @Override
    public void addWalletTransaction(String id) {
        String currentTime = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss", Locale.getDefault()).format(new Date());
        String currentDate = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(new Date());

        Map<String,Object> wal=new HashMap<>();
        wal.put("subscriptionId",id);
        wal.put("time_Of_transaction", currentDate +" "+ currentTime);
        wal.put("amount_deducted",Long.parseLong(totalPrice.getText().toString().substring(1).trim()));

        productController.handleWalletTransaction(wal);
    }

    @Override
    public void handlePaymentSuccess() {
        startActivity(new Intent(CheckoutActivity.this, OrderSuccessActivity.class));
    }

    @Override
    public void updatePreferences(String amount) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("amount", amount);
        editor.apply();
    }
}