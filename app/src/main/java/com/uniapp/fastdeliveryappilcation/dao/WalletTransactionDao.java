package com.uniapp.fastdeliveryappilcation.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.uniapp.fastdeliveryappilcation.model.SubscriptionHistory;
import com.uniapp.fastdeliveryappilcation.model.TransactionHistory;
import com.uniapp.fastdeliveryappilcation.model.User;

import java.util.List;

@Dao
public interface WalletTransactionDao {
    @Insert
    long[] insetAll(TransactionHistory... transactionHistories);

    @Update
    void updateAll(TransactionHistory... transactionHistories);

    @Delete
    void deleteAll(TransactionHistory... transactionHistories);

    @Query("SELECT * FROM transactionHistory")
    List<TransactionHistory> getAll();
}
