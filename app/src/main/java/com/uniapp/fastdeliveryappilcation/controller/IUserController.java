package com.uniapp.fastdeliveryappilcation.controller;

import java.util.Map;

public interface IUserController {
    void onLogin(Map<String, Object> params);
    void onDestroy();
}
