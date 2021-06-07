package com.uniapp.fastdeliveryappilcation.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.uniapp.fastdeliveryappilcation.R;
import com.uniapp.fastdeliveryappilcation.adapter.ActiveSubscriptionAdapter;

public class ActiveSubscriptionViewHolder extends RecyclerView.ViewHolder {
    public TextView subid,plan_name,start_date,end_date,noofdabba;
    public Button done1;

    public ActiveSubscriptionViewHolder(@NonNull View itemView) {
        super(itemView);
        subid=itemView.findViewById(R.id.subid);
        plan_name=itemView.findViewById(R.id.plan_name);
        start_date=itemView.findViewById(R.id.start);
        end_date=itemView.findViewById(R.id.end);
        done1=itemView.findViewById(R.id.done);
        noofdabba=itemView.findViewById(R.id.noofdabba);
    }
}
