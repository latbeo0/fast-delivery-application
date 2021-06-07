package com.uniapp.fastdeliveryappilcation.controller;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.room.Room;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.uniapp.fastdeliveryappilcation.adapter.SliderAdapter;
import com.uniapp.fastdeliveryappilcation.dao.SubscriptionDao;
import com.uniapp.fastdeliveryappilcation.dao.SubscriptionHistoryDao;
import com.uniapp.fastdeliveryappilcation.dao.UserDao;
import com.uniapp.fastdeliveryappilcation.dao.WalletTransactionDao;
import com.uniapp.fastdeliveryappilcation.database.UserDatabase;
import com.uniapp.fastdeliveryappilcation.fragment.SubscriptionFragment;
import com.uniapp.fastdeliveryappilcation.model.ActiveSubscription;
import com.uniapp.fastdeliveryappilcation.model.Slider;
import com.uniapp.fastdeliveryappilcation.model.SubscriptionHistory;
import com.uniapp.fastdeliveryappilcation.model.TransactionHistory;
import com.uniapp.fastdeliveryappilcation.model.User;
import com.uniapp.fastdeliveryappilcation.ultils.Adapter;

import com.uniapp.fastdeliveryappilcation.R;
import com.uniapp.fastdeliveryappilcation.ultils.ViewPagerAdapter;
import com.uniapp.fastdeliveryappilcation.view.IAddMoneyView;
import com.uniapp.fastdeliveryappilcation.view.ICheckoutView;
import com.uniapp.fastdeliveryappilcation.view.IDashboardView;
import com.uniapp.fastdeliveryappilcation.view.ISubscriptionHistoryView;
import com.uniapp.fastdeliveryappilcation.view.ISubscriptionView;
import com.uniapp.fastdeliveryappilcation.view.ITransactionHistoryView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProductController implements IProductController{
    Adapter adapter;
    List<Slider> models;
    SliderAdapter sliderAdapter;
    private UserDatabase userDatabase;
    ICheckoutView checkoutView;
    ISubscriptionView subscriptionView;
    IDashboardView dashboardView;
    ISubscriptionHistoryView subscriptionHistoryView;
    ITransactionHistoryView transactionHistoryView;
    IAddMoneyView addMoneyView;
    View view;

    public ProductController() {
    }

    public ProductController(ICheckoutView checkoutView) {
        this.checkoutView = checkoutView;
        userDatabase = Room.databaseBuilder((Context) checkoutView, UserDatabase.class, userDatabase.DB_NAME).build();
    }

    public ProductController(ISubscriptionView subscriptionView, View view) {
        this.subscriptionView = subscriptionView;
        this.view = view;
        userDatabase = Room.databaseBuilder(view.getContext(), UserDatabase.class, userDatabase.DB_NAME).build();
    }

    public ProductController(IDashboardView dashboardView, View view) {
        this.dashboardView = dashboardView;
        this.view = view;
        userDatabase = Room.databaseBuilder(view.getContext(), UserDatabase.class, userDatabase.DB_NAME).build();
    }

    public ProductController(ISubscriptionHistoryView subscriptionHistoryView, View view) {
        this.subscriptionHistoryView = subscriptionHistoryView;
        this.view = view;
        userDatabase = Room.databaseBuilder(view.getContext(), UserDatabase.class, userDatabase.DB_NAME).build();
    }

    public ProductController(ITransactionHistoryView transactionHistoryView, View view) {
        this.transactionHistoryView = transactionHistoryView;
        this.view = view;
        userDatabase = Room.databaseBuilder(view.getContext(), UserDatabase.class, userDatabase.DB_NAME).build();
    }

    public ProductController(IAddMoneyView addMoneyView) {
        this.addMoneyView = addMoneyView;
        userDatabase = Room.databaseBuilder((Context) addMoneyView, UserDatabase.class, userDatabase.DB_NAME).build();
    }

    @Override
    public void handleSliderInitialization(View view, ViewPager viewPager, SliderView sliderView) {
        models = new ArrayList<>();
        models.add(new Slider(R.drawable.photo1, "LUNCH", "Poster is any piece of printed paper designed to be attached to a wall or vertical surface.", 1,0));
        models.add(new Slider(R.drawable.photo3, "DINNER", "Business cards are cards bearing business information about a company or individual.", 1,0));
        models.add(new Slider(R.drawable.photo3, "COMBO", "Business cards are cards bearing business information about a company or individual.", 0,1));

        adapter = new Adapter(models, view.getContext());
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 0, 130, 0);

        sliderAdapter = new SliderAdapter(view.getContext());
        sliderAdapter.setCount(3);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(2);
        sliderView.startAutoCycle();
        sliderView.setOnIndicatorClickListener(sliderView::setCurrentPagePosition);
    }

    @Override
    public void handleMenuInitialization(TabLayout tabLayout, ViewPager viewPager, ViewPagerAdapter viewPagerAdapter) {
        tabLayout.addTab(tabLayout.newTab().setText("Veg"));
        tabLayout.addTab(tabLayout.newTab().setText("Non Veg"));
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void handleSubscription(Map<String, Object> params, IAddMoneyView addMoneyView) {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            SubscriptionDao subscriptionDao = userDatabase.getSubscriptionDao();
            ActiveSubscription activeSubscription = new ActiveSubscription(params.get("days") + " Day(s)",
                    String.valueOf(params.get("date_Of_activation")),
                    "27:09:2021",
                    String.valueOf(params.get("no_of_dabba")),
                    Long.parseLong(String.valueOf(params.get("userId")))
                    );
            long[] result = subscriptionDao.insetAll(activeSubscription);

            checkoutView.addWalletTransaction(String.valueOf(result[0]));
        });
    }

    @Override
    public void handleWalletTransaction(Map<String, Object> params) {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            WalletTransactionDao walletTransactionDao = userDatabase.getWalletTransactionDao();
            TransactionHistory transactionHistory = new TransactionHistory(String.valueOf(params.get("subscriptionId")) ,"-", "Payment Transaction", String.valueOf(params.get("time_Of_transaction")),
                    "Card",String.valueOf(params.get("amount_deducted")));
            walletTransactionDao.insetAll(transactionHistory);

            ContextCompat.getMainExecutor((Context) checkoutView).execute(()  -> {
                checkoutView.handlePaymentSuccess();
            });
        });
    }

    @Override
    public void handleAddWalletTransaction(Map<String, Object> params) {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            WalletTransactionDao walletTransactionDao = userDatabase.getWalletTransactionDao();
            TransactionHistory transactionHistory = new TransactionHistory("+", "Payment Transaction", String.valueOf(params.get("time_Of_transaction")),
                    "Card",String.valueOf(params.get("amount_added")));
            walletTransactionDao.insetAll(transactionHistory);
        });
    }

    @Override
    public void handleInitSubscriptionData(String id) {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            SubscriptionDao subscriptionDao = userDatabase.getSubscriptionDao();

            ActiveSubscription activeSubscription = subscriptionDao.findById(id);
            Map<String, Object> params = new HashMap<>();

            if (activeSubscription != null) {
                params.put("days", activeSubscription.getPlan_name());
                params.put("date_Of_activation", activeSubscription.getStart_date());
                params.put("no_of_dabba", activeSubscription.getDabba());
                params.put("id", activeSubscription.getSubcriptionid());

                ContextCompat.getMainExecutor(view.getContext()).execute(()  -> {
                    subscriptionView.initData(view, params);
                });
            }
        });
    }

    @Override
    public void handleInitCredit(String id) {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            UserDao userDao = userDatabase.getUserDao();

            User user = userDao.findBySpecialId(id);

            ContextCompat.getMainExecutor(view.getContext()).execute(()  -> {
                dashboardView.initCredit(user != null ? user.getAmount() : "0");
            });
        });
    }

    @Override
    public void handleSubscriptionHistory() {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            SubscriptionHistoryDao subscriptionDao = userDatabase.getSubscriptionHistoryDao();

            List<SubscriptionHistory> subscriptionHistories = subscriptionDao.getAll();

            ContextCompat.getMainExecutor(view.getContext()).execute(()  -> {
                subscriptionHistoryView.initData(subscriptionHistories);
            });
        });
    }

    @Override
    public void handleTransactionHistory() {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            WalletTransactionDao walletTransactionDao = userDatabase.getWalletTransactionDao();

            List<TransactionHistory> transactionHistoryList = walletTransactionDao.getAll();

            ContextCompat.getMainExecutor(view.getContext()).execute(()  -> {
                transactionHistoryView.initData(transactionHistoryList);
            });
        });
    }

    @Override
    public void handleRemoveSubscription(ActiveSubscription activeSubscription, ISubscriptionView subscriptionView) {
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(() -> {
            SubscriptionDao subscriptionDao = userDatabase.getSubscriptionDao();
            SubscriptionHistoryDao subscriptionHistoryDao = userDatabase.getSubscriptionHistoryDao();

            SubscriptionHistory subscriptionHistory = new SubscriptionHistory(activeSubscription.getSubcriptionid(), activeSubscription.getPlan_name(), activeSubscription.getEnd_date());

            subscriptionDao.deleteAll(activeSubscription);
            subscriptionHistoryDao.insetAll(subscriptionHistory);

            ContextCompat.getMainExecutor(view.getContext()).execute(subscriptionView::handleRemove);
        });
    }
}
