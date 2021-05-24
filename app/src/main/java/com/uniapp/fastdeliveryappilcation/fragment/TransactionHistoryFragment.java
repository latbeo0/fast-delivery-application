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
import java.util.Map;

import com.uniapp.fastdeliveryappilcation.R;
import com.uniapp.fastdeliveryappilcation.adapter.TransactionHistoryAdapter;

public class TransactionHistoryFragment extends Fragment {
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_transaction_history,container,false);
        recyclerView=view.findViewById(R.id.transaction_list);

        TransactionHistoryAdapter transactionhistoryAdapter = new TransactionHistoryAdapter(getActivity(), null);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(transactionhistoryAdapter);
        return (view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
