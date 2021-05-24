package com.uniapp.fastdeliveryappilcation.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.uniapp.fastdeliveryappilcation.model.User;

@Dao
public interface UserDao {
    @Insert
    void insetAll(User... user);

    @Update
    void updateAll(User... user);

    @Delete
    void deleteAll(User... user);

    @Query("SELECT * FROM User WHERE email = :email")
    User findById(String email);
}
