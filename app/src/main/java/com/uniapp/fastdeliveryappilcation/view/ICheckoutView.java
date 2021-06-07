package com.uniapp.fastdeliveryappilcation.view;

public interface ICheckoutView {
    void updatePreferences(String amount);
    void addSubscription(IAddMoneyView addMoneyView);
    void addWalletTransaction(String id);
    void handlePaymentSuccess();
}
