package com.uniapp.fastdeliveryappilcation.controller;

import android.content.Context;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.uniapp.fastdeliveryappilcation.dao.AdminDao;
import com.uniapp.fastdeliveryappilcation.dao.SliderDao;
import com.uniapp.fastdeliveryappilcation.dao.UserDao;
import com.uniapp.fastdeliveryappilcation.database.UserDatabase;
import com.uniapp.fastdeliveryappilcation.model.Admin;
import com.uniapp.fastdeliveryappilcation.model.Slider;
import com.uniapp.fastdeliveryappilcation.model.User;
import com.uniapp.fastdeliveryappilcation.view.IAdminLoginView;
import com.uniapp.fastdeliveryappilcation.view.IAdminMenuItemView;
import com.uniapp.fastdeliveryappilcation.view.IAdminMenuView;
import com.uniapp.fastdeliveryappilcation.view.IAdminProfileView;
import com.uniapp.fastdeliveryappilcation.view.IAdminSignupView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AdminController implements IAdminController {
    private UserDatabase userDatabase;
    IAdminSignupView adminSignupView;
    IAdminLoginView adminLoginView;
    IAdminMenuView adminMenuView;
    IAdminProfileView adminProfileView;
    IAdminMenuItemView adminMenuItemView;
    View adminView;

    public AdminController(IAdminSignupView adminSignupView) {
        this.adminSignupView = adminSignupView;
        userDatabase = Room.databaseBuilder((Context) adminSignupView, UserDatabase.class, userDatabase.DB_NAME).build();
    }

    public AdminController(IAdminLoginView adminLoginView) {
        this.adminLoginView = adminLoginView;
        userDatabase = Room.databaseBuilder((Context) adminLoginView, UserDatabase.class, userDatabase.DB_NAME).build();
    }

    public AdminController(IAdminMenuItemView adminMenuItemView) {
        this.adminMenuItemView = adminMenuItemView;
        userDatabase = Room.databaseBuilder((Context) adminMenuItemView, UserDatabase.class, userDatabase.DB_NAME).build();
    }

    public AdminController(IAdminMenuView adminMenuView, View view) {
        this.adminMenuView = adminMenuView;
        this.adminView = view;
        userDatabase = Room.databaseBuilder(view.getContext(), UserDatabase.class, userDatabase.DB_NAME).build();
    }

    public AdminController(IAdminProfileView adminProfileView, View view) {
        this.adminProfileView = adminProfileView;
        this.adminView = view;
        userDatabase = Room.databaseBuilder(view.getContext(), UserDatabase.class, userDatabase.DB_NAME).build();
    }

    @Override
    public void Signup(Admin admin) {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            AdminDao adminDao = userDatabase.getAdminDao();
            adminDao.insetAll(admin);

            ContextCompat.getMainExecutor((Context) adminSignupView).execute(()  -> {
                adminSignupView.OnSignupSuccess();
            });
        });
    }

    @Override
    public void Login(String email, String password) {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            AdminDao adminDao = userDatabase.getAdminDao();
            Admin admin = adminDao.findById(email, password);

            ContextCompat.getMainExecutor((Context) adminLoginView).execute(()  -> {
                if (admin != null) {
                    adminLoginView.OnLoginSuccess();
                } else {
                    adminLoginView.OnLoginFailed("Account does not existed!");
                }
            });
        });
    }

    @Override
    public void InitMenu() {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            SliderDao sliderDao = userDatabase.getSliderDao();
            List<Slider> list = sliderDao.getAll();

            ContextCompat.getMainExecutor(adminView.getContext()).execute(()  -> {
                adminMenuView.initData(adminView, list);
            });
        });
    }

    @Override
    public void InitProfileMenu() {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            UserDao userDao = userDatabase.getUserDao();
            List<User> list = userDao.getAll();

            ContextCompat.getMainExecutor(adminView.getContext()).execute(()  -> {
                adminProfileView.initData(adminView, list);
            });
        });
    }

    @Override
    public void getMenuItemById(Long id) {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            SliderDao sliderDao = userDatabase.getSliderDao();
            Slider slider = sliderDao.getMenuItemById(id);

            ContextCompat.getMainExecutor((Context) adminMenuItemView).execute(()  -> {
                adminMenuItemView.showItem(slider);
            });
        });
    }

    @Override
    public void handleMenuItem(Slider slider) {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            SliderDao sliderDao = userDatabase.getSliderDao();

            if (slider.getId() != 0) {
                sliderDao.updateAll(slider);
            } else {
                sliderDao.insetAll(slider);
            }

            ContextCompat.getMainExecutor((Context) adminMenuItemView).execute(()  -> {
                adminMenuItemView.handleItemSuccess();
            });
        });
    }
}
