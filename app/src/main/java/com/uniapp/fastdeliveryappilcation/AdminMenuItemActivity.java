package com.uniapp.fastdeliveryappilcation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.uniapp.fastdeliveryappilcation.controller.AdminController;
import com.uniapp.fastdeliveryappilcation.controller.IAdminController;
import com.uniapp.fastdeliveryappilcation.model.Slider;
import com.uniapp.fastdeliveryappilcation.view.IAdminMenuItemView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminMenuItemActivity extends AppCompatActivity implements IAdminMenuItemView {
    IAdminController adminController;
    MaterialButton next, delete;
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
        delete = findViewById(R.id.delete);
        title = findViewById(R.id.menu_title);
        description = findViewById(R.id.menu_description);
        meal = findViewById(R.id.menu_meal);
        combo = findViewById(R.id.menu_combo);
        delete.setVisibility(View.INVISIBLE);

        next.setOnClickListener(t -> {
            if (validateData()) {
                Toast.makeText(this,"Please fill all empty fields!",Toast.LENGTH_SHORT).show();
                return;
            }

            Slider slider = new Slider();

            slider.setId(getIntent().getExtras() != null ? getIntent().getExtras().getLong("menuId") : 0);
            slider.setTitle(title.getText().toString());
            slider.setDesc(description.getText().toString());
            slider.setMeal(Integer.parseInt(meal.getText().toString()));
            slider.setCombo(Integer.parseInt(combo.getText().toString()));
            slider.setImage(R.drawable.photo1);

            adminController.handleMenuItem(slider);
        });

        delete.setOnClickListener(t -> {
            Slider slider = new Slider();

            slider.setId(getIntent().getExtras() != null ? getIntent().getExtras().getLong("menuId") : 0);
            slider.setTitle(title.getText().toString());
            slider.setDesc(description.getText().toString());
            slider.setMeal(Integer.parseInt(meal.getText().toString()));
            slider.setCombo(Integer.parseInt(combo.getText().toString()));
            slider.setImage(R.drawable.photo1);

            adminController.handleDeleteMenuItem(slider);
        });
    }

    private void initData(Long id) {
        adminController.getMenuItemById(id);
    }

    private boolean validateData() {
        if (title.getText().toString().isEmpty()
        || description.getText().toString().isEmpty()
        || meal.getText().toString().isEmpty()
        || combo.getText().toString().isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcherDescription = pattern.matcher(description.getText().toString().trim());
        Matcher matcherTitle = pattern.matcher(title.getText().toString().trim());

        if (matcherDescription.find() || matcherTitle.find()) {
            return false;
        }

        return true;
    }

    @Override
    public void showItem(Slider slider) {
        title.setText(slider.getTitle());
        description.setText(slider.getDesc());
        meal.setText(Integer.toString(slider.getMeal()));
        combo.setText(Integer.toString(slider.getCombo()));
        delete.setVisibility(View.VISIBLE);
    }

    @Override
    public void handleItemSuccess() {
        startActivity(new Intent(AdminMenuItemActivity.this, AdminActivity.class));
    }

}