package com.uniapp.fastdeliveryappilcation.model;

public class SubscriptionHistory {
    String subscriptionid;
    String plan;
    String time;
    String AddorMinus;
    String amount;

    public SubscriptionHistory(String subscriptionid, String plan, String time, String addorMinus, String amount) {
        this.subscriptionid = subscriptionid;
        this.plan = plan;
        this.time = time;
        AddorMinus = addorMinus;
        this.amount = amount;
    }

    public String getSubscriptionId() {
        return subscriptionid;
    }

    public void setSubscriptionId(String subscriptionid) {
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

    public String getAddorMinus() {
        return AddorMinus;
    }

    public void setAddorMinus(String addorMinus) {
        AddorMinus = addorMinus;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
