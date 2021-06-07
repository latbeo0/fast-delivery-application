package com.uniapp.fastdeliveryappilcation.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.uniapp.fastdeliveryappilcation.R;
import com.uniapp.fastdeliveryappilcation.adapter.TransactionHistoryAdapter;
import com.uniapp.fastdeliveryappilcation.controller.ProductController;
import com.uniapp.fastdeliveryappilcation.model.TransactionHistory;
import com.uniapp.fastdeliveryappilcation.view.ITransactionHistoryView;

public class TransactionHistoryFragment extends Fragment implements ITransactionHistoryView {
    RecyclerView recyclerView;
    ProductController productController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_transaction_history,container,false);
        recyclerView=view.findViewById(R.id.transaction_list);
        productController = new ProductController(this, view);
        productController.handleTransactionHistory();

        return (view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initData(List<TransactionHistory> transactionHistoryList) {
        TransactionHistoryAdapter transactionhistoryAdapter = new TransactionHistoryAdapter(getActivity(), transactionHistoryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(transactionhistoryAdapter);
    }
}
