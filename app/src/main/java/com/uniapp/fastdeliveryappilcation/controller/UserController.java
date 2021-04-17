package com.uniapp.fastdeliveryappilcation.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.Log;

import androidx.room.Room;

import com.uniapp.fastdeliveryappilcation.database.UserDatabase;
import com.uniapp.fastdeliveryappilcation.model.User;
import com.uniapp.fastdeliveryappilcation.view.ILoginView;
import com.uniapp.fastdeliveryappilcation.view.ISignUpView;

import java.util.Map;

public class UserController implements IUserController {
    ILoginView loginView;
    ISignUpView signUpView;

    private UserDatabase userDatabase;

    public UserController(ILoginView loginView, ISignUpView signUpView) {
        this.loginView = loginView;
        this.signUpView = signUpView;
        userDatabase = Room.databaseBuilder((Context) loginView, UserDatabase.class, userDatabase.DB_NAME).build();
    }

    /* Implementation business function */
    @Override
    public void onLogin(Map<String, Object> params) {

        new GetById().execute(params);
    }

    @Override
    public void onSignUp(Map<String, Object> params) {
        new OnSignUp().execute(params);
    }

    @Override
    public void onDestroy() {
        userDatabase.close();
    }

    /* Implementation async tasks */
    @SuppressLint("StaticFieldLeak")
    private class GetById extends AsyncTask<Map<String, Object>, Map<String, Object>, Map<String, Object>> {
        @Override
        public Map<String, Object> doInBackground(Map<String, Object>... maps) {
            Map<String, Object> params = maps[0];
            User user = userDatabase.getUserDao().findById((Long) params.get("phoneNumber"));

            if (user != null) {
                params.put("result", true);
            }
            else {
                params.put("result", false);
            }
            return params;
        }

        @Override
        protected void onPostExecute(Map<String, Object> stringObjectMap) {
            super.onPostExecute(stringObjectMap);

            if ((Boolean) stringObjectMap.get("result")) {
                loginView.OnLoginSuccess("Login Successfully!", (Parcelable) stringObjectMap.get("phoneNumber"));
            }
            else {
                loginView.OnLoginError("Login failed");
            }

            onDestroy();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class OnSignUp extends AsyncTask<Map<String, Object>, User, User> {
        @Override
        public User doInBackground(Map<String, Object>... maps) {
            User user = (User) maps[0];

            User result = userDatabase.getUserDao().insetAll(user);
            Log.e("Insert user", result.getName());
            return result;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);

            if (user != null) {
                signUpView.OnSignUpSuccess(user);
            }
            else {
                signUpView.OnSignUpError("Login failed !");
            }

            onDestroy();
        }
    }
}
