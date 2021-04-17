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
    User insetAll(User... user);

    @Update
    User updateAll(User... user);

    @Delete
    void deleteAll(User... user);

    @Query("SELECT * FROM User WHERE Id = :id")
    User findById(Long id);
}
