package com.uniapp.fastdeliveryappilcation.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.fastdeliveryappilcation.R;

public class TransactionHistoryViewHolder extends RecyclerView.ViewHolder {
    public TextView transactionId,name,time,addorMinus,amount,paymentMethod;

    public TransactionHistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        transactionId=itemView.findViewById(R.id.subid);
        name=itemView.findViewById(R.id.name);
        time=itemView.findViewById(R.id.time);
        addorMinus=itemView.findViewById(R.id.op);
        amount=itemView.findViewById(R.id.amt);
        paymentMethod=itemView.findViewById(R.id.payment);
    }
}
