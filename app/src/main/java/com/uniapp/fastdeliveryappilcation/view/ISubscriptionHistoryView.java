package com.uniapp.fastdeliveryappilcation.view;

import com.uniapp.fastdeliveryappilcation.model.ActiveSubscription;
import com.uniapp.fastdeliveryappilcation.model.SubscriptionHistory;
import com.uniapp.fastdeliveryappilcation.model.TransactionHistory;

import java.util.List;

public interface ISubscriptionHistoryView {
    void initData(List<SubscriptionHistory> transactionHistoryList);
}
