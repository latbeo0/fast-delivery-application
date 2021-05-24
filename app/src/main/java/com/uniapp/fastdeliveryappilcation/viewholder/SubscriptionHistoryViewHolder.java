package com.uniapp.fastdeliveryappilcation.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.fastdeliveryappilcation.R;

public class SubscriptionHistoryViewHolder extends RecyclerView.ViewHolder {
    public TextView subscriptionid,plan,time,addorminus,amount;

    public SubscriptionHistoryViewHolder(@NonNull View itemView) {
        super(itemView);

        subscriptionid=itemView.findViewById(R.id.subid);
        plan=itemView.findViewById(R.id.plan_name);
        time=itemView.findViewById(R.id.start);
        addorminus=itemView.findViewById(R.id.op);
        amount=itemView.findViewById(R.id.amt);
    }
}
