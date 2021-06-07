package com.uniapp.fastdeliveryappilcation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.uniapp.fastdeliveryappilcation.controller.IProductController;
import com.uniapp.fastdeliveryappilcation.model.ActiveSubscription;
import com.uniapp.fastdeliveryappilcation.R;
import com.uniapp.fastdeliveryappilcation.view.ISubscriptionView;
import com.uniapp.fastdeliveryappilcation.viewholder.ActiveSubscriptionViewHolder;

import java.util.List;

public class ActiveSubscriptionAdapter extends RecyclerView.Adapter<ActiveSubscriptionViewHolder>{
    Context context;
    List<ActiveSubscription> subscriptionList;
    IProductController productController;
    ISubscriptionView subscriptionView;

    public ActiveSubscriptionAdapter(Context context, ISubscriptionView subscriptionView, List<ActiveSubscription> subcriptionList, IProductController productControllers) {
        this.context = context;
        this.subscriptionList = subcriptionList;
        this.productController = productControllers;
        this.subscriptionView = subscriptionView;
    }

    @NonNull
    @Override
    public ActiveSubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.subscription_single_item,parent,false);
        return new ActiveSubscriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ActiveSubscriptionViewHolder holder, int position) {
        final ActiveSubscription activeSubscription = subscriptionList.get(position);
        holder.subid.setText(String.valueOf(activeSubscription.getSubcriptionid()));
        holder.plan_name.setText(activeSubscription.getPlan_name());
        holder.start_date.setText(activeSubscription.getStart_date());
        holder.end_date.setText(activeSubscription.getEnd_date());
        holder.noofdabba.setText(activeSubscription.getDabba());

        holder.done1.setOnClickListener(v -> {
            productController.handleRemoveSubscription(activeSubscription, subscriptionView);
        });
    }
    @Override
    public int getItemCount() {
        return subscriptionList.size();
    }
}
