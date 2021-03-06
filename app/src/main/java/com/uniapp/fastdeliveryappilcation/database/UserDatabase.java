package com.uniapp.fastdeliveryappilcation.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.uniapp.fastdeliveryappilcation.dao.AdminDao;
import com.uniapp.fastdeliveryappilcation.dao.SliderDao;
import com.uniapp.fastdeliveryappilcation.dao.SubscriptionDao;
import com.uniapp.fastdeliveryappilcation.dao.SubscriptionHistoryDao;
import com.uniapp.fastdeliveryappilcation.dao.UserDao;
import com.uniapp.fastdeliveryappilcation.dao.WalletTransactionDao;
import com.uniapp.fastdeliveryappilcation.model.ActiveSubscription;
import com.uniapp.fastdeliveryappilcation.model.Admin;
import com.uniapp.fastdeliveryappilcation.model.Slider;
import com.uniapp.fastdeliveryappilcation.model.SubscriptionHistory;
import com.uniapp.fastdeliveryappilcation.model.TransactionHistory;
import com.uniapp.fastdeliveryappilcation.model.User;

@Database(entities = {User.class, SubscriptionHistory.class, TransactionHistory.class, ActiveSubscription.class, Admin.class, Slider.class}, version = 6, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public static final String DB_NAME = "user_db";

    public abstract UserDao getUserDao();
    public abstract SubscriptionDao getSubscriptionDao();
    public abstract SubscriptionHistoryDao getSubscriptionHistoryDao();
    public abstract WalletTransactionDao getWalletTransactionDao();
    public abstract AdminDao getAdminDao();
    public abstract SliderDao getSliderDao();
}