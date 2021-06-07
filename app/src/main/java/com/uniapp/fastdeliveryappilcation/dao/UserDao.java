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

    @Query("SELECT * FROM User WHERE email like :email OR phone like :email OR name like :email LIMIT 1")
    User findById(String email);

    @Query("SELECT * FROM User WHERE id = :email LIMIT 1")
    User findBySpecialId(String email);

    @Query("UPDATE User SET amount = :amount WHERE id = :id")
    void handleWallet(String id, String amount);

    @Query("UPDATE User SET address = :amount WHERE id = :id")
    void handleAddress(String id, String amount);
}
