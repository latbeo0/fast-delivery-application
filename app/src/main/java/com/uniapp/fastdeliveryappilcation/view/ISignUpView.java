package com.uniapp.fastdeliveryappilcation.view;

import com.uniapp.fastdeliveryappilcation.model.User;

public interface ISignUpView {
    void OnSignUpSuccess(User user);
    void OnSignUpError(String message);

}
