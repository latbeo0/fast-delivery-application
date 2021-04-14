package com.uniapp.fastdeliveryappilcation.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.uniapp.fastdeliveryappilcation.dao.UserDao;
import com.uniapp.fastdeliveryappilcation.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public static final String DB_NAME = "user_db";

    public abstract UserDao getUserDao();
}