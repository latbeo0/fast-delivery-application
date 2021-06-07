package com.uniapp.fastdeliveryappilcation.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.uniapp.fastdeliveryappilcation.model.ActiveSubscription;
import com.uniapp.fastdeliveryappilcation.model.User;

import java.util.List;

@Dao
public interface SubscriptionDao {
    @Insert
    long[] insetAll(ActiveSubscription... activeSubscriptions);

    @Update
    void updateAll(ActiveSubscription... activeSubscriptions);

    @Delete
    void deleteAll(ActiveSubscription... activeSubscriptions);

    @Query("SELECT * FROM subscription WHERE userId = :id LIMIT 1")
    ActiveSubscription findById(String id);

    @Query("SELECT * FROM subscription")
    List<ActiveSubscription> getAll();
}
