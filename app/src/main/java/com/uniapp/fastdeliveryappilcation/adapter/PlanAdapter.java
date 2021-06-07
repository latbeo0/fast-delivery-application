package com.uniapp.fastdeliveryappilcation.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uniapp.fastdeliveryappilcation.CheckoutActivity;
import com.uniapp.fastdeliveryappilcation.R;
import com.uniapp.fastdeliveryappilcation.model.Plan;
import com.uniapp.fastdeliveryappilcation.viewholder.PlanViewHolder;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanViewHolder>  {
    List<Plan> choosePlanList;

    public PlanAdapter(List<Plan> choosePlanList) {
        this.choosePlanList = choosePlanList;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_plan_single,parent,false);

        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlanViewHolder holder, int position) {
        final String day=choosePlanList.get(position).getPlan_name();
        final String price=choosePlanList.get(position).getPlan_price();
        final int meal=choosePlanList.get(position).getMeal();
        final int combo=choosePlanList.get(position).getCombo();

        holder.plan_name.setText(day);
        holder.plan_price.setText(price);

        holder.chhosebtn.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), CheckoutActivity.class);
            intent.putExtra("days",day.substring(0,2));
            intent.putExtra("prices",price);
            intent.putExtra("meal",meal);
            intent.putExtra("combo",combo);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return choosePlanList.size();
    }
}
