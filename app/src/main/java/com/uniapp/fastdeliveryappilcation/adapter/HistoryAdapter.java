package com.uniapp.fastdeliveryappilcation.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.uniapp.fastdeliveryappilcation.fragment.SubscriptionHistoryFragment;
import com.uniapp.fastdeliveryappilcation.fragment.TransactionHistoryFragment;

public class HistoryAdapter extends FragmentPagerAdapter {
    private int tabCount;

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Subscription";
            case 1:
                return "Transaction";
        }
        return null;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0 :
                return new SubscriptionHistoryFragment();
            case 1:
                return new TransactionHistoryFragment();
        }
        return null;
    }

    public HistoryAdapter(FragmentManager fm, int tabs) {
        super(fm);
        this.tabCount = tabs;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
