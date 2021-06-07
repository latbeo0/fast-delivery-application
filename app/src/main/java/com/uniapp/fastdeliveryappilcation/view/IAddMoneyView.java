package com.uniapp.fastdeliveryappilcation.view;

public interface IAddMoneyView {
    void updatePreferences(String amount);
    void addWalletTransaction(String id, IAddMoneyView addMoneyView);
}
