package com.uniapp.fastdeliveryappilcation.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.uniapp.fastdeliveryappilcation.model.ActiveSubscription;
import com.uniapp.fastdeliveryappilcation.model.SubscriptionHistory;

import java.util.List;

@Dao
public interface SubscriptionHistoryDao {
    @Insert
    long[] insetAll(SubscriptionHistory... subscriptionHistories);

    @Query("SELECT * FROM subscriptionHistory")
    List<SubscriptionHistory> getAll();
}
