package com.uniapp.fastdeliveryappilcation.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactionHistory")
public class TransactionHistory {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transactionid")
    long transactionid;

    @ColumnInfo(name = "AddorMinus")
    String AddorMinus;

    @ColumnInfo(name = "name")
    String name;

    @ColumnInfo(name = "time")
    String time;

    @ColumnInfo(name = "paymentmethod")
    String paymentmethod;

    @ColumnInfo(name = "amount")
    String amount;

    @ColumnInfo(name = "subscriptionId")
    String subscriptionId;

    public TransactionHistory(){}

    public TransactionHistory(String addorMinus, String name, String time, String paymentmethod, String amount) {
        this.AddorMinus = addorMinus;
        this.name = name;
        this.time = time;
        this.paymentmethod = paymentmethod;
        this.amount = amount;
    }

    public TransactionHistory(String subscriptionId, String addorMinus, String name, String time, String paymentmethod, String amount) {
        this.AddorMinus = addorMinus;
        this.name = name;
        this.time = time;
        this.paymentmethod = paymentmethod;
        this.amount = amount;
        this.subscriptionId = subscriptionId;
    }

    public long getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(long transactionid) {
        this.transactionid = transactionid;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getAddorMinus() {
        return AddorMinus;
    }

    public void setAddorMinus(String addorMinus) {
        AddorMinus = addorMinus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPaymentmethod() {
        return paymentmethod;
    }

    public void setPaymentmethod(String paymentmethod) {
        this.paymentmethod = paymentmethod;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
