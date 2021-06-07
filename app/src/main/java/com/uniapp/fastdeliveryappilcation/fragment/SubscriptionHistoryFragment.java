package com.uniapp.fastdeliveryappilcation.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.fastdeliveryappilcation.AddMoneyActivity;
import com.uniapp.fastdeliveryappilcation.MainActivity;
import com.uniapp.fastdeliveryappilcation.R;
import com.uniapp.fastdeliveryappilcation.adapter.SubscriptionHistoryAdapter;
import com.uniapp.fastdeliveryappilcation.controller.IProductController;
import com.uniapp.fastdeliveryappilcation.controller.ProductController;
import com.uniapp.fastdeliveryappilcation.model.ActiveSubscription;
import com.uniapp.fastdeliveryappilcation.model.SubscriptionHistory;
import com.uniapp.fastdeliveryappilcation.view.ISubscriptionHistoryView;

import java.util.List;

public class SubscriptionHistoryFragment extends Fragment implements ISubscriptionHistoryView {
    private Context context;
    RecyclerView recyclerView;
    SubscriptionHistoryAdapter subscriptionHistoryAdapter;
    IProductController productController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_subscription_history,container,false);
        recyclerView=view.findViewById(R.id.subcription_list);
        productController = new ProductController(this, view);
        productController.handleSubscriptionHistory();

        return (view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData(List<SubscriptionHistory> subscriptionHistories) {
        subscriptionHistoryAdapter = new SubscriptionHistoryAdapter(getContext(), subscriptionHistories);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(subscriptionHistoryAdapter);
    }
}
