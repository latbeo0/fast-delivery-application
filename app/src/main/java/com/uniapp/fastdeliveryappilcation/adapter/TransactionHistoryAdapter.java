package com.uniapp.fastdeliveryappilcation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.fastdeliveryappilcation.model.TransactionHistory;
import com.uniapp.fastdeliveryappilcation.viewholder.TransactionHistoryViewHolder;

import java.util.List;
import com.uniapp.fastdeliveryappilcation.R;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryViewHolder> {

    Context context;
    List<TransactionHistory> transactionHistoryList;

    public TransactionHistoryAdapter(Context context, List<TransactionHistory> transactionHistoryList) {
        this.context = context;
        this.transactionHistoryList = transactionHistoryList;
    }

    @NonNull
    @Override
    public TransactionHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_history_single_item,parent,false);
        TransactionHistoryViewHolder transactionHistoryViewHolder=new TransactionHistoryViewHolder(view);
        return transactionHistoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHistoryViewHolder holder, int position) {

        TransactionHistory transactionHistory = transactionHistoryList.get(position);
        holder.transactionId.setText(transactionHistory.getTransactionId());
        holder.name.setText(transactionHistory.getName());
        holder.paymentMethod.setText(transactionHistory.getPaymentmethod());
        holder.addorMinus.setText(transactionHistory.getAddorMinus());
        holder.amount.setText(transactionHistory.getAmount());
        holder.time.setText(transactionHistory.getTime());
    }

    @Override
    public int getItemCount() {
        return transactionHistoryList.size();
    }
}
