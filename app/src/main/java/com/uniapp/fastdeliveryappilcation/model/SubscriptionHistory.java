package com.uniapp.fastdeliveryappilcation.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "subscriptionHistory")
public class SubscriptionHistory {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    long id;

    @ColumnInfo(name = "subscriptionid")
    long subscriptionid;

    @ColumnInfo(name = "plan")
    String plan;

    @ColumnInfo(name = "time")
    String time;

    public SubscriptionHistory(long subscriptionid, String plan, String time) {
        this.plan = plan;
        this.time = time;
        this.subscriptionid = subscriptionid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSubscriptionid() {
        return subscriptionid;
    }

    public void setSubscriptionid(long subscriptionid) {
        this.subscriptionid = subscriptionid;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
