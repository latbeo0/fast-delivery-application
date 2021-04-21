package com.uniapp.fastdeliveryappilcation.controller;

import java.util.Map;

public interface IUserController {
    void onLogin(Map<String, Object> params);
    void codeVerification(String codeByUser);
    void onDestroy();
}
