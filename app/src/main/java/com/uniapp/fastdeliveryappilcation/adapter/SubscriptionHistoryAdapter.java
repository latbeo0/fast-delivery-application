package com.uniapp.fastdeliveryappilcation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.fastdeliveryappilcation.model.SubscriptionHistory;
import com.uniapp.fastdeliveryappilcation.viewholder.SubscriptionHistoryViewHolder;

import com.uniapp.fastdeliveryappilcation.R;

import java.util.List;

public class SubscriptionHistoryAdapter extends RecyclerView.Adapter<SubscriptionHistoryViewHolder> {

    Context context;
    List<SubscriptionHistory> subscriptionHistories;

    public SubscriptionHistoryAdapter(Context context, List<SubscriptionHistory> subscriptionHistories) {
        this.context = context;
        this.subscriptionHistories = subscriptionHistories;
    }

    @NonNull
    @Override
    public SubscriptionHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subcription_history_single_item,parent,false);
        return new SubscriptionHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionHistoryViewHolder holder, int position) {
        SubscriptionHistory subscriptionHistory = subscriptionHistories.get(position);
        holder.subscriptionid.setText(String.valueOf(subscriptionHistory.getSubscriptionid()));
        holder.plan.setText(subscriptionHistory.getPlan());
        holder.time.setText(subscriptionHistory.getTime());
    }

    @Override
    public int getItemCount() {
        return subscriptionHistories.size();
    }
}
