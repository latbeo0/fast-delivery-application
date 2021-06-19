package com.uniapp.fastdeliveryappilcation.controller;

import com.uniapp.fastdeliveryappilcation.model.Admin;
import com.uniapp.fastdeliveryappilcation.model.Slider;

public interface IAdminController {
    void Signup(Admin admin);
    void Login(String email, String password);
    void InitMenu();
    void InitProfileMenu();
    void getMenuItemById(Long id);
    void handleMenuItem(Slider slider);
    void handleDeleteMenuItem(Slider slider);
}
