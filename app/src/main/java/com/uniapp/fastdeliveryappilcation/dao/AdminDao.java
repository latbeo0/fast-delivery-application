package com.uniapp.fastdeliveryappilcation.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.uniapp.fastdeliveryappilcation.model.Admin;
import com.uniapp.fastdeliveryappilcation.model.User;

@Dao
public interface AdminDao {
    @Insert
    void insetAll(Admin... admin);

    @Update
    void updateAll(Admin... admin);

    @Delete
    void deleteAll(Admin... admin);

    @Query("SELECT * FROM Admin WHERE email = :email AND password = :password LIMIT 1")
    Admin findById(String email, String password);
}
