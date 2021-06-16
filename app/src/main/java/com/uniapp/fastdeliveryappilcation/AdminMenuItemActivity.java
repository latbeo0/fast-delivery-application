package com.uniapp.fastdeliveryappilcation;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.uniapp.fastdeliveryappilcation.controller.AdminController;
import com.uniapp.fastdeliveryappilcation.controller.IAdminController;
import com.uniapp.fastdeliveryappilcation.model.Slider;
import com.uniapp.fastdeliveryappilcation.view.IAdminMenuItemView;

public class AdminMenuItemActivity extends AppCompatActivity implements IAdminMenuItemView {
    IAdminController adminController;
    MaterialButton next;
    TextInputEditText title, description, meal, combo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_admin_add_menu);
        adminController = new AdminController(this);
        initVariable();

        if (getIntent().getExtras() != null) {
            initData(getIntent().getExtras().getLong("menuId"));
        }

    }

    private void initVariable() {
        next = findViewById(R.id.next);
        title = findViewById(R.id.menu_title);
        description = findViewById(R.id.menu_description);
        meal = findViewById(R.id.menu_meal);
        combo = findViewById(R.id.menu_combo);

        next.setOnClickListener(t -> {
            Slider slider = new Slider();

            slider.setId(getIntent().getExtras() != null ? getIntent().getExtras().getLong("menuId") : 0);
            slider.setTitle(title.getText().toString());
            slider.setDesc(description.getText().toString());
            slider.setMeal(Integer.parseInt(meal.getText().toString()));
            slider.setCombo(Integer.parseInt(combo.getText().toString()));
            slider.setImage(R.drawable.photo1);

            adminController.handleMenuItem(slider);
        });
    }

    private void initData(Long id) {
        adminController.getMenuItemById(id);
    }

    @Override
    public void showItem(Slider slider) {
        title.setText(slider.getTitle());
        description.setText(slider.getDesc());
        meal.setText(Integer.toString(slider.getMeal()));
        combo.setText(Integer.toString(slider.getCombo()));
    }

    @Override
    public void handleItemSuccess() {
        startActivity(new Intent(AdminMenuItemActivity.this, AdminActivity.class));
    }

}