package com.uniapp.fastdeliveryappilcation.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Parcelable;

import androidx.room.Room;

import com.uniapp.fastdeliveryappilcation.database.UserDatabase;
import com.uniapp.fastdeliveryappilcation.model.User;
import com.uniapp.fastdeliveryappilcation.view.ILoginView;

import java.util.Map;

public class UserController implements IUserController {
    ILoginView loginView;
    private UserDatabase userDatabase;

    public UserController(ILoginView loginView) {
        this.loginView = loginView;
        userDatabase = Room.databaseBuilder((Context) loginView, UserDatabase.class, userDatabase.DB_NAME).build();
    }

    /* Implementation business function */
    @Override
    public void onLogin(Map<String, Object> params) {

        new GetById().execute(params);
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
}
