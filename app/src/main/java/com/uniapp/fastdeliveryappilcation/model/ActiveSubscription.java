package com.uniapp.fastdeliveryappilcation.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "subscription")
public class ActiveSubscription {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subscriptionid")
    private long subcriptionid;

    @ColumnInfo(name = "userId")
    private String userId;

    @ColumnInfo(name = "plan_name")
    private String plan_name;

    @ColumnInfo(name = "start_date")
    private String start_date;

    @ColumnInfo(name = "end_date")
    private String end_date;

    @ColumnInfo(name = "dabba")
    private String dabba;

    @Ignore
    public ActiveSubscription()
    {

    }

    public ActiveSubscription(long subcriptionid, String plan_name, String start_date, String end_date, String dabba) {
        this.subcriptionid = subcriptionid;
        this.plan_name = plan_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.dabba=dabba;
    }

    @Ignore
    public ActiveSubscription(String plan_name, String start_date, String end_date, String dabba, long userId) {
        this.plan_name = plan_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.dabba=dabba;
        this.userId = String.valueOf(userId);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDabba() {
        return dabba;
    }

    public void setDabba(String dabba) {
        this.dabba = dabba;
    }

    public long getSubcriptionid() {
        return subcriptionid;
    }

    public void setSubcriptionid(long subcriptionid) {
        this.subcriptionid = subcriptionid;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
