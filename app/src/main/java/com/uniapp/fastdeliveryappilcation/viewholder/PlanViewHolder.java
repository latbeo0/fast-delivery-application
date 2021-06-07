package com.uniapp.fastdeliveryappilcation.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.fastdeliveryappilcation.R;

public class PlanViewHolder extends RecyclerView.ViewHolder {
    public TextView plan_name,plan_price;
    public Button chhosebtn;

    public PlanViewHolder(@NonNull View itemView) {
        super(itemView);
        plan_name=itemView.findViewById(R.id.plan_name);
        plan_price=itemView.findViewById(R.id.plan_price);
        chhosebtn=itemView.findViewById(R.id.choosebtn);

    }
}